package name.sakanacatcher.recruit.auth.authorization.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.MacSigner;

import javax.persistence.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user_auth_token")
public class Token {
    @Transient
    private static long EXPIRATION_INCREMENT = 3600L;

    @Transient
    private static long EXPIRATION_MAXIMUM = 604800L;

    @Transient
    private String sign;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "token", nullable = false, length = 10)
    private String token;

    @Column(name = "device", nullable = false, length = 45)
    private String device = "computer";

    @Column(name = "ip", nullable = false)
    private Long ip;

    @Column(name = "create_Time", nullable = false)
    private Long createTime;

    @Column(name = "expired_Time", nullable = false)
    private Long expiredTime;

    public Token(String sign, String username) {
        this.username = username;
        this.sign = sign;
        createTime = System.currentTimeMillis();
        expiredTime = Long.valueOf(Long.toString(System.currentTimeMillis() + EXPIRATION_INCREMENT * 8 * 1000));
        setToken();
    }

    public Token(String sign, String username, String device) {
        this.sign = sign;
        this.username = username;
        this.device = device;
        createTime = System.currentTimeMillis();
        expiredTime = Long.valueOf(Long.toString(System.currentTimeMillis() + EXPIRATION_INCREMENT * 8 * 1000));
        setToken();
    }

    public static Token parseString(String sign, String tokenStr) {
        Token token = new Token();
        String json = JwtHelper.decodeAndVerify(tokenStr, new MacSigner(sign)).getClaims();
        Map<String, String> claims = null;
        try {
            claims = new ObjectMapper().readValue(json, Map.class);
            token.setUsername(claims.get("username"));
            token.setCreateTime(Long.valueOf(claims.get("createTime")));
            token.setExpiredTime(Long.valueOf(claims.get("expiredTime")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return token;
    }

    public void setToken() {
        Map<String, String> claims = new HashMap<String, String>() {{
            put("username", username);
            put("device", device);
            put("createTime", Long.toString(System.currentTimeMillis()));
            put("expiredTime", String.valueOf(expiredTime));
        }};
        String json = null;
        try {
            json = new ObjectMapper().writeValueAsString(claims);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        token = JwtHelper.encode(json, new MacSigner(sign)).getEncoded();
    }

    @Override
    public String toString() {
        return token;
    }

    public boolean refreshToken() {
        if (expiredTime + EXPIRATION_INCREMENT < EXPIRATION_MAXIMUM) {
            expiredTime += EXPIRATION_INCREMENT;
            return true;
        } else {
            return false;
        }
    }
}