package name.sakanacatcher.recruit.collector.consumer.service;

import name.sakanacatcher.recruit.collector.consumer.dto.PreferenceTagDto;
import name.sakanacatcher.recruit.collector.consumer.repository.PreferenceTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Component
public class PreferenceTagService {

    @Autowired
    PreferenceTagRepository preferenceTagRepository;

    @Transactional
    public int save(PreferenceTagDto preferenceTagDto) {
        if (
                preferenceTagRepository.save(
                        preferenceTagDto.getName(),
                        preferenceTagDto.getParent(),
                        preferenceTagDto.getDescription(),
                        preferenceTagDto.getHeight()) == 1
        ) {

        }
        return preferenceTagRepository.getLastInsertId();
    }
}
