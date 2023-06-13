package com.example.certificateshomework.metrics;

import com.example.certificateshomework.exception.ApiException;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.MultiGauge;
import java.io.File;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CertificatesMetrics {

    //@Value("${server.ssl.key-store-password}") //todo
    private String keystorePassword = "password";

    //@Value("${server.ssl.key-store}") //todo
    private String keystorePath = "src/main/resources/bootsecurity.p12";

    public CertificatesMetrics(MeterRegistry registry) {
        MultiGauge certificates = MultiGauge.builder("certificates info")
                .description("certificate name and validity info")
                .register(registry);

        certificates.register(getCertificatesInfo().stream()
                .map(CertificateInfo::toRow)
                .collect(Collectors.toList())
        );
    }

    private List<CertificateInfo> getCertificatesInfo() {
        try {
            KeyStore truststore = KeyStore.getInstance(
                    new File(keystorePath),
                    keystorePassword.toCharArray()
            );

            List<CertificateInfo> certificateInfos = new ArrayList<>();
            Enumeration<String> aliases = truststore.aliases();

            while (aliases.hasMoreElements()) {
                String alias = aliases.nextElement();
                X509Certificate certificate = (X509Certificate) truststore.getCertificate(alias);
                Date now = new Date();
                Date validUntil = certificate.getNotAfter();
                long validityInMillis = validUntil.getTime() - now.getTime();
                long validityInDays = 0L;

                if (validityInMillis > 0) {
                    validityInDays = TimeUnit.DAYS.convert(
                            validityInMillis,
                            TimeUnit.MILLISECONDS
                    );
                }

                CertificateInfo certificateInfo = new CertificateInfo(
                        alias,
                        validityInDays
                );
                certificateInfos.add(certificateInfo);
            }

            return certificateInfos;
        } catch (
                KeyStoreException |
                IOException |
                CertificateException |
                NoSuchAlgorithmException e
        ) {
            throw new ApiException("Ошибка получения сертификата", "CERTIFICATE_PROCESS_ERROR");
        }
    }
}
