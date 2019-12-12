package xmu.oomall.util;

import com.netflix.zuul.context.RequestContext;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liznsalt
 */
public class UriUtil {
    public static String generateMethodAndUri(String method, String uri) {
        return (method.toLowerCase() + " " + uri).trim();
    }

    public static void changeHeader(RequestContext requestContext, HttpServletRequest request,
                                    String userId, String ip, String token) {
        System.out.println("userId:" + userId);
        System.out.println("ip:" + ip);
        System.out.println("token:" + token);

        // 1 修改参数
//        Map<String, List<String>> map = new HashMap<>(5);
//        map.put("userId", Collections.singletonList(userId));
//        map.put("ip", Collections.singletonList(ip));
//        requestContext.setRequestQueryParams(map);

        // 2 修改头部
        requestContext.addZuulRequestHeader("token", token);
        requestContext.addZuulRequestHeader("userId", userId);
        requestContext.addZuulRequestHeader("ip", ip);
    }
}
