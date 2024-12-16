package ecommerce.api.repository;

import ecommerce.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    // Phương thức tìm kiếm User theo username
    Optional<User> findByUsername(String username);
}
