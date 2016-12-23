package eop.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
	
    /**
     * 正则表达式获取匹配内容
     * @param content 内容
     * @param regex   正则表达式
     * @param g1      取组
     * @param g2
     * @return
     */
    public static List<String> getMatcherStr(String content,String regex, Integer g1,Integer g2){
    	List<String> matcherStrs = new ArrayList<String>();
    	if (null == content || null == regex) {
    		return matcherStrs;
    	}
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        while(matcher.find()){
            matcherStrs.add(matcher.group(g1));
            if(null!=g2){
                matcherStrs.add(matcher.group(g2));
            }
        }
        
        return matcherStrs;
    }
    
    /**
     * 正则验证
     * @param str	需要验证的字符串
     * @param regex	正则表达式	使用内置，请传null
     * @param type	内置验证  1:手机号
     * @return
     */
    public static boolean matcher(String str, String regex, Integer type) {
        if (eop.util.StringUtils.isEmpty(str)) {
    		return false;
    	}

    	if (eop.util.StringUtils.isEmpty(regex)) {
    		if (StringUtils.isEmpty(type)) {
    			return false;
    		}
    		switch (type) {
			case 1:
				return str.matches("1\\d{10}");//手机号验证
			default:
				break;
			}
    	}else {
    		return str.matches(regex);
    	}
    	
    	return false;
    }
    
    public static void main(String[] args) {
		System.out.println(matcher("18349364674", null, 1));
	}
}
