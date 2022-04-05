package name.sakanacatcher.recruit.auth.authorization.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Table(name = "resource")
@Entity
@Data
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name = "url", length = 100)
    private String url;

    @OneToMany(mappedBy = "resource")
    private Set<RoleBlackResource> roleBlackResources = new LinkedHashSet<>();

    @OneToMany(mappedBy = "resource")
    private Set<RoleWhiteResource> roleWhiteResources = new LinkedHashSet<>();
}