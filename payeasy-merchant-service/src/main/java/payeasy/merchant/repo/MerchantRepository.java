package payeasy.merchant.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import payeasy.merchant.entity.Merchant;
public interface MerchantRepository extends JpaRepository<Merchant, Long> {}
