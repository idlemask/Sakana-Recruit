package name.sakanacatcher.recruit.collector.consumer.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PreferenceTagDto implements Serializable {
    private final String name;
    private final int parent;
    private final String description;
    private final int height;
}
