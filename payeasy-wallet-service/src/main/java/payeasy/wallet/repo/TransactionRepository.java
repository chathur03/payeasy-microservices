package payeasy.wallet.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import payeasy.wallet.entity.Transaction;
import java.util.List;
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
  List<Transaction> findByUserIdOrderByCreatedAtDesc(Long userId);
}
