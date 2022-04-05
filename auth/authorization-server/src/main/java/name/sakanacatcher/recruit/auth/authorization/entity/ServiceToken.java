package name.sakanacatcher.recruit.auth.authorization.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.MacSigner;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "service")
public class ServiceToken {
    @Transient
    private static String SECRET = "Sakana";
    private static int RAND_CODE_LENGTH = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name = "token", nullable = false, length = 245)
    private String token;

    public ServiceToken(String name) {
        this.name = name;
        setToken(name);
    }

    public static ServiceToken parseString(String token) {
        String claim = JwtHelper.decodeAndVerify(token, new MacSigner(SECRET)).getClaims();
        return new ServiceToken(
                claim.substring(RAND_CODE_LENGTH)
        );
    }

    public void setToken(String name) {
        token = JwtHelper.encode(generateCode() + name, new MacSigner(SECRET)).getEncoded();
    }

    long generateCode() {
        return Math.round(Math.random() * Math.pow(10, RAND_CODE_LENGTH));
    }
}