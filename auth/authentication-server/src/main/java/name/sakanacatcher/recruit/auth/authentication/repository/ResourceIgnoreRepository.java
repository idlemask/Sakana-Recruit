package name.sakanacatcher.recruit.auth.authentication.repository;

import name.sakanacatcher.recruit.auth.authentication.entity.Resource;
import name.sakanacatcher.recruit.auth.authentication.entity.ResourceIgnore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResourceIgnoreRepository extends JpaRepository<ResourceIgnore, Integer> {
    List<ResourceIgnore> findAllByUrlContainsAndEnableEquals(String url,boolean enable);

    ResourceIgnore findById(int id);


}