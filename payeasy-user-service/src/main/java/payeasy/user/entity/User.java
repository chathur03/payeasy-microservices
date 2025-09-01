package payeasy.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity @Table(name = "USERS")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "USER_ID")
  private Long userId;

  @Column(name = "FULL_NAME", nullable = false)
  private String fullName;

  @Column(name = "EMAIL", unique = true)
  private String email;

  @Column(name = "MOBILE", unique = true, nullable = false)
  private String mobile;

  @Column(name = "PASSWORD_HASH")
  private String passwordHash;

  @Column(name = "MFA_ENABLED")
  private Boolean mfaEnabled = Boolean.FALSE;

  @Column(name = "CREATED_AT")
  private Instant createdAt;

  @Column(name = "UPDATED_AT")
  private Instant updatedAt;

  @PrePersist
  public void prePersist(){
    createdAt = Instant.now();
    updatedAt = createdAt;
  }
  @PreUpdate
  public void preUpdate(){
    updatedAt = Instant.now();
  }
}
