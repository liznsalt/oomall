package xmu.oomall.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import common.oomall.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xmu.oomall.util.UriUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author liznsalt
 */
@Component
public class PostFilter extends ZuulFilter {
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        HttpServletResponse response = requestContext.getResponse();
        // 得到和刷新token
        LOGGER.info("刷新token");
        String token = request.getHeader(UriUtil.TOKEN_NAME);
        String newToken = JwtTokenUtil.refreshHeadToken(token);
        response.setHeader(UriUtil.TOKEN_NAME, newToken);
        LOGGER.info("刷新结束");

        return null;
    }
}
