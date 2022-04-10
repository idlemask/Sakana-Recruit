package name.sakanacatcher.recruit.auth.authentication.server.repository;

import name.sakanacatcher.recruit.auth.authentication.server.entity.Resource;
import name.sakanacatcher.recruit.auth.authentication.server.entity.Role;
import name.sakanacatcher.recruit.auth.authentication.server.entity.RoleResouce;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleResourceRepository extends JpaRepository<RoleResouce, Integer> {
    RoleResouce findByRoleEqualsAndAndResourceEquals(Role role, Resource resource);
}