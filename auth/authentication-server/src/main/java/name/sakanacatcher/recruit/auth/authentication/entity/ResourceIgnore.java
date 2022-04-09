package name.sakanacatcher.recruit.auth.authentication.entity;

import lombok.Data;

import javax.persistence.*;

@Table(name = "resource_ignore")
@Entity
@Data
public class ResourceIgnore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "url", nullable = false, length = 250)
    private String url;

    @Column(name = "enable")
    private Boolean enable;

}