package eop.util;

import eop.model.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class SessionUtil {


    public static final String CURRENT_REQUEST = "currentRequest";

    private static ThreadLocal SESSIONS = new ThreadLocal() {
        protected Object initialValue() {
            return new HashMap();
        }
    };

    public static void init(HttpServletRequest request) {
        Map map = (Map) SESSIONS.get();

        if (map != null) {
            if (!map.isEmpty()) {
                // LOG.warn("SESSIONS of SessionUtil is not empty!!!");
            }
            map.put(CURRENT_REQUEST, request);
        }
    }

    public static User getCurrentUser() {
        HttpSession httpSession = getSession();
        return getCurrentUser(httpSession);
    }


    public static User getCurrentUser(HttpSession httpSession) {
        User user = null;
        if (httpSession != null) {
            user = (User)httpSession.getAttribute("loginUserInfo");
        }
        return user;
    }


    public static Integer getCurrentUserId() {
        User u = getCurrentUser();
        if (u != null) {
            return u.getUserId();
        }
        return null;
    }

    public static HttpServletRequest getRequest() {
        return getRequest(true);
    }

    public static HttpServletRequest getRequest(boolean isThrowException) {
        HttpServletRequest req = (HttpServletRequest) ((Map) SESSIONS.get()).get(CURRENT_REQUEST);
        if (req == null) {
            if (isThrowException) {
                throw new RuntimeException();
            } else {
                return null;
            }
        }
        return req;
    }

    public static HttpSession getSession() {
        HttpServletRequest request = getRequest();
        HttpSession session = request.getSession();
        if (session == null) {
            throw new RuntimeException();
        }
        return session;
    }

    /**
     * 获取项目域名
     * @return
     */
    public static String getContextPath() {
        HttpServletRequest request = getRequest();
        if (request != null) {
            String url=request.getScheme()+"://";
            url += request.getHeader("host");

            url += request.getContextPath();
            return url;
        }
        return null;
    }
    
    /**
     * isAjaxRequest:判断请求是否为Ajax请求. 
     * @param request 请求对象
     * @return boolean
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }
    
    public static boolean isAjaxRequest() {
    	HttpServletRequest request = getRequest();
    	if (request == null) {
    		return false;
    	}
        return isAjaxRequest(request);
    }

    public static String getBasePath() {
    	return RequestUtils.getBasePath(null);
    }

    public static void clearSession(final HttpServletRequest request){
    	request.getSession().invalidate();
    }
    
    public static void invalidateLoginToken(HttpServletResponse response){
        Cookie cookie = new Cookie(CustomizedProperty.getValue("child.login.cookieName"), null);
        cookie.setMaxAge(0);
        cookie.setPath("/");  
        response.addCookie(cookie);
    }
    
    public static void initLoginToken(String TGT,HttpServletResponse response){
        Cookie cookie = new Cookie(CustomizedProperty.getValue("child.login.cookieName"), TGT);
        cookie.setMaxAge(Integer.parseInt(CustomizedProperty.getValue("child.login.overtime")));
        cookie.setPath("/");  
        response.addCookie(cookie);
    }
    
}
