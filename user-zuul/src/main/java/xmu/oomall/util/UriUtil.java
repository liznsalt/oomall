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
        Map<String, List<String>> map = new HashMap<>(5);
        map.put("opId", Collections.singletonList(userId));
        map.put("ip", Collections.singletonList(ip));
        requestContext.setRequestQueryParams(map);

        // 2 修改头部
        requestContext.addZuulRequestHeader("token", token);
        requestContext.addZuulRequestHeader("userId", userId);
        requestContext.addZuulRequestHeader("roleId", roleId);
        requestContext.addZuulRequestHeader("ip", ip);
    }
}
