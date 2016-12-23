package eop.util;

import org.springframework.context.MessageSource;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class CustomizedProperty {
	
    public static final String numberChar = "0123456789";

    protected static final Pattern VAR_PATTERN = Pattern.compile("(\\$\\{)[^}]{1,}(\\})");

    private static MessageSource messageSource = null;

    public static MessageSource getMessageSource() {
        return messageSource;
    }

    public static void setMessageSource(MessageSource messageSource) {
        CustomizedProperty.messageSource = messageSource;
    }

    /**
     * 获取application.properties配置参数
     * @param key
     * @return value
     */
    public static String getValue(String key) {
        return CustomizedPropertyPlaceholderConfigurer.getContextProperty(key);
    }
    
    /**
     * 获取application.properties配置参数 并使用参数替换里面的${0} ${1}等
     * @param key
     * @param obj
     * @return
     */
    public static String getValue(String key,String... obj) {
    	String propertyValue = CustomizedPropertyPlaceholderConfigurer.getContextProperty(key);
    	Map<String, String> map = new HashMap<String, String>();
    	for (int i = 0; i < obj.length; i++) {
			map.put(i+"", obj[i]);
		}
        return CustomizedMessageSource.replaceTempleteVariables(propertyValue, map);
    }

}
