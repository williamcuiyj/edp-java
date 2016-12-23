package eop.util;

import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomizedMessageSource {

    public static final String numberChar = "0123456789";

    protected static final Pattern VAR_PATTERN = Pattern.compile("(\\$\\{)[^}]{1,}(\\})");

    private static MessageSource messageSource;

    public MessageSource getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(MessageSource messageSource) {
        CustomizedMessageSource.messageSource = messageSource;
    }

    public static String replaceTempleteVariables(String s, Map properties) {
        if (s == null) {
            return s;
        }
        if (properties == null) {
            IllegalArgumentException e = new IllegalArgumentException("Cannot perform function"
                + " replaceVariables(String, Map) with null Map value.");
            //LOG.warn("", e);
            throw e;
        }
        int last = 0;
        Matcher m = VAR_PATTERN.matcher(s);
        StringBuffer b = null;
        while (m.find()) {
            if (b == null) {
                b = new StringBuffer();
            }
            String n = s.substring(m.start() + 2, m.end() - 1);
            String v = (String) properties.get(n);

            b.append(s.substring(last, m.start()));
            b.append(v);

            last = m.end();
        }
        if (last != 0) {
            b.append(s.substring(last));
            return b.toString();
        } else {
            return s;
        }
    }

    /**
     * 获取资源信息
     * 
     * @param code 国际化资源key
     * @param args 参数
     * @return
     */
    public static String getMessage(String code, Object... args) {
        return messageSource.getMessage(code, args, Locale.SIMPLIFIED_CHINESE);
    }
}
