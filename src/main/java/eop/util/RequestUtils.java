package eop.util;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {
    /**
     * 获取项目basePath
     * @param request
     */
    public static String getBasePath(HttpServletRequest request) {
    	StringBuffer sb = new StringBuffer();
    	sb.append(request.getScheme()).append("://").append(request.getServerName());
    	if (!requestIsOnStandardPort(request)) {
    		sb.append(":").append(request.getServerPort());
        }
    	sb.append(request.getContextPath());
    	if (!sb.toString().endsWith("/")) {
    		sb.append("/");
    	}
    	return sb.toString();
    }

    private static boolean requestIsOnStandardPort(final HttpServletRequest request) {
        final int serverPort = request.getServerPort();
        return serverPort == 80 || serverPort == 443;
    }
}
