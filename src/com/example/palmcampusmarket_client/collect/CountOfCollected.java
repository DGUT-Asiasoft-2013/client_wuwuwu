package com.example.palmcampusmarket_client.collect;

import java.io.IOException;

import com.example.palmcampusmarket_client.api.Server;
import com.example.palmcampusmarket_client.api.Server;
import com.example.palmcampusmarket_client.api.entity.Commodity;

import android.os.Handler;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class CountOfCollected {

	public static interface OnCountResultListener{
		void onResult(String result);
	};

	static Handler handler;
	
	public static void getCount(Commodity commodity, final OnCountResultListener listener){
		if(handler==null) handler = new Handler();

		Request requestLike = Server.requestBuilderWithCs("commodity/"+commodity.getId()+"/collected")
				.build();

		Server.getSharedClient().newCall(requestLike).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, final Response arg1) throws IOException {
				try{
					final String num = arg1.body().string();
					
					handler.post(new Runnable() {
						@Override
						public void run() {
							listener.onResult(num);		
						}
					});
					
				}catch(Exception ex){
					handler.post(new Runnable() {
						@Override
						public void run() {
							listener.onResult("???");		
						}
					});
				}
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				handler.post(new Runnable() {
					@Override
					public void run() {
						listener.onResult("???");		
					}
				});
			}
		});		
	}
}
