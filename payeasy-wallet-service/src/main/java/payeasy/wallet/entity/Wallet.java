package payeasy.wallet.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity @Table(name="WALLETS")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Wallet {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="WALLET_ID") private Long walletId;

  @Column(name="USER_ID", nullable=false) private Long userId;

  @Column(name="BALANCE", precision=19, scale=2) private BigDecimal balance = BigDecimal.ZERO;

  @Column(name="LAST_UPDATED") private Instant lastUpdated;

  @PrePersist @PreUpdate
  public void touch(){ lastUpdated = Instant.now(); }
}
