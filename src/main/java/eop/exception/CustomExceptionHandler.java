package eop.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 统一异常处理程序
 */
public class CustomExceptionHandler implements HandlerExceptionResolver {

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
        Exception ex) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("ex", ex);
        // 根据不同错误转向不同页面
        if (ex instanceof BusinessException) {
            return new ModelAndView("redirect:/errors/404.jsp", model);
        } else if (ex instanceof ParameterException) {
            return new ModelAndView("redirect:/errors/404.jsp", model);
        } else {
            return new ModelAndView("redirect:/errors/404.jsp", model);
        }
    }
    public String getTrace(Throwable t) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        StringBuffer buffer = stringWriter.getBuffer();
        return buffer.toString();
    }
}
