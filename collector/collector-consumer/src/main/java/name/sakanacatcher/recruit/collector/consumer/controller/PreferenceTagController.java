package name.sakanacatcher.recruit.collector.consumer.controller;

import name.sakanacatcher.recruit.collector.consumer.repository.PreferenceTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PreferenceTagController {
    @Autowired
    PreferenceTagRepository preferenceTagRepository;

}
