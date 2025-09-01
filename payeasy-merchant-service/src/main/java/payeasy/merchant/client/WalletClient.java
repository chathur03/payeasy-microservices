package payeasy.merchant.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="wallet-service")
public interface WalletClient {
  @PostMapping("/internal/wallets/{userId}/debit")
  Object debit(@PathVariable("userId") Long userId,
               @RequestParam("amount") java.math.BigDecimal amount,
               @RequestParam("reason") String reason);
  @PostMapping("/internal/wallets/{userId}/credit")
  Object credit(@PathVariable("userId") Long userId,
                @RequestParam("amount") java.math.BigDecimal amount,
                @RequestParam("reason") String reason);
}
