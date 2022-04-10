package name.sakanacatcher.recruit.common.core.util;

import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.MacSigner;

public class ServiceTokenUtil {
    public static String generateToken(String sign, String appName) {
        return appName + "Bearer " + JwtHelper.encode(appName, new MacSigner(sign)).getEncoded();
    }
    // parse token
    public static String parseToken(String sign, String token) {
        return JwtHelper.decodeAndVerify(token, new MacSigner(sign)).getClaims();
    }
}
