package payeasy.bill.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Entity @Table(name="BILLS")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Bill {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="BILL_ID") private Long billId;

  @Column(name="USER_ID") private Long userId;
  @Column(name="BILLER_NAME") private String billerName;
  @Column(name="BILL_TYPE") private String billType;
  @Column(name="ACCOUNT_NUMBER") private String accountNumber;
  @Column(name="AMOUNT", precision=19, scale=2) private BigDecimal amount;
  @Column(name="STATUS") private String status; // PENDING/PAID
  @Column(name="DUE_DATE") private LocalDate dueDate;
  @Column(name="CREATED_AT") private Instant createdAt;

  @PrePersist public void pre(){ createdAt = Instant.now(); if(status==null) status="PENDING"; }
}
