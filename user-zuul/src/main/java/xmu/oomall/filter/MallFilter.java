package xmu.oomall.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
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
        return -100;
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
        return true;
    }

    /**
     * 自定义的过滤器逻辑，当shouldFilter()返回true时会执行。
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String host = request.getRemoteHost();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        LOGGER.info("Remote host:{},method:{},uri:{}", host, method, uri);
        // 提取token，进行权限判断
        readTokenFromHeader(requestContext, request);
        return null;
    }

    /**
     * 从 header 中读取 token 并校验
     */
    private void readTokenFromHeader(RequestContext requestContext,
                                     HttpServletRequest request) {
        //从 header 中读取
        String headerToken = request.getHeader("token");
        if (StringUtils.isEmpty(headerToken)) {
            verifyEmptyToken(requestContext, request);
        } else {
            verifyToken(requestContext, request, headerToken);
        }
    }


    private void verifyEmptyToken(RequestContext requestContext,
                                  HttpServletRequest request) {
        String method = request.getMethod();
        String uri = request.getRequestURI();
        if (!privilegeService.matchAuth(method, uri, 0)) {
            // 不在游客名单内
            System.out.println("不在游客名单内");
            setUnauthorizedResponse(requestContext);
        }
    }

    /**
     * TODO 校验token
     */
    private void verifyToken(RequestContext requestContext,
                             HttpServletRequest request,
                             String token) {
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
        // FIXME 用户角色
        String roleId = tokenMap.get(JwtTokenUtil.CLAIM_KEY_ROLEID);
        if (roleId == null) {
            // 角色都没有
            setUnauthorizedResponse(requestContext);
            return;
        }
        if (!privilegeService.matchAuth(method, uri, Integer.valueOf(roleId))) {
            // 不在用户权限名单内
            System.out.println("不在角色权限名单内");
            setUnauthorizedResponse(requestContext);
        } else {
            // 验证以及刷新token
            String newToken = JwtTokenUtil.refreshHeadToken(token);
            if (newToken == null) {
                // 没有钥匙？
                setUnauthorizedResponse(requestContext);
                return;
            }

            // 修改header，加上userId和ip
            String userId = tokenMap.get(JwtTokenUtil.CLAIM_KEY_USERID);
            UriUtil.changeHeader(requestContext, request,
                    userId, roleId,
                    IpAddressUtil.getIpAddress(request), newToken);
        }
    }

    /**
     * 设置 401 无权限状态
     */
    private void setUnauthorizedResponse(RequestContext requestContext) {
        requestContext.getResponse().setContentType("text/html;charset=UTF-8");
        requestContext.setSendZuulResponse(false);
        requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        requestContext.set("isSuccess", false);
        CommonResult result = CommonResult.unauthorized(null);
        requestContext.setResponseBody(result.toString());
    }

}
