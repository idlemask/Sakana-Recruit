package name.sakanacatcher.recruit.auth.authentication.server.repository;

import name.sakanacatcher.recruit.auth.authentication.server.entity.ResourceIgnore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResourceIgnoreRepository extends JpaRepository<ResourceIgnore, Integer> {

    List<ResourceIgnore> findAllByEnableEquals(boolean enable);

    ResourceIgnore findById(int id);


}