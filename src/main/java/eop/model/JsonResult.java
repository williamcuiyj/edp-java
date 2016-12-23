package eop.model;

import org.json.simple.JSONObject;
import eop.util.StringUtils;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonResult {
	
	public static final String RESULTCODE_SUCCESS = "1";
	
	public static final String RESULTCODE_FAILURE = "0";

	public static final String RESULTCODE_NOLOGIN = "-1";

    private String resultCode;

    private String returnMsg;

    private List result;

    private Map resultMap;

    private Map map = new HashMap();

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
        map.put("resultCode", resultCode);
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
        map.put("returnMsg", returnMsg);
    }

    public List getResult() {
        return result;
    }

    public void setResult(List result) {
        this.result = result;
        map.put("result", result);
    }

    public Map getResultMap() {
        return resultMap;
    }

    public void setResultMap(Map resultMap) {
        this.resultMap = resultMap;
        map.put("resultMap", resultMap);
    }

    public void writeJSON(Writer out) {
        try {
            JSONObject.writeJSONString(map, out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void writeToReponse(Writer out, String resultCode, String returnMsg, Map resultMap) {
    	JsonResult jsonResult = new JsonResult();
        jsonResult.setResultCode(resultCode);
        if (!StringUtils.isEmpty(returnMsg)) {
        	jsonResult.setReturnMsg(returnMsg);
        }
        if (resultMap != null && !resultMap.isEmpty()) {
        	jsonResult.setResultMap(resultMap);
        }
        jsonResult.writeJSON(out);
    }

}
