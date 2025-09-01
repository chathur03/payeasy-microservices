package payeasy.merchant.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity @Table(name="MERCHANTS")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Merchant {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="MERCHANT_ID") private Long merchantId;

  @Column(name="MERCHANT_NAME") 
  private String merchantName;
  @Column(name="EMAIL") 
  private String email;
  @Column(name="MOBILE") 
  private String mobile;
  @Column(name="PASSWORD_HASH") 
  private String passwordHash;
  @Column(name="CREATED_AT") 
  private Instant createdAt;

  @PrePersist public void pre(){ createdAt = Instant.now(); }
}
