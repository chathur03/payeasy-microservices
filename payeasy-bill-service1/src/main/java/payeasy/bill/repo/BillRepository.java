package payeasy.bill.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import payeasy.bill.entity.Bill;
import java.util.List;
public interface BillRepository extends JpaRepository<Bill, Long> {
  List<Bill> findByUserId(Long userId);
}
