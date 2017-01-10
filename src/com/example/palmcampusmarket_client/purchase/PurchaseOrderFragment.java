package com.example.palmcampusmarket_client.purchase;

import java.io.IOException;
import java.util.List;

import com.example.palmcampusmarket_client.NewCommodityActivity;
import com.example.palmcampusmarket_client.R;
import com.example.palmcampusmarket_client.api.Server;
import com.example.palmcampusmarket_client.api.entity.CarData;
import com.example.palmcampusmarket_client.api.entity.Commodity;
import com.example.palmcampusmarket_client.api.entity.Page;
import com.example.palmcampusmarket_client.api.entity.PurchaseHistory;
import com.example.palmcampusmarket_client.fragment.ImageView;
import com.example.palmcampusmarket_client.purchase.Adapter_ListView_Car.OnCheckedChangedListener;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


@SuppressLint("ValidFragment")
public class PurchaseOrderFragment extends Fragment{

	CheckBox allchoise;
	TextView allPrice;
	Button pay;
	ImageView commodity_picture;
	private Adapter_ListView_Car adapter;
	private String str_del = "结算";
	private boolean[] is_choice;
	private ListView listView;
	private List<PurchaseHistory> data;
	int page = 0 ,totalPrice;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


		is_choice=new boolean[CarData.arrayList_car.size()];
		View view=inflater.inflate(R.layout.activity_purchase_order, null);
		initView(view);


		return view;
	}

	private void initView(View view) {

		allPrice = (TextView) view.findViewById(R.id.total);

		pay = (Button) view.findViewById(R.id.pay);
		pay.setText(str_del);
		allchoise = (CheckBox) view.findViewById(R.id.allchoise);


		allchoise.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				adapter.toggleAllCheckState(allchoise.isChecked());
			}
		});



		listView = (ListView) view.findViewById(R.id.list_purchase);

		adapter = new Adapter_ListView_Car(getActivity());

		adapter.setOnCheckedChangedListener(new OnCheckedChangedListener() {

			@Override
			public void onItemCheckStatusChanged() {
				int totalPrice = adapter.getTotalPrice();
				allPrice.setText("总价："+totalPrice+"元");
				int seleteNumber = adapter.selectNumber();			
				int listSize = adapter.adapterSize();
				if(listSize==seleteNumber){
					allchoise.setChecked(true);
				}else{
					allchoise.setChecked(false);
				}



			}
		});

		listView.setAdapter(adapter);
		adapter.setAdapterCarList(data);



		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent(getActivity(), NewCommodityActivity.class);
				startActivity(intent);
			}
		});

		pay.setOnClickListener(new OnClickListener() { //鐐瑰嚮

			@Override
			public void onClick(View v) {
				List<PurchaseHistory> data1 = adapter.getCheckedHistory();
				for(PurchaseHistory history : data1){
					PurchaseHistory history1=new PurchaseHistory();
					history1=history;
					Commodity commodity=history1.getCommodity();
					OkHttpClient client =Server.getSharedClient();
					Integer commodity_Id = commodity.getId();
					MultipartBody.Builder requestBody =new MultipartBody.Builder()
							.setType(MultipartBody.FORM)
							.addFormDataPart("historyId",String.valueOf(history1.getId()) )
							.addFormDataPart("buyNumber", String.valueOf(history1.getBuyNumber()))
							.addFormDataPart("totalPrice", String.valueOf(history1.getTotalPrice()));

					Request request = Server.requestBuilderWithWallet("save_bill/"+commodity_Id)  
							.method("post", null)
							.post(requestBody.build())
							.build();


					client.newCall(request).enqueue(new Callback() {

						@Override
						public void onResponse(final Call arg0, Response arg1) throws IOException {
							final String s = arg1.body().string();
							getActivity().runOnUiThread(new Runnable() {
								@Override
								public void run() {


									try{
										new AlertDialog.Builder(getActivity())
										.setTitle("支付成功")
										.setPositiveButton("好", new DialogInterface.OnClickListener() {

											@Override
											public void onClick(DialogInterface dialog, int which) {


											}
										})
										.show();
									}catch(Exception e){

									}
								}
							});

						}

						@Override
						public void onFailure(Call arg0, IOException arg1) {


						}
					});
				}
			}
		});


	}



	@Override
	public void onResume() {
		super.onResume();
		reload();
	}

	void reload(){ //获取数据

		Request request= Server.requestBuilderWithApi("purchaseOrder")
				.get()
				.build();

		Server.getSharedClient().newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				try{
					final Page<PurchaseHistory> data = new ObjectMapper()
							.readValue(arg1.body().string(),
									new TypeReference<Page<PurchaseHistory>>() {});

					getActivity().runOnUiThread(new Runnable() {

						@Override
						public void run() {
							PurchaseOrderFragment.this.page = data.getNumber();	
							adapter.setAdapterCarList(data.getContent());

						}
					});
				}catch(final Exception e){
					getActivity().runOnUiThread(new Runnable() {

						@Override
						public void run() {
							new AlertDialog.Builder(getActivity())
							.setMessage(e.getMessage())
							.show();

						}
					});
				}

			}

			@Override
			public void onFailure(Call arg0,final IOException e) {
				getActivity().runOnUiThread(new Runnable() {

					@Override
					public void run() {
						new AlertDialog.Builder(getActivity())
						.setMessage(e.getMessage())
						.show();

					}
				});

			}
		});
	}



}
