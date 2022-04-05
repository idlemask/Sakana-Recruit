package name.sakanacatcher.recruit.collector.consumer.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@Entity
@Table(name = "preference_tag")
@NamedEntityGraph(name = "PreferenceTag.Graph", attributeNodes = {@NamedAttributeNode("children")})
@JsonIgnoreProperties({"handler", "hibernateInitializer"})
public class PreferenceTag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "parent")
    private PreferenceTag parent;

    private String description;

    private int height;

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    private List<PreferenceTag> children;

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("name", name);
        map.put("description", description);
        map.put("children", null);
        return map;
    }

    @Override
    public String toString() {
        return "PreferenceTag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", height='" + height + '\'' +
                ", description='" + description + '\'' +
                (children != null ? ", children=" + children : "") +
                '}';
    }
}
