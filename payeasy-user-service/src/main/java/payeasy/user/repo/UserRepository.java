package payeasy.user.repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import payeasy.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByMobile(String mobile);
  Optional<User> findByEmail(String email);
}
