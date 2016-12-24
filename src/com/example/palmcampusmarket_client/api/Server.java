package com.example.palmcampusmarket_client.api;


import java.net.CookieManager;
import java.net.CookiePolicy;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class Server {
	static OkHttpClient client;

	static { 
		CookieManager cookieManager = new CookieManager();
		cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

		client = new OkHttpClient.Builder()
				.cookieJar(new JavaNetCookieJar(cookieManager))
				.build();
	}

	public static OkHttpClient getSharedClient(){
		return client;
	}




	public static String serverAddress = "http://172.27.15.10:8080/membercenter/";


	public  static Request.Builder requestBuilderWithWallet(String wallet){
		return new Request.Builder()
				.url(serverAddress+"wallet/"+wallet);
	}

	public static Request.Builder requestBuilderWithApi(String api){
		return new Request.Builder()

				.url(serverAddress+"api/"+api);
	}

	public static Request.Builder requestBuilderWithCs(String cs){
		return new Request.Builder()
				.url(serverAddress+"cs/"+cs);
	}


}




