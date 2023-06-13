package com.example.certificateshomework.metrics;

import io.micrometer.core.instrument.MultiGauge;
import io.micrometer.core.instrument.Tags;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificateInfo {

    private String certificateName;
    private Long validityInDays;

    public MultiGauge.Row<CertificateInfo> toRow() {
        return MultiGauge.Row.of(
                Tags.of("days before expiration date for alias", certificateName),
                this,
                c -> this.validityInDays
        );
    }
}
