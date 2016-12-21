package com.example.palmcampusmarket_client;

import com.example.palmcampusmarket_client.fragment.PurchaseFragmentFunctionbar;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

///按钮没写
public class PurchaseActivity extends Activity {  //购买页面

	TextView commodityDescribe,buyerName,buyerTelephone,buyerAddress,singlePrice;
	Button btnAdd,btnSub;
	EditText buyNumber;
	ImageView commodityPicture;
	TextView totalPriceBig;
	int num,totalPrice;
	PurchaseFragmentFunctionbar buyFunctionbar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_purchase);
		commodityDescribe=(TextView)findViewById(R.id.commodity_describe);//商品描述
		buyerName=(TextView)findViewById(R.id.buyer_name);//买家姓名
		buyerTelephone=(TextView)findViewById(R.id.buyer_telephone);//买家电话
		buyerAddress=(TextView)findViewById(R.id.buyer_address);//买家地址
		singlePrice=(TextView)findViewById(R.id.buy_price);//单价
		buyNumber=(EditText)findViewById(R.id.buy_number);//购买数量
		commodityPicture=(ImageView)findViewById(R.id.commodity_picture);//物品图片
		buyFunctionbar=(PurchaseFragmentFunctionbar)getFragmentManager().findFragmentById(R.id.frag_tabber);
		
		findViewById(R.id.btn_buy).setOnClickListener(new View.OnClickListener() { //按下购买按钮
			
			@Override
			public void onClick(View v) {
				goBuySuccess();
				
			}
		});
		
		buyNumber.addTextChangedListener(new TextWatcher() {   //EditText修改的监听事件
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
				num=Integer.parseInt(buyNumber.getText().toString()); //取出EditText中的数值
				totalPrice=num*9999;
				buyFunctionbar.setPrice("总价："+totalPrice);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				num=Integer.parseInt(buyNumber.getText().toString()); //取出EditText中的数值
				totalPrice=num*9999;
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
			commodityDescribe.setText("你特么在逗我？");
			buyerName.setText("收货人："+"尤桃");
			buyerTelephone.setText("13888888888");
			buyerAddress.setText("广东省东莞市松山湖大学路一号东莞理工学院");
			
			singlePrice.setText("单价："+"9999");
			
			
		}
	
	void goBuySuccess(){  //按了购买按钮后跳转页面
		
	}
}
