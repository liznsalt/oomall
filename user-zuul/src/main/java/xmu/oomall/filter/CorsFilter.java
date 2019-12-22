package xmu.oomall.filter;

import common.oomall.util.JwtTokenUtil;
import org.springframework.stereotype.Component;
import xmu.oomall.util.UriUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author liznsalt
 */
@Component
public class CorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setHeader("Access-Control-Expose-Headers", "Location");

        // @deprecated post 过滤器完成
        refreshToken(request, response);

        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void destroy() {}

    private void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader(UriUtil.TOKEN_NAME);
        String newToken = JwtTokenUtil.refreshHeadToken(token);
        response.setHeader(UriUtil.TOKEN_NAME, newToken);
    }

}
