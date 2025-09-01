package payeasy.wallet.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity @Table(name="BANK_ACCOUNTS")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BankAccount {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="BANK_ID") private Long bankId;

  @Column(name="USER_ID", nullable=false) private Long userId;
  @Column(name="BANK_NAME") private String bankName;
  @Column(name="ACCOUNT_NUMBER") private String accountNumber;
  @Column(name="IFSC_CODE") private String ifscCode;
  @Column(name="UPI_ID") private String upiId;
  @Column(name="IS_PRIMARY") private Boolean isPrimary = Boolean.FALSE;
  @Column(name="CREATED_AT") private Instant createdAt;

  @PrePersist public void pre(){ createdAt = Instant.now(); }
}
