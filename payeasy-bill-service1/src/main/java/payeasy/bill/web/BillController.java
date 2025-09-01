package payeasy.bill.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import payeasy.bill.client.WalletClient;
import payeasy.bill.entity.Bill;
import payeasy.bill.repo.BillRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bills")
public class BillController {
  private final BillRepository bills;
  private final WalletClient wallet;

  @PostMapping
  public Bill create(@RequestBody Bill b){ return bills.save(b); }

  @GetMapping("/user/{userId}")
  public List<Bill> list(@PathVariable Long userId){ return bills.findByUserId(userId); }

  @PostMapping("/{billId}/pay")
  public ResponseEntity<String> pay(@PathVariable Long billId){
    Bill b = bills.findById(billId).orElseThrow();
    if("PAID".equalsIgnoreCase(b.getStatus())) return ResponseEntity.ok("Already paid");
    wallet.debit(b.getUserId(), b.getAmount(), "Bill: "+b.getBillerName());
    b.setStatus("PAID");
    bills.save(b);
    return ResponseEntity.ok("Bill paid");
  }
}
