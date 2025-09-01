package payeasy.wallet.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity @Table(name="TRANSACTIONS")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Transaction {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="TXN_ID") private Long txnId;

  @Column(name="USER_ID") private Long userId;
  @Column(name="WALLET_ID") private Long walletId;
  @Column(name="BANK_ID") private Long bankId;
  @Column(name="MERCHANT_ID") private Long merchantId;
  @Column(name="BILL_ID") private Long billId;

  @Column(name="TXN_TYPE") private String txnType; // CREDIT/DEBIT/TRANSFER/RECHARGE/BILL/ORDER
  @Column(name="AMOUNT", precision=19, scale=2) private BigDecimal amount;
  @Column(name="STATUS") private String status;   // SUCCESS/FAILED
  @Column(name="DESCRIPTION") private String description;
  @Column(name="CREATED_AT") private Instant createdAt;

  @PrePersist public void pre(){ createdAt = Instant.now(); }
}
