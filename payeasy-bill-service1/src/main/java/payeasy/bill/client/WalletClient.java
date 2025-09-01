package payeasy.bill.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name="wallet-service")
public interface WalletClient {
  @PostMapping("/internal/wallets/{userId}/debit")
  Object debit(@PathVariable("userId") Long userId, @RequestParam("amount") BigDecimal amount,
               @RequestParam("reason") String reason);
}
