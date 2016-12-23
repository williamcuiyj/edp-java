package eop.util;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PublicCode {
    

    public static Map<String, Map<String, String>> codeMap = new HashMap<String, Map<String, String>>();

    public static Map<String, Map<String, String>> linkageMap = new HashMap<String, Map<String, String>>();

    public static Map<String, String> systemMap = new HashMap<String, String>();
    
    public static Map<String, Object> siteInfoMap = new HashMap<String, Object>();

    public static Map<String, Object> outTradeNoMap = new HashMap<String, Object>();//add by 周利军 2014-12-26 用于存放同步用的 outTradeNo

    public static ArrayList alipayBank = new ArrayList();

    public static final Integer IS_YES = 1;

    public static final Integer IS_NO = 0;


    public static Map<String, Map<String, String>> getLinkageMap() {
        return linkageMap;
    }

    public static void setLinkageMap(Map<String, Map<String, String>> linkageMap) {
        PublicCode.linkageMap = linkageMap;
    }

    public static Map<String, Object> getSiteInfoMap() {
        return siteInfoMap;
    }

    public static void setSiteInfoMap(Map<String, Object> siteInfoMap) {
        PublicCode.siteInfoMap = siteInfoMap;
    }

    public Map<String, Map<String, String>> getCodeMap() {
        return codeMap;
    }

    public void setCodeMap(Map<String, Map<String, String>> codeMap) {
        PublicCode.codeMap = codeMap;
    }

    public static Map<String, String> getSystemMap() {
        return systemMap;
    }

    public static void setSystemMap(Map<String, String> systemMap) {
        PublicCode.systemMap = systemMap;
    }

    public static String getNameChn(String type, String key) {

        return codeMap.get(type).get(key);
    }

    /**
     * 传递以逗号分隔的多个key(如：01,02)，返回对应的中文名(如：建筑,医学)，并以逗号分隔
     * 
     * @param type
     * @param keys
     * @return
     */
    public static String getNamesChns(String type, String keys, String split) {
        StringBuffer sb = new StringBuffer();
        if (StringUtils.hasText(keys)) {
            String[] arr = keys.split(",");
            if (arr != null) {
                for (String s : arr) {
                    if (sb.length() > 0) {
                        sb.append(split);
                    }
                    sb.append(getNameChn(type, s));
                }
            }
        }
        return sb.toString();
    }

    public static String getNamesChns(String type, String keys) {
        return getNamesChns(type, keys, Constants.SPLITTER_COMMA);
    }

}
