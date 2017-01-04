package com.example.palmcampusmarket_client;

import java.io.IOException;
import java.util.List;


import com.example.palmcampusmarket_client.api.Server;
import com.example.palmcampusmarket_client.api.entity.Commodity;
import com.example.palmcampusmarket_client.api.entity.Page;
import com.example.palmcampusmarket_client.api.entity.PurchaseHistory;
import com.example.palmcampusmarket_client.api.entity.User;
import com.example.palmcampusmarket_client.fragment.ImageDown;
import com.example.palmcampusmarket_client.fragment.ImageView;
import com.example.palmcampusmarket_client.fragment.PurchaseFragmentFunctionbar;
import com.example.palmcampusmarket_client.fragment.pages.HomeListFragment;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.Data;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PurchaseOrderActivity extends Fragment {

	PurchaseFragmentFunctionbar payFuntionbar;
	ListView listView;
	View view;
	
	
	List<PurchaseHistory> data;
	int page = 0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if(view==null){
			view=inflater.inflate(R.layout.activity_purchase_order, null);
			listView=(ListView)view.findViewById(R.id.list_purchase);
			listView.setAdapter(listAdapter);
			
			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

					onItemClicked(position);
				
				}


			});
			
			payFuntionbar=(PurchaseFragmentFunctionbar)getFragmentManager().findFragmentById(R.id.frag_tabber);
			
		}
		
		
		return view;
	}
	
	BaseAdapter listAdapter=new BaseAdapter() {
		
		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			
			if(convertView==null){
				LayoutInflater inflater = LayoutInflater.from(parent.getContext());
				view = inflater.inflate(R.layout.fragment_purchase_order, null);
			}else{
				view=convertView;
			}
				
			
				CheckBox check;
				TextView commodity_describe,buyer_telephone,buyer_name;
				Button pay;
				ImageView commodity_picture;
				
				check=(CheckBox)view.findViewById(R.id.check);
				commodity_describe=(TextView)view.findViewById(R.id.commodity_describe);
				buyer_telephone=(TextView)view.findViewById(R.id.buyer_telephone);
				buyer_name=(TextView)view.findViewById(R.id.buyer_name);
				pay=(Button)view.findViewById(R.id.pay);
				commodity_picture=(ImageView)view.findViewById(R.id.commodity_picture);
				
				final PurchaseHistory purchaseHistory=data.get(position);
				final Commodity commodity=purchaseHistory.getCommodity();
				commodity_describe.setText(commodity.getCommDescribe());
				commodity_picture.load(Server.serverAddress+commodity.getCommImage());
				User user=purchaseHistory.getUser();
				buyer_name.setText("收货人："+user.getAccount());
				buyer_telephone.setText("联系电话"+user.getTelephone());
			
				check.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if(isChecked){
							
							payFuntionbar.setPrice("总价:"+purchaseHistory.getTotalPrice());
						}else{
							payFuntionbar.setPrice("总价：");
						}
						
					}
				});
				
				pay.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						OkHttpClient client =Server.getSharedClient();
						Integer commodity_Id = commodity.getId();
						MultipartBody.Builder requestBody =new MultipartBody.Builder()
								.setType(MultipartBody.FORM)
								
								.addFormDataPart("buyNumber", String.valueOf(purchaseHistory.getBuyNumber()))
								.addFormDataPart("totalPrice", String.valueOf(purchaseHistory.getTotalPrice()));
						
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
											.setTitle("购买成功")
											.setMessage(s)
											.setPositiveButton("好", new DialogInterface.OnClickListener() {

												@Override
												public void onClick(DialogInterface dialog, int which) {
													getActivity().finish();

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
				});
			
			return view;
		}
		
		@Override
		public long getItemId(int position) {
			
			return position;
		}
		
		@Override
		public Object getItem(int position) {
			
			return data.get(position);
		}
		
		@Override
		public int getCount() {
			
			return data==null ? 0: data.size();
		}
	};
	
	@Override
	public void onResume() {
		
		super.onResume();
		reload();
	}
	
	void reload(){
		
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
							PurchaseOrderActivity.this.page = data.getNumber();
							PurchaseOrderActivity.this.data = data.getContent();
							listAdapter.notifyDataSetInvalidated();

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
	
	void onItemClicked(int position){
		
	}
}
