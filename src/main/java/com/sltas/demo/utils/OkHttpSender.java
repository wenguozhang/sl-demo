package com.sltas.demo.utils;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Slf4j
public final class OkHttpSender {
	private static MediaType JSON = null;
	private static OkHttpClient OK_HTTP_CLIENT = null;
	static {
		JSON = MediaType.get("application/json; charset=utf-8");
		X509TrustManager x509TrustManager = initX509TrustManager();
		SSLSocketFactory socketFactory = initSSLSocketFactory(x509TrustManager);
		ConnectionPool connectionPool = initConnectionPool();
        OK_HTTP_CLIENT = new OkHttpClient.Builder()
        		.sslSocketFactory(socketFactory, x509TrustManager)
        		// 是否开启缓存
        		.retryOnConnectionFailure(false)
        		// 连接池
        		.connectionPool(connectionPool)
        		// 设置链接超时时间
        		.connectTimeout(3, TimeUnit.SECONDS)
        		// 设置写数据超时时间
        		.writeTimeout(120, TimeUnit.SECONDS)
        		// 设置读数据超时时间
        		.readTimeout(120, TimeUnit.SECONDS)
        		.build();
	}
	/*--------------------business_method--------------------*/
	/**
	 * json方式交互
	 * post请求
	 * @param url			请求的url
	 * @param json			请求的json字符串
	 * @return				返回json字符串
	 * @throws IOException	链接异常 
	 */
	public static String postJson(String url, String json) throws IOException {
		log.info("okhttp post request start:");
		log.info("request url [{}]",url);
		log.info("request body json [{}]",json);
		long startTime = System.currentTimeMillis();
		String result = postJsonExecute(url,json);
    	long endTime = System.currentTimeMillis();
    	log.info("okhttp post request finish. time consumed:[{}] milliseconds",endTime - startTime);
    	log.info("okhttp post request finish. response json:[{}]",result);
    	return result;
    }
	
	/**
	 * post请求发送json格式报文
	 * @param url	请求url
	 * @param json  数据json
	 * @return		响应报文
	 * @throws IOException 连接异常
	 */
	private static String postJsonExecute(String url, String json) throws IOException{
		RequestBody body = RequestBody.create(JSON, json);
    	Request request = new Request.Builder().url(url).post(body).build();
    	Response response = OK_HTTP_CLIENT.newCall(request).execute();
    	return response.body().string();
	}
	
	/*--------------------init_method--------------------*/
	/**
	 * okhttp创建ssl证书管理器
	 * @return	ssl证书管理器
	 */
	private static X509TrustManager initX509TrustManager() {
		return new X509TrustManager() {
			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) { }// NOSONAR
			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) { }// NOSONAR
			@Override
			public X509Certificate[] getAcceptedIssuers() {// NOSONAR
				return new X509Certificate[0];// NOSONAR
			}
        };
	}
	/**
	 * okhttp初始化连接池
	 * @return 连接池
	 */
	private static ConnectionPool initConnectionPool() {
		return new ConnectionPool(100, 10, TimeUnit.MINUTES);
	}
	/**
	 * okhttp初始化ssl连接工厂
	 * @param x509TrustManager	连接管理对象 {@link X509TrustManager}
	 * @return					连接工厂 {@link SSLSocketFactory}
	 */
	private static SSLSocketFactory initSSLSocketFactory(X509TrustManager x509TrustManager) {
		SSLContext sslContext;
		try {
			sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null,new TrustManager[]{x509TrustManager},new SecureRandom());
			return sslContext.getSocketFactory();
		} catch (NoSuchAlgorithmException | KeyManagementException e) {
			log.error("ERROR: okHttp initSSLSocketFactory failed. message:[{}]",e.toString());
		} 
		return null;
	}
}
