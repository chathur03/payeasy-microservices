package payeasy.wallet.web;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import payeasy.wallet.entity.BankAccount;
import payeasy.wallet.entity.Transaction;
import payeasy.wallet.entity.Wallet;
import payeasy.wallet.repo.BankAccountRepository;
import payeasy.wallet.repo.TransactionRepository;
import payeasy.wallet.service.WalletService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class WalletController {

  private final WalletService service;
  private final BankAccountRepository banks;
  private final TransactionRepository txns;

  // --- Wallets
  @PostMapping("/wallets/{userId}")
  public Wallet createWallet(@PathVariable Long userId){
    return service.getOrCreate(userId);
  }

  @GetMapping("/wallets/{userId}/balance")
  public BigDecimal balance(@PathVariable Long userId){
    return service.getOrCreate(userId).getBalance();
  }

  @PostMapping("/wallets/{userId}/add")
  public Wallet add(@PathVariable Long userId, @RequestParam BigDecimal amount,
                    @RequestParam(defaultValue = "Add money") String reason){
    return service.credit(userId, amount, reason);
  }

  @PostMapping("/wallets/transfer")
  public ResponseEntity<String> transfer(@RequestBody TransferRequest r){
    service.transfer(r.fromUserId, r.toUserId, r.amount);
    return ResponseEntity.ok("Transferred");
  }

  @GetMapping("/transactions")
  public List<Transaction> userTxns(@RequestParam Long userId){
    return txns.findByUserIdOrderByCreatedAtDesc(userId);
  }

  // --- Bank accounts
  @PostMapping("/bank-accounts")
  public BankAccount link(@RequestBody BankAccount b){ return banks.save(b); }

  @GetMapping("/bank-accounts/user/{userId}")
  public List<BankAccount> listBank(@PathVariable Long userId){ return banks.findByUserId(userId); }

  // --- Internal (used by other services through Feign) ---
  @PostMapping("/internal/wallets/{userId}/debit")
  public Wallet internalDebit(@PathVariable Long userId, @RequestParam BigDecimal amount,
                              @RequestParam(defaultValue="") String reason){
    return service.debit(userId, amount, reason);
  }
  @PostMapping("/internal/wallets/{userId}/credit")
  public Wallet internalCredit(@PathVariable Long userId, @RequestParam BigDecimal amount,
                               @RequestParam(defaultValue="") String reason){
    return service.credit(userId, amount, reason);
  }

  @Data public static class TransferRequest { Long fromUserId; Long toUserId; BigDecimal amount; }
}
