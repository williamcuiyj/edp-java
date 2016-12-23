package eop.util;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 短信http接口的java代码调用示例
 * 基于Apache HttpClient 4.3
 *
 * @author songchao
 * @since 2015-04-03
 */

public class YunPianMessager {

    //查账户信息的http地址
    private static String URI_GET_USER_INFO = "http://yunpian.com/v1/user/get.json";

    //智能匹配模版发送接口的http地址
    private static String URI_SEND_SMS = "http://yunpian.com/v1/sms/send.json";

    //模板发送接口的http地址
    private static String URI_TPL_SEND_SMS = "http://yunpian.com/v1/sms/tpl_send.json";

    //发送语音验证码接口的http地址
    private static String URI_SEND_VOICE = "http://yunpian.com/v1/voice/send.json";

    //编码格式。发送编码格式统一用UTF-8
    private static String ENCODING = "UTF-8";

    private static final Logger LOG = LoggerFactory.getLogger(eop.util.YunPianMessager.class);

    public static void main(String[] args) throws IOException, URISyntaxException {

        //修改为您的apikey.apikey可在官网（http://www.yunpian.com)登录后用户中心首页看到
        String apikey = "9cdc4b0314171199079fd6c09d8eb34a";

        //修改为您要发送的手机号
        String mobile = "13198588430";

        /**************** 查账户信息调用示例 *****************/
        System.out.println(eop.util.YunPianMessager.getUserInfo(apikey));

        /**************** 使用智能匹配模版接口发短信(推荐) *****************/
        //设置您要发送的内容(内容必须和某个模板匹配。以下例子匹配的是系统提供的1号模板）
        String text = "【牛牛宝】您在牛牛宝的注册验证码是:1234。 此验证码用于牛牛宝注册验证，如非本人操作请忽略该条短信！";
        //发短信调用示例
        LOG.debug("YunPianMessager Send messenger begin");
        eop.util.YunPianMessager.sendSms(text, mobile);

        LOG.debug("YunPianMessager Send messenger end");

//        /**************** 使用指定模板接口发短信(不推荐，建议使用智能匹配模版接口) *****************/
//        //设置模板ID，如使用1号模板:【#company#】您的验证码是#code#
//        long tpl_id = 1;
//        //设置对应的模板变量值
//        //如果变量名或者变量值中带有#&=%中的任意一个特殊符号，需要先分别进行urlencode编码
//        //如code值是#1234#,需作如下编码转换
//        String codeValue = URLEncoder.encode("#1234#", ENCODING);
//        String tpl_value = "#code#=" + codeValue + "&#company#=云片网";
//        //模板发送的调用示例
//        System.out.println(YunPianMessager.tplSendSms(apikey, tpl_id, tpl_value, mobile));
//
//        /**************** 使用接口发语音验证码 *****************/
//        String code = "1234";
//        System.out.println(YunPianMessager.sendVoice(apikey, mobile ,code));
    }

    /**
     * 取账户信息
     *
     * @return json格式字符串
     * @throws IOException
     */

    public static String getUserInfo(String apikey) throws IOException, URISyntaxException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", apikey);
        return post(URI_GET_USER_INFO, params);
    }

    /**
     * 智能匹配模版接口发短信
     *
     * @param apikey apikey
     * @param text   　短信内容
     * @param mobile 　接受的手机号
     * @return json格式字符串
     * @throws IOException
     */

    public static String sendSms(String apikey, String text, String mobile) throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", apikey);
        params.put("text", text);
        params.put("mobile", mobile);
        return post(URI_SEND_SMS, params);
    }

    /**
     * 云片验证码短信发送接口
     * TODO:该接口暂未对返回数据进行校验
     * @param text
     * @param mobile
     * @return
     */
    public static boolean sendSms(String text, String mobile) {
        String apikey = "9cdc4b0314171199079fd6c09d8eb34a";
        try {
            String result = sendSms(apikey, text,mobile);
            JSONObject  dataJson=JSONObject.fromObject(result);
            if(dataJson.get("code").toString().equals("0")){
               return true;
            }else{
                LOG.warn("短信发送异常[{}],[{}],{}", new String[]{mobile,text,result}) ;
                return false;
            }
        } catch (Exception e){
            e.printStackTrace();
            LOG.warn("短信发送异常！[{}][{}]",mobile,text);
            return false;
        }
    }

    /**
     * 通过模板发送短信(不推荐)
     *
     * @param apikey    apikey
     * @param tpl_id    　模板id
     * @param tpl_value 　模板变量值
     * @param mobile    　接受的手机号
     * @return json格式字符串
     * @throws IOException
     */

    public static String tplSendSms(String apikey, long tpl_id, String tpl_value, String mobile) throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", apikey);
        params.put("tpl_id", String.valueOf(tpl_id));
        params.put("tpl_value", tpl_value);
        params.put("mobile", mobile);
        return post(URI_TPL_SEND_SMS, params);
    }

    /**
     * 通过接口发送语音验证码
     * @param apikey apikey
     * @param mobile 接收的手机号
     * @param code   验证码
     * @return
     */

    public static String sendVoice(String apikey, String mobile, String code) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", apikey);
        params.put("mobile", mobile);
        params.put("code", code);
        return post(URI_SEND_VOICE, params);
    }

    /**
     * 基于HttpClient 4.3的通用POST方法
     * @param url       提交的URL
     * @param paramsMap 提交<参数，值>Map
     * @return 提交响应
     */
    public static String post(String url, Map<String, String> paramsMap) {
        CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;
        try {
            HttpPost method = new HttpPost(url);
            if (paramsMap != null) {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
                    paramList.add(pair);
                }
                method.setEntity(new UrlEncodedFormEntity(paramList, ENCODING));
            }
            response = client.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return responseText;
    }
}
