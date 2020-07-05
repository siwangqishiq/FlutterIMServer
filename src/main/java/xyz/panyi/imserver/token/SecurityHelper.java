package xyz.panyi.imserver.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.netty.util.internal.StringUtil;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.auth0.jwt.algorithms.Algorithm.HMAC256;

public class SecurityHelper {
    public static final String ACCOUNT_SCRECT = "xxxim_server";
    public static final long ACCOUNT_TOKEN_EXPIRE_TIME = 24 * 60 * 60 * 1000; // 签发的token超时时间

    private static JWTVerifier verifier;

    static {
        try {
            verifier = JWT.require(HMAC256(ACCOUNT_SCRECT)).build();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private static final String KEY_ACCOUNT = "_account";
    private static final String KEY_PWD = "_pwd";

    public static String createToken(String account, String pwd) {
        if (StringUtil.isNullOrEmpty(account) || StringUtil.isNullOrEmpty(pwd))
            return null;

        Map<String, Object> headMap = new HashMap<String, Object>();
        headMap.put(KEY_ACCOUNT, account);
        headMap.put(KEY_PWD, pwd);

        String token = null;
        try {
            token = JWT.create().withHeader(headMap)
                    .withExpiresAt(new Date(System.currentTimeMillis() + ACCOUNT_TOKEN_EXPIRE_TIME))
                    .sign(HMAC256(ACCOUNT_SCRECT));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return token;
    }

    public static String createToken(long uid, String pwd) {
        return createToken(String.valueOf(uid) , pwd);
    }

    /**
     * 验证Token
     * @param token
     * @return
     */
    public static boolean vertifyToken(final String token ,final ICheck check) {
        if (StringUtil.isNullOrEmpty(token))
            return false;

        try {
            DecodedJWT decodeJWT = verifier.verify(token);
            final String account = decodeJWT.getHeaderClaim(KEY_ACCOUNT).asString();
            final String pwd = decodeJWT.getHeaderClaim(KEY_PWD).asString();

            if(check != null){
                return check.validateAccount(token , account , pwd);
            }else{
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public interface ICheck{
        boolean validateAccount(final String token , final String uid , final String pwd);
    }

    /**
     * 从Token 中获取用户标识
     * @param token
     * @return
     */
    public static String getAccountFromToken(String token) {
        if (vertifyToken(token , null)) {
            DecodedJWT decodeJWT = verifier.verify(token);
            return decodeJWT.getHeaderClaim(KEY_ACCOUNT).asString();
        }
        return null;
    }
}
