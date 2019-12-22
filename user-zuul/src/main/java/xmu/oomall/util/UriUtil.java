package xmu.oomall.util;

import com.netflix.zuul.context.RequestContext;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liznsalt
 */
public class UriUtil {
    /**
     * @deprecated
     * 自检验key
     */
    public static String PASS_KEY = "pass-key-oomall-3130199";
    /**
     * @deprecated
     * 自检验value
     */
    public static String PASS_VALUE = "oomall-pass-key-12345313";

    /**
     * @see UriUtil#generateRedisKey(String, String)
     * 存method+url的前缀
     */
    public static final String METHOD_URL_PREFIX = "Oomall_Method_Url:";
    /**
     * 存放时间 1min
     */
    public static final Long METHOD_URL_TIME = 60L * 10;

    public static final String WHITE_URL_KEY_PREFIX = "Oomall_White_Urls:";
    public static final Long WHITE_URL_TIME = 12L * 60 * 60;

    public static final String TOKEN_NAME = "authorization";

    /**
     * @deprecated
     * @param method 方法
     * @param uri url
     * @return /
     */
    public static String generateMethodAndUri(String method, String uri) {
        return (method.toLowerCase() + " " + uri).trim();
    }

    public static void changeHeader(RequestContext requestContext,
                                    HttpServletRequest request,
                                    String userId,
                                    String roleId,
                                    String ip,
                                    String token) {
        // 1 修改参数
//        Map<String, List<String>> map = new HashMap<>(5);
//        map.put("opId", Collections.singletonList(userId));
//        map.put("roleId", Collections.singletonList(roleId));
//        map.put("ip", Collections.singletonList(ip));
//        requestContext.setRequestQueryParams(map);

        // 2 修改头部
        requestContext.addZuulRequestHeader(TOKEN_NAME, token);
        requestContext.addZuulRequestHeader("token", token);
        requestContext.addZuulRequestHeader("userId", userId);
        System.out.println(userId);
        requestContext.addZuulRequestHeader("roleId", roleId);
        requestContext.addZuulRequestHeader("ip", ip);
    }

    /**
     * 生成存放在redis中的method+url
     */
    public static String generateRedisKey(String method, String url) {
        return METHOD_URL_PREFIX + method + url;
    }
}
