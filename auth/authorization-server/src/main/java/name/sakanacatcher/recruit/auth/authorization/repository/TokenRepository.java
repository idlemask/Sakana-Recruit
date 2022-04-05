package name.sakanacatcher.recruit.auth.authorization.repository;

import name.sakanacatcher.recruit.auth.authorization.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    List<Token> findAllByUsername(String username);

    Integer deleteAllByUsername(String username);

    Integer deleteByUsernameAndDevice(String username, String device);
}
