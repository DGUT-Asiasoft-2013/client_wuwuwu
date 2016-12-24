package com.example.palmcampusmarket_client;

import java.io.IOException;

import com.example.palmcampusmarket_client.api.Server;
import com.example.palmcampusmarket_client.api.entity.Commodity;
import com.example.palmcampusmarket_client.api.entity.User;
import com.example.palmcampusmarket_client.fragment.AvatarView;
import com.example.palmcampusmarket_client.fragment.PurchaseFragmentFunctionbar;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

///按钮没写
public class PurchaseActivity extends Activity {  //购买页面

	TextView commodityDescribe,buyerName,buyerTelephone,buyerAddress,singlePrice;
	Button btnAdd,btnSub;
	EditText buyNumber;
	AvatarView commodityPicture;
	int num,totalPrice,priceOne;
	Commodity commodity;
	PurchaseFragmentFunctionbar buyFunctionbar;
	Integer buyerId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_purchase);
		commodityDescribe=(TextView)findViewById(R.id.commodity_describe);//商品描述
		buyerName=(TextView)findViewById(R.id.buyer_name);//买家姓名
		buyerTelephone=(TextView)findViewById(R.id.buyer_telephone);//买家电话
		buyerAddress=(TextView)findViewById(R.id.buyer_address);//买家地址ַ
		singlePrice=(TextView)findViewById(R.id.buy_price);//单价
		buyNumber=(EditText)findViewById(R.id.buy_number);//购买数量
		commodityPicture=(AvatarView)findViewById(R.id.commodity_picture);//物品图片
		buyFunctionbar=(PurchaseFragmentFunctionbar)getFragmentManager().findFragmentById(R.id.frag_tabber);
		
		commodity=(Commodity)getIntent().getSerializableExtra("infomation");//从商品详情页面获取商品信息
 		
		findViewById(R.id.btn_buy).setOnClickListener(new View.OnClickListener() { //按下购买按钮
			
			@Override
			public void onClick(View v) {
				goBuyNow();
				
			}
		});
		
		buyNumber.addTextChangedListener(new TextWatcher() {   //EditText修改的监听事件
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
				num=Integer.parseInt(buyNumber.getText().toString()); //EditText中的数值ֵ
				priceOne=Integer.parseInt(commodity.getCommPrice());
				totalPrice=num*priceOne;
				buyFunctionbar.setPrice("总价："+totalPrice);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				num=Integer.parseInt(buyNumber.getText().toString()); //取出EditText中的数值ֵ
				priceOne=Integer.parseInt(commodity.getCommPrice());
				totalPrice=num*priceOne;
				buyFunctionbar.setPrice("总价："+totalPrice);
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
				
			}
		});
	}
	
	@Override
		protected void onResume() {
			super.onResume();
			commodityDescribe.setText(commodity.getCommDescribe());	
			singlePrice.setText("总价："+commodity.getCommPrice());
			if(commodity.getCommImage()!=null){
			commodityPicture.load(commodity.getCommImage());
			}
			OkHttpClient client =Server.getSharedClient();
			Request request = Server.requestBuilderWithApi("me")
					.method("get", null)
					.build();
			client.newCall(request).enqueue(new Callback() {
				
				@Override
				public void onResponse(final Call arg0, Response arg1) throws IOException {
					try{
						final User user =new ObjectMapper().readValue(arg1.body().bytes(), User.class);
						runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								PurchaseActivity.this.onResponse(arg0,user);//获取用户信息
								
							}
						});
					}catch(final Exception e){
						runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								PurchaseActivity.this.onFailuer(arg0,e);
							}
						});
					}
				}
				
				@Override
				public void onFailure(Call arg0, IOException arg1) {
					PurchaseActivity.this.onFailuer(arg0,arg1);
					
				}
			});
			
		}
	
	void goBuyNow(){  //按了购买按钮后跳转页面
		OkHttpClient client =Server.getSharedClient();
		Integer commodity_Id = commodity.getId();
		MultipartBody.Builder requestBody =new MultipartBody.Builder()
				.setType(MultipartBody.FORM)
				.addFormDataPart("commmodity_Id", commodity_Id.toString())
				.addFormDataPart("commodityPrice", commodity.getCommPrice())
				.addFormDataPart("buyNumber", buyNumber.getText().toString())
				.addFormDataPart("totalPrice", String.valueOf(totalPrice));
		
		Request request = Server.requestBuilderWithApi("purchaseHistory")  //写入消费记录表
				.method("post", null)
				.post(requestBody.build())
				.build();
		
		final ProgressDialog progressDialog = new ProgressDialog(PurchaseActivity.this);
		progressDialog.setMessage("请稍候");
		progressDialog.setCancelable(false);
		progressDialog.setCanceledOnTouchOutside(false);
		
		client.newCall(request).enqueue(new Callback() {
			
			@Override
			public void onResponse(final Call arg0, Response arg1) throws IOException {
				final String s = arg1.body().string();
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						progressDialog.dismiss();
						
						try{
							PurchaseActivity.this.onSubmitResponse(arg0, s);
						}catch(Exception e){
							Toast.makeText(getBaseContext(),s,Toast.LENGTH_LONG).show();
						}
					}
				});
				
			}
			
			@Override
			public void onFailure(Call arg0, IOException arg1) {
				
				
			}
		});
	}
	
	protected void onSubmitResponse(Call arg0, String s) {
		new AlertDialog.Builder(this)
		.setTitle("购买成功")
		.setMessage(s)
		.setPositiveButton("好", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();

			}
		})
		.show();
	}

	protected void onResponse(Call arg0,User user){
		buyerName.setText("收货人："+user.getAccount());
		buyerTelephone.setText("联系电话："+user.getTelephone());
		buyerAddress.setText("收货地址："+user.getAddress());
		buyerId=user.getId();
	}
	
	protected void onFailuer(Call arg0,Exception ex){
		buyerName.setText("收货人：");
		buyerTelephone.setText("联系电话：");
		buyerAddress.setText("收货地址：");
	}
}
