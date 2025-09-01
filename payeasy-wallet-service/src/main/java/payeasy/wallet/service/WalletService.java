package payeasy.wallet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import payeasy.wallet.entity.Transaction;
import payeasy.wallet.entity.Wallet;
import payeasy.wallet.repo.TransactionRepository;
import payeasy.wallet.repo.WalletRepository;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class WalletService {
  private final WalletRepository wallets;
  private final TransactionRepository txns;

  public Wallet getOrCreate(Long userId){
    return wallets.findByUserId(userId).orElseGet(() -> wallets.save(
      Wallet.builder().userId(userId).balance(BigDecimal.ZERO).build()
    ));
  }

  @Transactional
  public Wallet credit(Long userId, BigDecimal amount, String reason){
    Wallet w = getOrCreate(userId);
    w.setBalance(w.getBalance().add(amount));
    wallets.save(w);
    txns.save(Transaction.builder().userId(userId).walletId(w.getWalletId())
      .txnType("CREDIT").amount(amount).status("SUCCESS").description(reason).build());
    return w;
  }

  @Transactional
  public Wallet debit(Long userId, BigDecimal amount, String reason){
    Wallet w = getOrCreate(userId);
    if(w.getBalance().compareTo(amount) < 0){
      txns.save(Transaction.builder().userId(userId).walletId(w.getWalletId())
        .txnType("DEBIT").amount(amount).status("FAILED").description("Insufficient: "+reason).build());
      throw new IllegalStateException("Insufficient balance");
    }
    w.setBalance(w.getBalance().subtract(amount));
    wallets.save(w);
    txns.save(Transaction.builder().userId(userId).walletId(w.getWalletId())
      .txnType("DEBIT").amount(amount).status("SUCCESS").description(reason).build());
    return w;
  }

  @Transactional
  public void transfer(Long fromUser, Long toUser, BigDecimal amount){
    debit(fromUser, amount, "Transfer to user "+toUser);
    credit(toUser, amount, "Transfer from user "+fromUser);
  }
}
