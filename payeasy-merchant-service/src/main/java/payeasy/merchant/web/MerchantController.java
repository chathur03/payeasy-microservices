package payeasy.merchant.web;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import payeasy.merchant.client.WalletClient;
import payeasy.merchant.entity.Merchant;
import payeasy.merchant.entity.Product;
import payeasy.merchant.repo.MerchantRepository;
import payeasy.merchant.repo.ProductRepository;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MerchantController {
  private final MerchantRepository merchants;
  private final ProductRepository products;
  private final WalletClient wallet;

  @PostMapping("/merchants")
  public Merchant createMerchant(@RequestBody Merchant m){ return merchants.save(m); }

  @GetMapping("/merchants/{id}")
  public ResponseEntity<Merchant> one(@PathVariable Long id){
    return merchants.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }
  
  @PutMapping("/merchants")
  public ResponseEntity<Merchant> updateMerchant(@RequestHeader Long id, @RequestBody Merchant updated) {
      return merchants.findById(id).map(existing -> {
          // update only the fields you want to allow changes for
          existing.setMerchantName(updated.getMerchantName());
          existing.setEmail(updated.getEmail());
          existing.setMobile(updated.getMobile());
          existing.setPasswordHash(updated.getPasswordHash());
          
          Merchant saved = merchants.save(existing);
          return ResponseEntity.ok(saved);
      }).orElse(ResponseEntity.notFound().build());
  }


  @PostMapping("/products")
  public Product addProduct(@RequestBody Product p){ return products.save(p); }

  @GetMapping("/products")
  public List<Product> list(@RequestParam(required=false) Long merchantId){
    return merchantId==null ? products.findAll() : products.findByMerchantId(merchantId);
  }

  // Simplified "order" -> just debit user's wallet and (optionally) credit merchant wallet (if you want)
  @PostMapping("/orders/pay")
  public ResponseEntity<String> payOrder(@RequestBody PayOrder req){
    wallet.debit(req.userId, req.amount, "Order to merchant "+req.merchantId);
    return ResponseEntity.ok("Order paid");
  }

  
  @Data public static class PayOrder { Long userId; Long merchantId; BigDecimal amount; }
}
