package name.sakanacatcher.recruit.common.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.MacSigner;

import java.util.HashMap;
import java.util.Map;

public class ServiceTokenUtil {
    public static String generateToken(String sign, String appName) {
        return appName + "Bearer " + JwtHelper.encode(appName, new MacSigner(sign)).getEncoded();
    }
    // parse token
    public static String parseToken(String sign, String token) {
        return JwtHelper.decodeAndVerify(token, new MacSigner(sign)).getClaims();
    }
}
