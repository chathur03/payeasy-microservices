package payeasy.merchant.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import payeasy.merchant.entity.Product;
import java.util.List;
public interface ProductRepository extends JpaRepository<Product, Long> {
  List<Product> findByMerchantId(Long merchantId);
}
