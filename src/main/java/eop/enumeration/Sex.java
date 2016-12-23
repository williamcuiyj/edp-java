package eop.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum Sex implements EnumBase{
    MALE("男"), FEMALE("女");

    private String description;

    Sex(String description) {
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
        for (Sex c : Sex.values()) {
            map.put(c.name(), c.getDescription());
        }
        return map;
    }

    @Override
    public String toString(){
        return this.name();
    }
}
