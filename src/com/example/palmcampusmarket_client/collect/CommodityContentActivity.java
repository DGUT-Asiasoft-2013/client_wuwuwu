package com.example.palmcampusmarket_client.collect;

import java.io.IOException;

import com.example.palmcampusmarket_client.PurchaseActivity;
import com.example.palmcampusmarket_client.R;
import com.example.palmcampusmarket_client.api.Server;
import com.example.palmcampusmarket_client.api.entity.Commodity;
import com.example.palmcampusmarket_client.collect.CountOfCollected.OnCountResultListener;
import com.example.palmcampusmarket_client.fragment.ImageView;
import com.example.palmcampusmarket_client.fragment.pages.HomeListFragment;

import android.R.integer;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.Response;

public class CommodityContentActivity extends Activity {
	//	TextView btnBack;
	//	TextView btnCollect;
	//	Button btnBuy;		
	//
	//	@Override
	//	protected void onCreate(Bundle savedInstanceState) {
	//		// TODO Auto-generated method stub
	//		super.onCreate(savedInstanceState);
	//
	//		setContentView(R.layout.activity_commodity_content);
	//
	//		final Commodity commodity = (Commodity) getIntent().getSerializableExtra("commodity");
	//
	//
	//
	//		btnBack = (TextView) findViewById(R.id.btn_back_commodity_content);
	//
	//		btnBack.setOnClickListener(new OnClickListener() {
	//
	//			@Override
	//			public void onClick(View v) {
	//				finish();
	//
	//			}
	//		});
	//
	//		btnCollect = (TextView) findViewById(R.id.btn_collect_commodity_content);
	//
	//		checkIsCollected(commodity, new OnCheckResultListener() {
	//
	//			@Override
	//			public void onResult(String result) {
	//				if(result.equals("false")){
	//					btnCollect.setText("☆");
	//
	//				}else{
	//					btnCollect.setText("★");
	//				}		
	//
	//			}
	//		});
	//
	//		btnBuy = (Button) findViewById(R.id.btn_buy);
	//
	//		ImageView image;
	//		TextView name;
	//
	//		final TextView countCollected;
	//
	//		TextView seller;
	//		TextView num;
	//		TextView price;
	//		TextView describe;
	//
	//
	//		image = (ImageView) findViewById(R.id.image_commodity_content);
	//		image.load(Server.serverAddress + commodity.getCommImage());
	//
	//		name = (TextView) findViewById(R.id.name_commodity_content);
	//		name.setText(commodity.getCommName());
	//
	//		countCollected = (TextView) findViewById(R.id.collected_commodity_content);
	//
	//		CountOfCollected.getCount(commodity, new OnCountResultListener() {
	//
	//			@Override
	//			public void onResult(String result) {
	//				// TODO Auto-generated method stub
	//				countCollected.setText(result + "收藏");
	//
	//			}
	//		});
	//
	//
	//
	//		seller = (TextView) findViewById(R.id.seller_commodity_content);
	//		seller.setText(commodity.getUser().getNickname());
	//		seller.setOnClickListener(new OnClickListener() {
	//			
	//			@Override
	//			public void onClick(View v) {
	//				// TODO Auto-generated method stub
	//			}
	//		});
	//
	//		num = (TextView) findViewById(R.id.num_commodity_content);
	//		String s = String.valueOf(commodity.getCommNumber());
	//		num.setText(s);
	//
	//		price = (TextView) findViewById(R.id.price_commodity_content);
	//		price.setText(commodity.getCommPrice());
	//
	//		describe = (TextView) findViewById(R.id.describe_commodity_content);
	//		describe.setText(commodity.getCommDescribe());
	//
	//
	//
	//		btnCollect.setOnClickListener(new OnClickListener() {
	//
	//			@Override
	//			public void onClick(View v) {
	//				checkIsCollected(commodity,new OnCheckResultListener() {
	//
	//					@Override
	//					public void onResult(String result) {
	//						// TODO Auto-generated method stub
	//
	//						if(result.equals("false")){
	//							btnCollect.setText("☆");
	//
	//						}else{
	//							btnCollect.setText("★");
	//						}		
	//						changeCollected(result,commodity);
	//
	//					}
	//				});
	//
	//			}
	//		});		
	//	}
	//
	//	protected void callSeller(String telephone) {
	//		// TODO Auto-generated method stub
	//		
	//	}
	//
	//	@Override
	//	protected void onResume() {
	//		// TODO Auto-generated method stub
	//		super.onResume();
	//	}
	//
	//	void changeCollected(String checkResult,final Commodity commodity){
	//
	//		String param = String.valueOf(checkResult.equals("false"));
	//		MultipartBody body = new MultipartBody
	//				.Builder()
	//				.addFormDataPart("collect",param)
	//				.build();
	//
	//		Request request = Server.requestBuilderWithCs("commodity/" + commodity.getId() + "/collect")
	//				.post(body)
	//				.build();
	//
	//		Server.getSharedClient().newCall(request).enqueue(new Callback() {
	//
	//			@Override
	//			public void onResponse(Call arg0, Response arg1) throws IOException {
	//				runOnUiThread(new Runnable() {					
	//					@Override
	//					public void run() {
	//						checkIsCollected(commodity, new OnCheckResultListener() {
	//
	//							@Override
	//							public void onResult(String result) {
	//								if(result.equals("false")){
	//									btnCollect.setText("☆");
	//
	//								}else{
	//									btnCollect.setText("★");
	//								}		
	//
	//							}
	//						});
	//
	//					}
	//				});
	//
	//			}
	//
	//			@Override
	//			public void onFailure(Call arg0, IOException arg1) {
	//				// TODO Auto-generated method stub
	//
	//			}
	//		});
	//
	//
	//	}
	//
	//
	//	interface OnCheckResultListener{
	//		void onResult(String result);
	//	}
	//
	//	Handler handler;
	//
	//	void checkIsCollected(Commodity commodity,final OnCheckResultListener listener){
	//
	//		if(handler == null){
	//			handler = new Handler();
	//		}
	//
	//		Request request = Server.requestBuilderWithCs("commodity/" + commodity.getId().toString() + "/iscollected")
	//				.build();
	//
	//		Server.getSharedClient().newCall(request).enqueue(new Callback() {
	//
	//			@Override
	//			public void onResponse(Call arg0, final Response arg1) throws IOException {
	//
	//				try {
	//					final String checkResult = arg1.body().string();
	//
	//					handler.post(new Runnable() {
	//
	//						@Override
	//						public void run() {
	//							// TODO Auto-generated method stub								
	//
	//							listener.onResult(checkResult);
	//
	//						}
	//					});
	//
	//				} catch (IOException e) {
	//					// TODO Auto-generated catch block
	//					e.printStackTrace();
	//				}
	//
	//			}
	//
	//			@Override
	//			public void onFailure(Call arg0, IOException arg1) {
	//				// TODO Auto-generated method stub
	//
	//			}
	//		});		
	//	}

	TextView btnBack;
	TextView btnCollect;
	RadioGroup tabbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_commodity_content);

		final Commodity commodity = (Commodity) getIntent().getSerializableExtra("commodity");

		btnBack = (TextView) findViewById(R.id.btn_back_commodity_content);

		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});

		btnCollect = (TextView) findViewById(R.id.btn_collect_commodity_content);
		
		btnCollect.setOnClickListener(new OnClickListener() {
			
						@Override
						public void onClick(View v) {
							checkIsCollected(commodity,new OnCheckResultListener() {
			
								@Override
								public void onResult(String result) {
									// TODO Auto-generated method stub
			
									if(result.equals("false")){
										btnCollect.setText("☆");
			
									}else{
										btnCollect.setText("★");
									}		
									changeCollected(result,commodity);
			
								}
							});
			
						}
					});		

		checkIsCollected(commodity, new OnCheckResultListener() {

			@Override
			public void onResult(String result) {
				if(result.equals("false")){
					btnCollect.setText("☆");

				}else{
					btnCollect.setText("★");
				}		

			}
		});

		ImageView image;
		image = (ImageView) findViewById(R.id.image_commodity_content);
		image.load(Server.serverAddress + commodity.getCommImage());
		

		



		getFragmentManager()
		.beginTransaction()
		.replace(R.id.content_checked,new CommodityContentFragment())
		.commit();

		tabbar = (RadioGroup) findViewById(R.id.rd_content);
		tabbar.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int tab = group.getCheckedRadioButtonId();

				RadioButton rb = (RadioButton) findViewById(tab);

				int index = 0;

				if(rb.getText().equals("商品")){

					index = 0;
					changeContentFragment(index);


				}else if(rb.getText().equals("评价")){

					index = 1;
					changeContentFragment(index);

				}else if(rb.getText().equals("卖家")){


					index = 2;
					changeContentFragment(index);

				}


			}
		});


	}



	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();		

	}
	void changeCollected(String checkResult,final Commodity commodity){

		String param = String.valueOf(checkResult.equals("false"));
		MultipartBody body = new MultipartBody
				.Builder()
				.addFormDataPart("collect",param)
				.build();

		Request request = Server.requestBuilderWithCs("commodity/" + commodity.getId() + "/collect")
				.post(body)
				.build();

		Server.getSharedClient().newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				runOnUiThread(new Runnable() {					
					@Override
					public void run() {
						checkIsCollected(commodity, new OnCheckResultListener() {

							@Override
							public void onResult(String result) {
								if(result.equals("false")){
									btnCollect.setText("☆");

								}else{
									btnCollect.setText("★");
								}		

							}
						});

					}
				});

			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				// TODO Auto-generated method stub

			}
		});


	}


	interface OnCheckResultListener{
		void onResult(String result);
	}

	Handler handler;

	void checkIsCollected(Commodity commodity,final OnCheckResultListener listener){

		if(handler == null){
			handler = new Handler();
		}

		Request request = Server.requestBuilderWithCs("commodity/" + commodity.getId().toString() + "/iscollected")
				.build();

		Server.getSharedClient().newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, final Response arg1) throws IOException {

				try {
					final String checkResult = arg1.body().string();

					handler.post(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub								

							listener.onResult(checkResult);

						}
					});

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				// TODO Auto-generated method stub

			}
		});		
	}

	void changeContentFragment(int index){
		Fragment newFrag = null;
		switch(index){
		case 0:{ 
			newFrag = new CommodityContentFragment();
			break;
		}
		case 1:{
			newFrag = new CommodityContentFragment();
			break;
		}
		case 2:{
			newFrag = new CommodityContentFragment();
			break;
		}
		default:{
			newFrag = new CommodityContentFragment();
			break;		
		}
		}

		if(newFrag==null)
			return;

		getFragmentManager()
		.beginTransaction()
		.replace(R.id.content_checked, newFrag)
		.commit();

	}

}
