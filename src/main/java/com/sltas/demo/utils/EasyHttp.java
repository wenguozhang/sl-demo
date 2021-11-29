package com.sltas.demo.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Description:简单的http请求发送
 * @author: wenguozhang 
 * @date:   2021年11月11日 上午11:41:01  
 */
public class EasyHttp {

	public static  String post(String postUrl,  String jsonData){
        OutputStreamWriter out = null;
        InputStream is = null;
        try {
            URL url = new URL(postUrl); // 创建连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
            connection.connect();
            out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            out.append(jsonData);
            System.out.println("开始请求");
            out.flush();
            out.close();
            is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String response = reader.readLine();
            System.out.println("请求结束");

            connection.disconnect();
            return response;
        }catch (Exception e) {
            System.out.println("处理异常:{"+e+"}");
            return null;
        } finally {
            try {
                is.close();
                out.close();
            } catch (Exception e) {
                System.out.println("数据流关闭异常");
            }
        }
    }
}
