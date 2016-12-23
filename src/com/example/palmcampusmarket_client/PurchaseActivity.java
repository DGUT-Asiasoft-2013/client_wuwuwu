package com.example.palmcampusmarket_client;

import java.io.IOException;

import com.example.palmcampusmarket_client.api.Server;
import com.example.palmcampusmarket_client.api.entity.User;
import com.example.palmcampusmarket_client.fragment.PurchaseFragmentFunctionbar;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

///��ťûд
public class PurchaseActivity extends Activity {  //����ҳ��

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
		commodityDescribe=(TextView)findViewById(R.id.commodity_describe);//��Ʒ����
		buyerName=(TextView)findViewById(R.id.buyer_name);//�������
		buyerTelephone=(TextView)findViewById(R.id.buyer_telephone);//��ҵ绰
		buyerAddress=(TextView)findViewById(R.id.buyer_address);//��ҵ�ַ
		singlePrice=(TextView)findViewById(R.id.buy_price);//����
		buyNumber=(EditText)findViewById(R.id.buy_number);//��������
		commodityPicture=(ImageView)findViewById(R.id.commodity_picture);//��ƷͼƬ
		buyFunctionbar=(PurchaseFragmentFunctionbar)getFragmentManager().findFragmentById(R.id.frag_tabber);
		
		findViewById(R.id.btn_buy).setOnClickListener(new View.OnClickListener() { //���¹���ť
			
			@Override
			public void onClick(View v) {
				goBuySuccess();
				
			}
		});
		
		buyNumber.addTextChangedListener(new TextWatcher() {   //EditText�޸ĵļ����¼�
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
				num=Integer.parseInt(buyNumber.getText().toString()); //ȡ��EditText�е���ֵ
				totalPrice=num*9999;
				buyFunctionbar.setPrice("�ܼۣ�"+totalPrice);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				num=Integer.parseInt(buyNumber.getText().toString()); //ȡ��EditText�е���ֵ
				totalPrice=num*9999;
				buyFunctionbar.setPrice("�ܼۣ�"+totalPrice);
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
				
			}
		});
	}
	
	@Override
		protected void onResume() {
			super.onResume();
			commodityDescribe.setText("����ô�ڶ��ң�");	
			singlePrice.setText("���ۣ�"+"9999");
			
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
								PurchaseActivity.this.onResponse(arg0,user);//��ȡ�û���Ϣ
								
							}
						});
					}catch(final Exception e){
						runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								PurchaseActivity.this.onFailuer(arg0,e);//��ȡ
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
	
	void goBuySuccess(){  //���˹���ť����תҳ��
		
	}
	
	protected void onResponse(Call arg0,User user){
		buyerName.setText("�ջ��ˣ�"+user.getAccount());
		buyerTelephone.setText("��ϵ�绰��"+user.getTelephone());
		buyerAddress.setText("�ջ���ַ��"+user.getAddress());
	}
	
	protected void onFailuer(Call arg0,Exception ex){
		buyerName.setText("�ջ��ˣ�");
		buyerTelephone.setText("��ϵ�绰��");
		buyerAddress.setText("�ջ���ַ��");
	}
}
