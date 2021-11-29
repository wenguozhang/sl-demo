package com.sltas.demo.front;

import com.sltas.demo.utils.EasyHttp;

/**
 * 通知按照状态重发，每次运行需要修改url和cycleNum
 * @Description:TODO(用一句话描述该文件做什么) 
 * @author: wenguozhang 
 * @date:   2021年7月20日 上午10:24:35
 */
public class Notification {
	public static void main(String[] args) {
		//cycleNum为循环次数，每次循环最多重发200条
		int cycleNum = 253;
		for(int i=0; i< cycleNum; i++) {
			System.out.println("-----------------"+i);
//			String url = "http://10.10.4.81:9101/systemRefresh/list/notification/112551/standard/synBill/E";
			String url = "https://front.mp.sinojy.cn/systemRefresh/list/notification/112462/standard/synBill/O"; 
//			String url = "http://frontzw.zeevor.com/systemRefresh/list/notification/112555/standard/synBill/F";
			System.out.println(EasyHttp.post(url,null));
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
	}
	
}
