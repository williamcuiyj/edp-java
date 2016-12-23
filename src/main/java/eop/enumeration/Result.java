package eop.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum Result implements EnumBase{
    SUCCESS("成功"), FAIL("失败");

    private String description;

    Result(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static Map<String, String> options() {
        Map<String, String> map = new HashMap<>();
        for (Result c : Result.values()) {
            map.put(c.name(), c.getDescription());
        }
        return map;
    }

    @Override
    public String toString(){
        return this.name();
    }
}
