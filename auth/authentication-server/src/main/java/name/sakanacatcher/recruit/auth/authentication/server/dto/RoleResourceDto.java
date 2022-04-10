package name.sakanacatcher.recruit.auth.authentication.server.dto;

import name.sakanacatcher.recruit.auth.authentication.server.entity.Resource;

import javax.persistence.*;

public class RoleResourceDto {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "role_id")
    private int roleId;

    @Column(name = "resource_id")
    private Resource resource;
}
