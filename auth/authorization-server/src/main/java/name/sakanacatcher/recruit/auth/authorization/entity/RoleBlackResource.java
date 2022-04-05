package name.sakanacatcher.recruit.auth.authorization.entity;

import lombok.Data;

import javax.persistence.*;

@Table(name = "role_black_resource")
@Entity
@Data
public class RoleBlackResource {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_id")
    private Resource resource;

}