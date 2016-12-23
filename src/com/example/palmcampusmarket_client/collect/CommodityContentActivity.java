package com.example.palmcampusmarket_client.collect;

import com.example.palmcampusmarket_client.R;
import com.example.palmcampusmarket_client.api.entity.Commodity;
import com.example.palmcampusmarket_client.collect.CountOfCollected.OnCountResultListener;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CommodityContentActivity extends Activity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_commodity_content);
		
		Commodity commodity = (Commodity) getIntent().getSerializableExtra("commodity");
		
		Button btnBack;
		Button btnCollect;
		Button btnBuy;		
		
		btnBack = (Button) findViewById(R.id.btn_back_commodity_content);
		btnCollect = (Button) findViewById(R.id.btn_collect_commodity_content);
		btnBuy = (Button) findViewById(R.id.btn_buy);
		
		ImageView image;
		TextView name;
		final TextView countCollected;
		TextView price;
		TextView describe;
		
		
		image = (ImageView) findViewById(R.id.image_commodity_content);
//		image.
		
		name = (TextView) findViewById(R.id.name_commodity_content);
		name.setText(commodity.getCommName());
		
		countCollected = (TextView) findViewById(R.id.collected_commodity_content);
		
		CountOfCollected.getCount(commodity, new OnCountResultListener() {
			
			@Override
			public void onResult(String result) {
				// TODO Auto-generated method stub
				
				countCollected.setText(result + "�ղ�");
				
			}
		});
		
		price = (TextView) findViewById(R.id.price_commodity_content);
		price.setText(commodity.getCommPrice());
		
		describe = (TextView) findViewById(R.id.describe_commodity_content);
		describe.setText(commodity.getCommDescribe());
		
	
	}

}
