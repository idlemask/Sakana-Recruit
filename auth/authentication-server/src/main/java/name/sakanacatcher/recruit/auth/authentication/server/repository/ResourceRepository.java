package name.sakanacatcher.recruit.auth.authentication.server.repository;

import name.sakanacatcher.recruit.auth.authentication.server.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource, Integer> {
    Resource findByUrl(String url);
    Resource findById(int id);

    List<Resource> findAllByName(String name);
    List<Resource> findAllByNameStartingWith(String name);

    Boolean existsByUrl(String url);
    @Transactional
    @Modifying
    @Query(value = "UPDATE resource SET enable = ?2 where name = ?1", nativeQuery = true)
    void updateEnableByName(String appName, Boolean enable);

    @Transactional
    @Modifying
    @Query(value = "UPDATE resource SET enable = ?2 where id = ?1", nativeQuery = true)
    void updateEnableById(Integer id, Boolean enable);
}