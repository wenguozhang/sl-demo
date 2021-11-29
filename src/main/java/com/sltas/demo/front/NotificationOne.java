package com.sltas.demo.front;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.sltas.demo.utils.EasyHttp;

/**
 * 通知按照状态重发，每次运行需要修改url和cycleNum
 * @Description:TODO(用一句话描述该文件做什么) 
 * @author: wenguozhang 
 * @date:   2021年7月20日 上午10:24:35
 */
public class NotificationOne {
	public static void main(String[] args) {
		//cycleNum为循环次数，每次循环最多重发200条
		//select concat(merchant_id,'-',plat_no,'-',notify_serial_number) from order_status_notify where 
		String[] rowData = {""};
		for(int i=0; i< rowData.length; i++) {
			String[] str = rowData[i].split("-");
			String merId = str[0];
			String platNo = str[1];
			String NoticeSeq = str[2];
			String url = "https://front.mp.sinojy.cn/systemRefresh/notification/"+merId+"/"+platNo+"/synBill/"+NoticeSeq;
			System.out.println(url+"-----"+i);
			System.out.println(EasyHttp.post(url,null));
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
