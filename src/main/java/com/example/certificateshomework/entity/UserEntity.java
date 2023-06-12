package com.example.certificateshomework.entity;

import com.example.certificateshomework.enums.UserRole;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table("users")
public class UserEntity {

    @Id
    private Long id;
    private String username;
    private String password;
    private UserRole role;
    private String firstName;
    private String lastName;
    private boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + maskPassword() + '\'' +
                ", role='" + role + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", enabled=" + enabled +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserEntity that = (UserEntity) o;
        return enabled == that.enabled && Objects.equals(id, that.id)
                && Objects.equals(username, that.username) && Objects.equals(
                password, that.password) && Objects.equals(role, that.role)
                && Objects.equals(firstName, that.firstName) && Objects.equals(
                lastName, that.lastName) && Objects.equals(createdAt, that.createdAt)
                && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, role, firstName, lastName, enabled, createdAt,
                updatedAt);
    }

    private String maskPassword() {
        return "********";
    }
}
