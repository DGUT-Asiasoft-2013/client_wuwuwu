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
			buyerName.setText("�ջ��ˣ�"+"����");
			buyerTelephone.setText("13888888888");
			buyerAddress.setText("�㶫ʡ��ݸ����ɽ����ѧ·һ�Ŷ�ݸ��ѧԺ");
			
			singlePrice.setText("���ۣ�"+"9999");
			
			
		}
	
	void goBuySuccess(){  //���˹���ť����תҳ��
		
	}
}
