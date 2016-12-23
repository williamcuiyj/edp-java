package eop.util;

import eop.enumeration.Result;

import java.util.HashMap;
import java.util.Map;

public class ResultMsg {

    private String resultCode;

    private Result result;

    private Map<String, Object> resultMap;

    private String message;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public Map<String, Object> getResultMap() {
        if (resultMap==null){
            return new HashMap<>() ;
        }
        return resultMap;
    }

    public void setResultMap(Map<String, Object> resultMap) {
        this.resultMap = resultMap;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
