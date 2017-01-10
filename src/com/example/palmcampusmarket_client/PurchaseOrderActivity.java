package com.example.palmcampusmarket_client;

import java.io.IOException;
import java.util.List;


import com.example.palmcampusmarket_client.api.Server;
import com.example.palmcampusmarket_client.api.entity.CarData;
import com.example.palmcampusmarket_client.api.entity.Commodity;
import com.example.palmcampusmarket_client.api.entity.Page;
import com.example.palmcampusmarket_client.api.entity.PurchaseHistory;
import com.example.palmcampusmarket_client.api.entity.User;
import com.example.palmcampusmarket_client.fragment.ImageDown;
import com.example.palmcampusmarket_client.fragment.ImageView;
import com.example.palmcampusmarket_client.fragment.pages.HomeListFragment;
import com.example.palmcampusmarket_client.purchase.PurchaseFragmentFunctionbar;
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

@SuppressLint("ValidFragment")
public class PurchaseOrderActivity extends Fragment{

	CheckBox allchoise;
	TextView total;
	Button payall;
	ListView listView;
	View view;
	private boolean[] is_choice;
	
	List<PurchaseHistory> data;
	int page = 0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		is_choice=new boolean[CarData.arrayList_car.size()];
		if(view==null){
			view=inflater.inflate(R.layout.activity_purchase_order, null);
			allchoise=(CheckBox)view.findViewById(R.id.allchoise);
			total=(TextView)view.findViewById(R.id.total);
			payall=(Button)view.findViewById(R.id.pay);
			listView=(ListView)view.findViewById(R.id.list_purchase);
			listView.setAdapter(listAdapter);
			
			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

					onItemClicked(position);
				
				}


			});
			
			allchoise.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
					/*
					 * 判断一：（全选按钮选中）全选按钮是否选择，如果选择，那么列表每一行都选中
					 * 判断二：（全选按钮取消）当取消全选按钮时，会有两种情况
					 * ，第一：主动点击全选按钮，此时直接取消列表所有的选中状态，第二：取消列表某一行，导致全选取消，此时列表其他行仍然是选中
					 * 
					 * 判断二的分析：（主动取消）判断列表每一行的选中状态，如果全部都是选中状态，那么（列表选中数=列表总数），此时属于主动取消，
					 * 则取消所有行的选中状态，反之（被动取消）则不响应
					 */

					// 记录列表每一行的选中状态数量
					int isChoice_all = 0;
					if (arg1) {
						// 设置全选
						for (int i = 0; i < CarData.arrayList_car.size(); i++) {
							// 如果选中了全选，那么就将列表的每一行都选中
							((CheckBox) (listView.getChildAt(i)).findViewById(R.id.check)).setChecked(true);
						}
					} else {
						// 设置全部取消
						for (int i = 0; i < CarData.arrayList_car.size(); i++) {
							// 判断列表每一行是否处于选中状态，如果处于选中状态，则计数+1
							if (((CheckBox) (listView.getChildAt(i)).findViewById(R.id.check)).isChecked()) {
								// 计算出列表选中状态的数量
								isChoice_all += 1;
							}
						}
						// 判断列表选中数是否等于列表的总数，如果等于，那么就需要执行全部取消操作
						if (isChoice_all == CarData.arrayList_car.size()) {
							// 如果没有选中了全选，那么就将列表的每一行都不选
							for (int i = 0; i < CarData.arrayList_car.size(); i++) {
								// 列表每一行都取消
								((CheckBox) (listView.getChildAt(i)).findViewById(R.id.check)).setChecked(false);
							}
						}
					}
				}
			});
			
			
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
				TextView commodity_describe,buyer_telephone,buyer_name,buy_num,price;
				Button pay;
				ImageView commodity_picture;
				
				
				commodity_describe=(TextView)view.findViewById(R.id.commodity_describe);
				buyer_telephone=(TextView)view.findViewById(R.id.buyer_telephone);
				buyer_name=(TextView)view.findViewById(R.id.buyer_name);
				pay=(Button)view.findViewById(R.id.pay);
				commodity_picture=(ImageView)view.findViewById(R.id.commodity_picture);
				buy_num=(TextView)view.findViewById(R.id.num);
				price=(TextView)view.findViewById(R.id.price);
				
				
				final PurchaseHistory purchaseHistory=data.get(position);
				final Commodity commodity=purchaseHistory.getCommodity();
				commodity_describe.setText(commodity.getCommDescribe());
				commodity_picture.load(Server.serverAddress+commodity.getCommImage());
				buy_num.setText("x"+purchaseHistory.getBuyNumber());
				price.setText("总价："+purchaseHistory.getTotalPrice());
				User user=purchaseHistory.getUser();
				buyer_name.setText("收货人："+user.getAccount());
				buyer_telephone.setText("联系电话"+user.getTelephone());
			
				
				
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
											.setTitle("支付成功")
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
	
//	/** adapter的回调函数，当点击CheckBox的时候传递点击位置和checkBox的状态 */
//	@Override
//	public void getChoiceData(int position, boolean isChoice) {
//		//得到点击的哪一行
//		if (isChoice) {
//			if (CarData.arrayList_car.size()!=0) {
//				//49表示商品的价格，这里偷了下懒，没有去动态获取商品价格
//				CarData.Allprice_car += Float.valueOf(CarData.arrayList_car.get(position).get("num").toString())*49;
//			}
//		} else {
//			if (CarData.arrayList_car.size()!=0) {
//				CarData.Allprice_car -= Float.valueOf(CarData.arrayList_car.get(position).get("num").toString())*49;
//			}
//		}
//		// 记录列表处于选中状态的数量
//		int num_choice = 0;
//		for (int i = 0; i < CarData.arrayList_car.size(); i++) {
//			// 判断列表中每一行的选中状态，如果是选中，计数加1
//			if (null!=listView.getChildAt(i)&&((CheckBox) (listView.getChildAt(i)).findViewById(R.id.check)).isChecked()) {
//				// 列表处于选中状态的数量+1
//				num_choice += 1;
//				is_choice[i]=true;
//			}
//		}
//		// 判断列表中的CheckBox是否全部选择
//		if (num_choice == CarData.arrayList_car.size()) {
//			// 如果选中的状态数量=列表的总数量，那么就将全选设置为选中
//			allchoise.setChecked(true);
//		} else {
//			// 如果选中的状态数量！=列表的总数量，那么就将全选设置为取消
//			allchoise.setChecked(false);
//		}
//
//		total.setText("合计：￥"+CarData.Allprice_car + "");
//
//		System.out.println("选择的位置--->"+position);
//		
//	}
//	
	void onItemClicked(int position){
		
	}
//
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		
//	}
}
