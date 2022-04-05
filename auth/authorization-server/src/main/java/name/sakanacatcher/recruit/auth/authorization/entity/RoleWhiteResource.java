package name.sakanacatcher.recruit.auth.authorization.entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "role_white_resource")
public class RoleWhiteResource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_id")
    private Resource resource;
}