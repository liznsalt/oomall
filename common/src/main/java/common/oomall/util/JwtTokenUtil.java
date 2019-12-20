package common.oomall.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JwtToken生成的工具类
 * JWT token的格式：header.payload.signature
 * header的格式（算法、token的类型）：
 * {"alg": "HS512","typ": "JWT"}
 * payload的格式（用户名、创建时间、生成时间）：
 * {"username":"wang","role":"user","created":1489079981393,"exp":1489684781}
 * signature的生成算法：
 * HMACSHA512(base64UrlEncode(header) + "." +base64UrlEncode(payload),secret)
 *
 * @author /
 */
public class JwtTokenUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);
    public static final String CLAIM_KEY_USERID = "userId";
    public static final String CLAIM_KEY_ROLEID = "roleId";
    public static final String CLAIM_KEY_CREATED = "created";
    public static final String CLAIM_KEY_EXP = "exp";

    private static String secret = "mall-secret";
    private static Long expiration = 60L * 60 * 24 * 30;
    private static String tokenHead = "Bearer";

    /**
     * 根据负责生成JWT的token
     */
    public static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 从token中获取JWT中的负载
     */
    public static Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            LOGGER.info("JWT格式验证失败:{}", token);
        }
        return claims;
    }

    public static Map<String, String> getMapFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        if (claims == null) {
            return null;
        }
        Claims jwt = JwtTokenUtil.getClaimsFromToken(token);
        String json = JacksonUtil.toJson(jwt);

        Map<String, String> map = new HashMap<>(5);
        map.put(CLAIM_KEY_USERID, JacksonUtil.parseString(json, CLAIM_KEY_USERID));
        map.put(CLAIM_KEY_ROLEID, JacksonUtil.parseString(json, CLAIM_KEY_ROLEID));
        map.put(CLAIM_KEY_CREATED, JacksonUtil.parseString(json, CLAIM_KEY_CREATED));
        map.put(CLAIM_KEY_EXP, JacksonUtil.parseString(json, CLAIM_KEY_EXP));
        return map;
    }

    /**
     * 生成token的过期时间
     */
    public static Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * 从token中获取登录用户名
     */
    public static String getUserNameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 验证token是否还有效
     *
     * @param token       客户端传入的token
     * @param userDetails 从数据库中查询出来的用户信息
     */
    public static boolean validateToken(String token, UserDetails userDetails) {
        String username = getUserNameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 判断token是否已经失效
     */
    public static boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * 从token中获取过期时间
     */
    public static Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 根据用户信息生成token
     */
    public static String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERID, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 当原来的token没过期时是可以刷新的
     *
     * @param token
     */
    public static String refreshHeadToken(String token) {
        if(StrUtil.isEmpty(token)){
            return null;
        }
        Claims claims = getClaimsFromToken(token);
        if (claims == null) {
            return null;
        }
        // FIXME 如果token已经过期，返回新token
        if (isTokenExpired(token)) {
            claims.put(CLAIM_KEY_CREATED, new Date());
        }
        return generateToken(claims);
    }

    /**
     * 判断token在指定时间内是否刚刚刷新过
     * @param token 原token
     * @param time 指定时间（秒）
     */
    public static boolean tokenRefreshJustBefore(String token, int time) {
        Claims claims = getClaimsFromToken(token);
        Date created = claims.get(CLAIM_KEY_CREATED, Date.class);
        Date refreshDate = new Date();
        //刷新时间在创建时间的指定时间内
        return refreshDate.after(created) && refreshDate.before(DateUtil.offsetSecond(created, time));
    }
}

