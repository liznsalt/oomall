package xmu.oomall.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import common.oomall.api.CommonResult;
import common.oomall.util.IpAddressUtil;
import common.oomall.util.JacksonUtil;
import common.oomall.util.JwtTokenUtil;
import common.oomall.util.ResponseUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import xmu.oomall.domain.MallRole;
import xmu.oomall.service.PrivilegeService;
import xmu.oomall.service.RedisService;
import xmu.oomall.util.UriUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author liznsalt
 */
@Component
public class PreFilter extends ZuulFilter {
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PrivilegeService privilegeService;

    @Autowired
    private RedisService redisService;

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
        String method = request.getMethod();
        String uri = request.getRequestURI();
        LOGGER.info("uri:{}", request.getRequestURI());
        return !privilegeService.isWhiteUrl(method, uri);
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
    private void readTokenFromHeader(RequestContext requestContext, HttpServletRequest request) {
        // 从 header 中读取
        String headerToken = request.getHeader(UriUtil.TOKEN_NAME);
        LOGGER.debug("token:" + headerToken);
        if (StringUtils.isEmpty(headerToken)) {
            verifyEmptyToken(requestContext, request);
            return;
        } else {
            // 解析token，得到用户的id和role
            Map<String, String> tokenMap = JwtTokenUtil.getMapFromToken(headerToken);
            if (tokenMap != null) {
                LOGGER.debug("tokenMap:" + tokenMap);
                String roleId = tokenMap.get(JwtTokenUtil.CLAIM_KEY_ROLEID);
                // 先做redis判断
                if (roleId != null && hasMethodUrlInRedis(request, roleId)) {
                    LOGGER.info("method+url在redis中");
                    // 这个method+url近期被访问过，还在redis里面
                    //  修改header，加上userId和ip
                    LOGGER.info("网关通行，不存redis");
                    String userId = tokenMap.get(JwtTokenUtil.CLAIM_KEY_USERID);
                    UriUtil.changeHeader(requestContext, request, userId, roleId,
                            IpAddressUtil.getIpAddress(request), headerToken);
                    LOGGER.info("结束");
                    return;
                }
            }
            // tokenMap 为空 跳过 正常检查
        }
        // 正常校验token
        verifyToken(requestContext, request, headerToken);
    }

    private void verifyEmptyToken(RequestContext requestContext, HttpServletRequest request) {
        String method = request.getMethod();
        String uri = request.getRequestURI();
        if (!privilegeService.matchAuth(method, uri, MallRole.VISITOR)) {
            // 不在游客名单内
            LOGGER.info("不在游客名单内");
            setFailedResponse(requestContext, 660, "用户未登录");
        }
    }

    boolean hasMethodUrlInRedis(HttpServletRequest request, String roleId) {
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String methodUrl = UriUtil.generateRedisKey(method, uri);

        String redisRecord = redisService.get(methodUrl);
        return roleId.equals(redisRecord);
    }

    /**
     * TODO 校验token
     */
    private void verifyToken(RequestContext requestContext, HttpServletRequest request, String token) {
        String host = request.getRemoteHost();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        // 解析token，得到用户的id和role
        Map<String, String> tokenMap = JwtTokenUtil.getMapFromToken(token);
        LOGGER.debug("tokenMap:" + tokenMap);

        // 检验用户是否够权限访问此uri（method + uri）
        if (tokenMap == null) {
            LOGGER.info("tokenMap为空");
            setFailedResponse(requestContext, 660, "用户未登录");
            return;
        }
        // FIXME 用户角色
        String roleId = tokenMap.get(JwtTokenUtil.CLAIM_KEY_ROLEID);
        if (roleId == null) {
            LOGGER.info("roleId为空");
            // 角色都没有
            setFailedResponse(requestContext, 660, "用户未登录");
            return;
        }
        if (!privilegeService.matchAuth(method, uri, Integer.valueOf(roleId))) {
            // 不在用户权限名单内
            LOGGER.info("不在角色权限名单内");
            if (Integer.valueOf(roleId).equals(MallRole.USER)) {
                setFailedResponse(requestContext, 666, "用户无操作权限");
            } else {
                setFailedResponse(requestContext, 675, "管理员无权限");
            }
        } else {
            LOGGER.info("修改header");
            // 修改header，加上userId和ip
            String userId = tokenMap.get(JwtTokenUtil.CLAIM_KEY_USERID);
            UriUtil.changeHeader(requestContext, request, userId, roleId,
                    IpAddressUtil.getIpAddress(request), token);

            LOGGER.info("网关通行，存在redis里面");
            // 网关通行
            // 存在redis里面
            redisService.set(UriUtil.generateRedisKey(method, uri), roleId);
            redisService.expire(UriUtil.generateRedisKey(method, uri), UriUtil.METHOD_URL_TIME);

            LOGGER.info("结束");
        }
    }

    /**
     * 设置 401 无权限状态
     */
    private void setFailedResponse(RequestContext requestContext, int code, String message) {
        LOGGER.info("网关通行失败");
        requestContext.getResponse().setContentType("application/json;charset=UTF-8");
        requestContext.setSendZuulResponse(false);
        requestContext.set("sendForwardFilter.ran", true);
//        requestContext.setResponseStatusCode(200);
        requestContext.set("isSuccess", false);
        requestContext.setResponseBody(JacksonUtil.toJson(ResponseUtil.fail(code, message)));
    }

}
