package xmu.oomall.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import common.oomall.api.CommonResult;
import common.oomall.component.ERole;
import common.oomall.util.CookieUtil;
import common.oomall.util.IpAddressUtil;
import common.oomall.util.JacksonUtil;
import common.oomall.util.JwtTokenUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import xmu.oomall.service.PrivilegeService;
import xmu.oomall.util.UriUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author liznsalt
 */
@Component
public class MallFilter extends ZuulFilter {
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PrivilegeService privilegeService;

    //无权限时的提示语

    private static final String INVALID_TOKEN = "invalid token";
    private static final String INVALID_USERID = "invalid userId";

    /**
     * 过滤器类型，有pre、routing、post、error四种。
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器执行顺序，数值越小优先级越高。
     */
    @Override
    public int filterOrder() {
        return 1;
    }

    /**
     * 是否进行过滤，返回true会执行过滤。
     * 有白名单
     */
    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        LOGGER.info("uri:{}", request.getRequestURI());
        // 不在白名单内则需要过滤
        return !privilegeService.getWhiteList().contains(request.getRequestURI());
    }

    /**
     * 自定义的过滤器逻辑，当shouldFilter()返回true时会执行。
     */
    @Override
    public Object run() {
        System.out.println("不在白名单内");
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String host = request.getRemoteHost();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        LOGGER.info("Remote host:{},method:{},uri:{}", host, method, uri);
        // 头部提取token，进行权限判断
        readTokenFromHeader(requestContext, request);
        return null;
    }

    /**
     * 从 header 中读取 token 并校验
     */
    private void readTokenFromHeader(RequestContext requestContext, HttpServletRequest request) {
        //从 header 中读取
        String headerToken = request.getHeader("token");
        if (StringUtils.isEmpty(headerToken)) {
            setUnauthorizedResponse(requestContext);
        } else {
            verifyToken(requestContext, request, headerToken);
        }
    }

    /**
     * TODO 校验token
     */
    private void verifyToken(RequestContext requestContext, HttpServletRequest request, String token) {
        String host = request.getRemoteHost();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String methodAndUri = UriUtil.generateMethodAndUri(method, uri);
        // 解析token，得到用户的id和role
        Map<String, String> tokenMap = JwtTokenUtil.getMapFromToken(token);
        System.out.println("tokenMap:" + tokenMap);

        // 检验用户是否够权限访问此uri（method + uri）
        if (tokenMap == null) {
            setUnauthorizedResponse(requestContext);
            return;
        }
        // FIXME 判断是用户还是管理员
        String role = tokenMap.get(JwtTokenUtil.CLAIM_KEY_ROLE);
        System.out.println("role:" + role);
        if (ERole.USER.getName().equals(role)) {
            // 用户
            if (!privilegeService.matchAuth(method, uri, ERole.USER)) {
                // 不在用户权限名单内
                System.out.println("不在用户权限名单内");
                setUnauthorizedResponse(requestContext);
                return;
            }
        } else if (ERole.ADMIN.getName().equals(role)) {
            // 管理员
            if (!privilegeService.matchAuth(method, uri, ERole.ADMIN)) {
                // 不在管理员权限名单内
                System.out.println("不在管理员权限名单内");
                setUnauthorizedResponse(requestContext);
                return;
            }
        } else {
            // 没有这个角色？
            setUnauthorizedResponse(requestContext);
            return;
        }

        // 验证以及刷新token
        String newToken = JwtTokenUtil.refreshHeadToken(token);
        if (newToken == null) {
            // 没有钥匙？
            setUnauthorizedResponse(requestContext);
            return;
        }

        // 修改header，加上userId和ip
        String userId = tokenMap.get(JwtTokenUtil.CLAIM_KEY_USERID);
        UriUtil.changeHeader(requestContext, request, userId, IpAddressUtil.getIpAddress(request), newToken);
    }

    /**
     * 设置 401 无权限状态
     */
    private void setUnauthorizedResponse(RequestContext requestContext) {
        requestContext.setSendZuulResponse(false);
        requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());

        CommonResult result = CommonResult.unauthorized(null);
        String res = JacksonUtil.toJson(result);

        requestContext.setResponseBody(res);
    }

}
