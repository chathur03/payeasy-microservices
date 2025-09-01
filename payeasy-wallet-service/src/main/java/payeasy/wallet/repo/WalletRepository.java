package payeasy.wallet.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import payeasy.wallet.entity.Wallet;
import java.util.Optional;
public interface WalletRepository extends JpaRepository<Wallet, Long> {
  Optional<Wallet> findByUserId(Long userId);
}
