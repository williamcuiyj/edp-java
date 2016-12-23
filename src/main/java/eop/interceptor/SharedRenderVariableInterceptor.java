package eop.interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 拦截器,用于存放渲染视图时需要的的共享变量
 * 
 */
public class SharedRenderVariableInterceptor extends HandlerInterceptorAdapter implements InitializingBean {
    static Log LOG = LogFactory.getLog(SharedRenderVariableInterceptor.class);

    //系统启动并初始化一次的变量
    private Map<String, Object> globalRenderVariables = new HashMap<String, Object>();

    @Override
    public void postHandle(HttpServletRequest request,
        HttpServletResponse response, Object handler,
        ModelAndView modelAndView) throws Exception {
        if (modelAndView == null) {
            return;
        }

        String viewName = modelAndView.getViewName();
        if (viewName != null && !viewName.startsWith("redirect:")) {
            modelAndView.addAllObjects(globalRenderVariables);
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
        HttpServletResponse response, Object handler) throws Exception {
        return super.preHandle(request, response, handler);
    }


    //在系统启动时会执行一次
    public void afterPropertiesSet() throws Exception {

    }
}
