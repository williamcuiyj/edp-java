package eop.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;
import eop.util.*;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 网站顶层拦截器 主要处理访问安全控制 用户安全数据处理
 */
public class SecurityFilter extends OncePerRequestFilter {
    private static final Logger LOG = LoggerFactory.getLogger(SecurityFilter.class);
    public static final String[] REQUEST_URL_STREING_EXCLUDE = {
            "plugin", "resource", "static", "uploads", "errors","favicon.ico","favicon" };

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession();
        // 初始化SessionUtil
        SessionUtil.init(request);
        request.setAttribute("ctx", request.getContextPath());
        request.setAttribute("fullPath", RequestUtils.getBasePath(request));
        filterChain.doFilter(request, response);
    }

    private boolean includeExclusiveRQURL(String currentURI, String[] urls) {
        for (String uri : urls) {
            if (currentURI.indexOf(uri) != -1) {
                return true;
            }
        }
        return false;
    }

}
