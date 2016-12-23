package eop.util;

import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	
    public static final String numberChar = "0123456789";

    protected static final Pattern VAR_PATTERN = Pattern.compile("(\\$\\{)[^}]{1,}(\\})");

    public static Integer toInteger(Object srcStr, Integer defaultValue) {
        try {
            if (srcStr != null && eop.util.StringUtils.isInt(srcStr)) {
                String s = srcStr.toString().replaceAll("(\\s)", "");
                return s.length() > 0 ? Integer.valueOf(s) : defaultValue;
            }
        } catch (Exception e) {
        }
        return defaultValue;
    }

    public static boolean isInt(Object srcStr) {
        if (srcStr == null) {
            return false;
        }
        String s = srcStr.toString().replaceAll("(\\s)", "");
        Pattern p = Pattern.compile("([-]?[\\d]+)");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    public static boolean isEmpty(Object srcStr) {
        return nvl(srcStr, "").trim().length() == 0
            || nvl(srcStr, "").equals("null");
    }

    public static String nvl(Object src, String alt) {
        if (src == null) {
            return alt;
        } else {
            return eop.util.StringUtils.nvl(src.toString(), alt);
        }
    }

    /**
     * 空字符串处理，空字符串返回指定字符串
     * @param srcStr
     * @param objStr
     * @return
     */
    public static String nvl(String srcStr, String objStr) {
        if (srcStr == null || 0 == srcStr.trim().length()
            || "null".equalsIgnoreCase(srcStr.trim())) {
            return objStr;
        } else {
            return srcStr;
        }
    }

    /**
     * 获取UUID字符串
     * @return
     */
    public static String getUUIDStr() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取UUID字符串，并拼接字符串类型
     * @param type
     * @return
     */
    @Deprecated
    public static String genBase64Type(String type) {
        if (!eop.util.StringUtils.isEmpty(type)) {
            String uuid = eop.util.StringUtils.getUUIDStr().substring(0, 6);
            return Base64.encode(uuid + "_" + type);
        }
        return null;
    }

    /**
     * BASE64解码工具
     * @param type
     * @return
     */
    public static String decodeBase64Type(String type) {
        String result = null;
        if (!eop.util.StringUtils.isEmpty(type)) {
            String s = Base64.decode(type);
            int n = s.indexOf("_");
            if (n > 0) {
                result = s.substring(n + 1);
            }
        }
        return result;
    }

    public static String trim(Object srcStr) {
        if (srcStr != null) {
            return srcStr.toString().trim();
        }
        return null;
    }

    public static boolean toBoolean(Object srcStr, boolean defaultValue) {
        try {
            if (srcStr != null) {
                return Boolean.parseBoolean(trim(srcStr.toString()));
            }
        } catch (Exception e) {

        }
        return defaultValue;
    }



    /**
     * 去掉所传字符串中的空格、回车、换行符、制表符
     * 
     * @param str 需要过滤的字符串
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 过滤掉字符串中的HTML标签
     * 
     * @param htmlStr 需要过滤的字符串
     * @return
     */
    public static String delHTMLTag(String htmlStr) {
    	if (isEmpty(htmlStr)) {
    		return "";
    	}
        //定义script的正则表达式   
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>";
        //定义style的正则表达式   
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>";
        //定义HTML标签的正则表达式   
        String regEx_html = "<[^>]+>";

        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        //过滤script标签 
        htmlStr = m_script.replaceAll("");

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        //过滤style标签
        htmlStr = m_style.replaceAll("");

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        //过滤html标签
        htmlStr = m_html.replaceAll("");
        //返回文本字符串   
        return htmlStr.trim();
    }

    /**
     * 驼峰命名转换
     */
    private static final char SEPARATOR = '_';

    /**
     * 将驼峰格式字符串转换为下划线格式字符串
     * @param s
     * @return
     */
    public static String toUnderlineName(String s) {
        if (s == null) {
            return null;
        }
        if (s.indexOf(".") != -1) {
        	s = s.split("\\.")[s.split("\\.").length-1];
        }
 
        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
 
            boolean nextUpperCase = true;
 
            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }
 
            if ((i >= 0) && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    if (i > 0) sb.append(SEPARATOR);
                }
                upperCase = true;
            } else {
                upperCase = false;
            }
 
            sb.append(Character.toLowerCase(c));
        }
 
        return sb.toString();
    }
 
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }
 
        s = s.toLowerCase();
 
        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
 
            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }
 
        return sb.toString();
    }
 
    public static String toCapitalizeCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = toCamelCase(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
    
    /**
     * 判断url 是否 存在于urls数组中
     * @param uri
     * @param urls
     * @return
     */
    public static boolean isUrlInArray(String uri, String[] urls) {
    	if (urls == null) {
    		return false;
    	}
    	for (String url : urls) {
            if (uri.contains(url)) {
                return true;
            }
        }
        return false;
    }

    //生成订单号
    public static String billNoCreate(String prefix) {
        String billNo = "";
        //根据时间获取一段随机数
        String str = getRandom();
        //随机生成从111到999直接的随机数
        int num = new Random().nextInt(888) + 111;
        billNo = str + String.valueOf(num);
        return prefix + billNo;
    }


    /**
     * 根据系统时间获得指定位数的随机数
     *
     * @return 获得的随机数
     */
    public static String getRandom() {

        Long seed = System.currentTimeMillis();// 获得系统时间，作为生成随机数的种子

        StringBuffer sb = new StringBuffer();// 装载生成的随机数

        Random random = new Random(seed);// 调用种子生成随机数

        for (int i = 0; i < 20; i++) {

            sb.append(numberChar.charAt(random.nextInt(numberChar.length())));
        }

        return sb.toString();

    }
    
    /**
     * 生成随机 6位数
     */
    public static String createSJPWD(){
    	String s = "";
    	for (int i = 0; i < 6; i++) {
    		s += (int)(Math.random()*10);
		}
    	return s;
    }
    
    /**
     * 生成随机密码
     * @return ["原密码"，"加密密码"]
     */
    public static String[] buildSJPwd(){
        String psw = createSJPWD();
        return new String[]{psw, MD5Util.MD5( "&*()"+psw+"!@#$%^" )};
    }

    /**
     * 检查注册手机号码格式是否正确
     *
     * @param mobile
     * @return  格式匹配返回true 不匹配返回false
     */
    public static boolean isMobile(String mobile) {
        String pattern = "^0?(1)[0-9]{10}$";
        if (mobile == null || "".equals(mobile) || !mobile.matches(pattern)) {
            return false;
        }
        return true;
    }

}
