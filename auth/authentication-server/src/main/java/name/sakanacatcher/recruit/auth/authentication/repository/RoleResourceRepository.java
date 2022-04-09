package name.sakanacatcher.recruit.auth.authentication.repository;

import name.sakanacatcher.recruit.auth.authentication.entity.Resource;
import name.sakanacatcher.recruit.auth.authentication.entity.Role;
import name.sakanacatcher.recruit.auth.authentication.entity.RoleResouce;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleResourceRepository extends JpaRepository<RoleResouce, Integer> {
    RoleResouce findByRoleEqualsAndAndResourceEquals(Role role, Resource resource);
}