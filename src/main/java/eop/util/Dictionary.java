package eop.util;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 数据字典，用于页面编码转换等
 */
@Component
public class Dictionary {

    public static Map<String,Map<String,String>> dictionary;

    public void setDictionary(Map dictionary) {
        this.dictionary = dictionary;
    }

    public Map getDictionary() {
        return dictionary;
    }
}
