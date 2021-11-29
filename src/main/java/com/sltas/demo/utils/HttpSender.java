package com.sltas.demo.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.reflect.TypeToken;

/**
 * 发送HTTP请求
 * @author 陈伟
 * @createDate 2020年3月3日上午11:09:37
 */
public class HttpSender {
	private static Logger logger = LoggerFactory.getLogger(HttpSender.class);
	// 将最大连接数增加到
	public static final int MAX_TOTAL = 600;
	// 将每个路由基础的连接增加到
	public static final int MAX_ROUTE_TOTAL = 300;
	public static final int SOCKET_TIME = 60 * 1000;
	public static final int CONNECT_TIME = 5 * 1000;
	public static final int CONNECTION_REQUEST_TIMEOUT = 1 * 1000;
	private static final HttpSender instance = new HttpSender();
	private static HttpClientBuilder hcb = null;
	
	private HttpSender() {
	}
	
	static {
		//采用绕过验证的方式处理https请求
		SSLContext sslcontext = null;
		try {
			sslcontext = createIgnoreVerifySSL();
		} catch (KeyManagementException e) {
			logger.error("", e);
		} catch (NoSuchAlgorithmException e) {
			logger.error("", e);
		}
        // 设置协议http和https对应的处理socket链接工厂的对象
		SSLConnectionSocketFactory sSLConnectionSocketFactory = new SSLConnectionSocketFactory(sslcontext,SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
            .register("http", PlainConnectionSocketFactory.INSTANCE)
            .register("https", sSLConnectionSocketFactory)
            .build();
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        hcb = HttpClients.custom().setConnectionManager(connManager).setConnectionManagerShared(true);

		/**
		 * setMaxConnTotal:
		 * 		连接池最大并发连接数
		 * setMaxConnPerRoute:
		 * 		分配给同一个route(路由)最大的并发连接数。
		 * 		route：运行环境机器 到 目标机器的一条线路。
		 * 		举例来说，我们使用HttpClient的实现来分别请求 www.baidu.com 的资源和 www.bing.com 的资源那么他就会产生两个route。
		 */
		hcb.setMaxConnTotal(MAX_TOTAL).setMaxConnPerRoute(MAX_ROUTE_TOTAL);
	}

	public static HttpSender getInstance() {
		return instance;
	}
	
	/**
	 * 发送JSON请求和结果
	 * 成功才返回结果，否则返回null
	 * @author caiyonggang
	 * @createDate 2016年8月2日上午11:01:32
	 * @param url 请求地址
	 * @return JSONObject
	 */
	public static Map<String,Object> sendAndReciveJSON(String url,String contentStr) {
		// 固定数据添加
		Map<String,Object> result = null;
		try {
			//打印JSON请求参数
			//String fotmatStr = JSONStrFormat.format(contentObj.toString());
			//logger.info(fotmatStr);
			logger.info("sendAndReciveJSON=====url:"+url);
			logger.info("sendAndReciveJSON=====contentStr:"+contentStr);
			Map<String, String> res = sendPost(url,null,contentStr);
			String bodyStr = res.get("body");
			String status = res.get("status");
			//请求发送成功才能返回结果
			if("1".equals(status)){
				result = GsonBUILDERUtil.GSON_BUILDER_MAP_COMMON.fromJson(bodyStr,new TypeToken<Map<String,Object>>(){}.getType());
			}
		} catch (Exception e) {
			logger.error("请求数据出现异常:{}",e.getMessage());
		}
		return result;
	}


	/**
	 * 发送表单POST请求
	 * @author caiyonggang
	 * @createDate 2016年8月2日上午10:41:19
	 * @param url
	 * @param bodyParams 表单字段名值对：Map<String,String>
	 * @return
	 * Map<String,String>： 
	 * 	元素1 "status":"状态1成功0失败";
	 * 	元素2 "body":"返回信息"
	 * @throws
	 * @throws IOException
	 */
	public static Map<String,String> sendPost(String url,Map<String,String> headersParams,Map<String,String> bodyParams){
		logger.info("请求URL:"+url+" ;请求体（表单）:"+bodyParams);
		Map<String,String> resultMap = null;
		//创建post对象
		HttpPost post = createFormPost(url,headersParams,bodyParams);
		//发送post请求
		resultMap = sendHttpPost(post);
	    return resultMap;
	}
	
	/**
	 * 发送文本POST请求
	 * @author caiyonggang
	 * @createDate 2016年8月2日上午10:41:16
	 * @param url
	 * @param bodyParams
	 * @return
	 * Map<String,String>： 
	 * 	元素1 "status":"状态：1成功（HttpStatus=200），0失败";
	 * 	元素2 "body":"返回信息"
	 * @throws
	 * @throws IOException
	 */
	public static Map<String,String> sendPost(String url,Map<String,String> headersParams,String bodyParams) {
		//logger.info("请求URL:"+url+" ;请求体（文本）:"+JSONStrFormat.format(strParam));
		Map<String,String> resultMap = null;
		//创建post对象
		HttpPost post = createStringPost(url,headersParams,bodyParams);
		//发送post请求
		resultMap = sendHttpPost(post);
	    return resultMap;
	}
	
	/**
	 * 传入HttpPost对象，发送post请求，返回结果
	 * @author caiyonggang
	 * @createDate 2016年7月29日下午12:07:56
	 * @param post HttpPost对象
	 * @return 
	 * 	Map<String,String>： 
	 * 	元素1 "status":"状态：1成功（HttpStatus=200），0失败";
	 * 	元素2 "body":"返回信息"
	 * @throws
	 * @throws IOException
	 */
	private static Map<String,String> sendHttpPost(HttpPost post)  {
		String body = "";
		String status = "0";
		Map<String,String> resultMap = new HashMap<String,String>();//返回对象

		//创建自定义的httpclient对象,忽略证书校验
//		hcb = HttpClientBuilder.create();
		CloseableHttpClient httpClient = hcb.build();

		long startTime = System.currentTimeMillis();
		CloseableHttpResponse response = null;
		int statusCode = 0;
		long endTime = 0;
		try {
			response = httpClient.execute(post);

			statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				status = "1";
			}else{
				logger.error("请求失败:" + response.getStatusLine());
			}
			//post请求返回的字符串
			HttpEntity entity = response.getEntity();
			body = EntityUtils.toString(entity);
			System.out.println("返回数据："+body);
			EntityUtils.consume(entity);
		} catch (Exception e) {
			//自定义请求结果状态，1-成功，0-失败
			logger.error("请求失败:"+e);
		}finally{
			endTime = System.currentTimeMillis();
			resultMap.put("status", status);
			//释放链接
			if(response!=null){
				try {
					response.close();
				} catch (IOException e) {
					logger.error("response关闭失败:"+e);
				}
			}

			if (post != null) {
				post.releaseConnection();
			}

			//连接池使用的时候不能关闭连接，否则下次使用会抛异常 java.lang.IllegalStateException: Connection pool shut down
			/*
			if(httpClient!=null){
				try {
					httpClient.close();;
				} catch (IOException e) {
					logger.info("httpClient关闭失败:"+e);
				}
			}
			**/
		}
		logger.info("请求结果 statusCode:" + statusCode+" ; 耗时:" + (endTime - startTime)+"毫秒 ; 结果正文:"+body);
		resultMap.put("body", body);
		logger.info("请求结果 "+resultMap);
		return resultMap;
	}
	
	/**
	 * 创建表单形式的POST请求对象
	 * @author caiyonggang
	 * @createDate 2016年8月2日上午10:15:43
	 * @param url
	 * @param bodyParams 表单字段名值对：Map<String,String>
	 * @return
	 */
	private static HttpPost createFormPost(String url,Map<String,String> headersParams,Map<String,String> bodyParams) {
		HttpPost post = new HttpPost(url);
		post.addHeader("Content-type","application/json;charset=UTF-8");
		if(headersParams!=null){
			for(Entry<String,String> map :headersParams.entrySet()){
				post.addHeader(map.getKey(),map.getValue());
			}
		}
		// 对content做处理
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		for(Entry<String,String> map :bodyParams.entrySet()){
			list.add(new BasicNameValuePair(map.getKey(),map.getValue()));
		}
		try {
			post.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
			RequestConfig requestConfig = RequestConfig.custom()
					.setConnectTimeout(CONNECT_TIME).setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
					.setSocketTimeout(SOCKET_TIME).build();
			post.setConfig(requestConfig);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			// 这个错误可以忽略
		}
		return post;
	}
	
	/**
	 * 创建文本形式（JSON）的Post请求对象
	 * @author caiyonggang
	 * @createDate 2016年8月2日上午10:13:42
	 * @param url 请求地址
	 * @param bodyParams 请求文本（JSON）
	 * @return
	 */
	private static HttpPost createStringPost(String url,Map<String,String> headersParams,String bodyParams){
		HttpPost post = new HttpPost(url);
		post.addHeader("Content-type","application/json;charset=UTF-8");
		if(headersParams!=null){
			for(Entry<String,String> map :headersParams.entrySet()){
				post.addHeader(map.getKey(),map.getValue());
			}
		}
	    post.setEntity(new StringEntity(bodyParams, Charset.forName("UTF-8")));
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(CONNECT_TIME).setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
				.setSocketTimeout(SOCKET_TIME).build();
		post.setConfig(requestConfig);
		return post;
	}
	
	/**
	 * SSLContext绕过验证
	 * 	
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 */
	private static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
		SSLContext sc = SSLContext.getInstance("TLS");
		
		// 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
		X509TrustManager trustManager = new X509TrustManager() {
			@Override
			public void checkClientTrusted(
					java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
					String paramString) throws CertificateException {
			}

			@Override
			public void checkServerTrusted(
					java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
					String paramString) throws CertificateException {
			}

			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		sc.init(null, new TrustManager[] { trustManager }, null);
		return sc;
	}
	
}
