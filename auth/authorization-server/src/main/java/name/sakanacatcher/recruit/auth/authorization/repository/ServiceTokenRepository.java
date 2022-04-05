package name.sakanacatcher.recruit.auth.authorization.repository;

import name.sakanacatcher.recruit.auth.authorization.entity.ServiceToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceTokenRepository extends JpaRepository<ServiceToken, Integer> {
    ServiceToken findByName(String name);
}
