package name.sakanacatcher.recruit.auth.authentication.entity;

import javax.persistence.*;

@Table(name = "role")
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "comment", length = 120)
    private String comment;

    public String getComments() {
        return comment;
    }

    public void setComment(String comments) {
        this.comment = comments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}