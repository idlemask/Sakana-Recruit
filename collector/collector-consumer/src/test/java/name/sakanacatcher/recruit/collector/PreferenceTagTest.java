package name.sakanacatcher.recruit.collector;



import name.sakanacatcher.recruit.collector.consumer.CollectorConsumer;
import name.sakanacatcher.recruit.collector.consumer.dto.PreferenceTagDto;
import name.sakanacatcher.recruit.collector.consumer.pojo.PreferenceTag;
import name.sakanacatcher.recruit.collector.consumer.repository.PreferenceTagRepository;
import name.sakanacatcher.recruit.collector.consumer.service.PreferenceTagService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CollectorConsumer.class)
public class PreferenceTagTest {
    @Autowired
    PreferenceTagRepository preferenceTagRepository;
    @Autowired
    PreferenceTagService preferenceTagService;

    @Test
    public void TestRepositoryFindAll() {
        List<PreferenceTag> preferenceTagList = preferenceTagRepository.findAll();
        PreferenceTag tag = preferenceTagList.get(2);
        System.out.println(tag.getParent());

    }

    @Test
    public void TestRepositoryFindOne() {
        PreferenceTag preferenceTag = preferenceTagRepository.findById(1);
        System.out.println(preferenceTag);
    }

    @Test
    public void TestRepositorySave() {
        int preferenceTag = preferenceTagRepository.save("lalala", 0, "", 1);
        System.out.println(preferenceTag);
    }

    @Test
    public void TestRepositoryGetLastInsertId() {
        System.out.println(preferenceTagRepository.getLastInsertId());
    }

    @Test
    public void TestServiceSave() {
        PreferenceTagDto preferenceTagDto = new PreferenceTagDto("lalala", 0, "", 1);
        int preferenceTag = preferenceTagService.save(preferenceTagDto);
        System.out.println(preferenceTag);
    }
}
