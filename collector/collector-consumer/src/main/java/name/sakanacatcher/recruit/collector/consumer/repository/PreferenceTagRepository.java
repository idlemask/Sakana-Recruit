package name.sakanacatcher.recruit.collector.consumer.repository;

import name.sakanacatcher.recruit.collector.consumer.pojo.PreferenceTag;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PreferenceTagRepository extends JpaRepository<PreferenceTag, Integer> {
    @EntityGraph(value = "PreferenceTag.Graph", type = EntityGraph.EntityGraphType.FETCH)
    List<PreferenceTag> findAll();

    PreferenceTag findById(int id);

    List<PreferenceTag> findAllByName(String name);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO preference_tag (name,parent,description,height) VALUES (?1,?2,?3,?4)", nativeQuery = true)
    int save(String name, int parent, String description, int height);


    @Query(value = "select last_insert_id() FROM preference_tag limit 1", nativeQuery = true)
    int getLastInsertId();
}
