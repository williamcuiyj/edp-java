package eop.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 发送请求类，支持get，post
 */
public class SendRequestUtil {

	private static final Logger LOG = LoggerFactory.getLogger(eop.util.SendRequestUtil.class);
	
	/**
	 * 发送get请求
	 * @param url
	 * @return
	 */
	public static String sendGet(String url) {
		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				result = EntityUtils.toString(entity, "UTF-8");
			} else {
				LOG.warn("get请求异常！");
			}
		} catch (ClientProtocolException e) {
			LOG.error("get请求ClientProtocolException异常！");
			e.printStackTrace();
		} catch (IOException e) {
			LOG.error("get请求IOException异常！");
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 发送post请求
	 * @param url
	 * @param param  post参数
	 * @return
	 */
	public static String sendPost(String url, Map<String, String> param) {
		String result = null;
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			if (param != null && !param.isEmpty()) {
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				for (String key : param.keySet()) {
					nvps.add(new BasicNameValuePair(key, param.get(key)));
				}
				httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
			}
			HttpResponse response = null;
		
			response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				result = EntityUtils.toString(entity, "UTF-8");
			} else {
				LOG.warn("post请求异常！");
			}
		} catch (ClientProtocolException e) {
			LOG.error("post请求ClientProtocolException异常！");
			e.printStackTrace();
		} catch (IOException e) {
			LOG.error("post请求IOException异常！");
			e.printStackTrace();
		}
		return result;
	}
	
	public static void main(String[] args) {
		System.out.println(sendGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx36c358cc4ac6bd2b&secret=fed482c214fb7736eae6129be32cf66b"));
	}
}
