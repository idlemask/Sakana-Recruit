package name.sakanacatcher.recruit.auth.authorization.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtil {

    private static final long EXPIRATION_ACCESS = 3600L;    // 1h = 3600s
    private static final long[] EXPIRATION_LEVEL = {24, 72, 168};
    @Value("${sign:sakana}")
    private String SECRET;

    // 生成token
    public String generateToken(String username) {
        String expiration = Long.toString(System.currentTimeMillis() + EXPIRATION_ACCESS * 8 * 1000);
        Map<String, String> claims = new HashMap<String, String>() {{
            put("username", username);
            put("createDate", Long.toString(System.currentTimeMillis()));
            put("expiredDate", expiration);
        }};
        String json = null;
        try {
            json = new ObjectMapper().writeValueAsString(claims);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String token = JwtHelper.encode(json, new MacSigner(SECRET)).getEncoded();
        return token;
    }

    public String refreshToken(String token) {
        Map<String, String> map = parseToken(token);
        long sta = Long.parseLong(map.get("createDate"));
        long exp = Long.parseLong(map.get("expiredDate"));
        int i = 0;
        while (sta < exp && i < EXPIRATION_LEVEL.length) {
            sta += EXPIRATION_LEVEL[i] * EXPIRATION_ACCESS * 1000;
        }
        map.replace("createDate", String.valueOf(sta));
        String json = null;
        try {
            json = new ObjectMapper().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return JwtHelper.encode(json, new MacSigner(SECRET)).getEncoded();
    }

    // 解析token
    public Map<String, String> parseToken(String token) {
        String json = JwtHelper.decodeAndVerify(token, new MacSigner(SECRET)).getClaims();
        Map<String, String> claims = null;
        try {
            claims = new ObjectMapper().readValue(json, Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return claims;
    }

    public String getUsername(String token) {
        return parseToken(token).get("username");
    }

    public long getExpiredDate(String token) {
        return Long.parseLong(parseToken(token).get("expiredDate"));
    }

    public long getCreateDate(String token) {
        return Long.parseLong(parseToken(token).get("createDate"));
    }

}
