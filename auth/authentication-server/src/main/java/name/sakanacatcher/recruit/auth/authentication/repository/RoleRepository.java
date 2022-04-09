package name.sakanacatcher.recruit.auth.authentication.repository;

import name.sakanacatcher.recruit.auth.authentication.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findById(int id);
}