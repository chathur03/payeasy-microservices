package payeasy.wallet.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import payeasy.wallet.entity.BankAccount;
import java.util.List;
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
  List<BankAccount> findByUserId(Long userId);
}

