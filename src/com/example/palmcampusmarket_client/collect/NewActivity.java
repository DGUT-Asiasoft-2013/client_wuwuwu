package com.example.palmcampusmarket_client.collect;

import com.example.palmcampusmarket_client.NewCommodityActivity;
import com.example.palmcampusmarket_client.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class NewActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_new);
		
		TextView newCommodity;
		newCommodity = (TextView) findViewById(R.id.btn_commodity_new);
		newCommodity.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				goNewCommodity();
				
			}
		});
		
		TextView back;
		back = (TextView) findViewById(R.id.btn_back_new);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
	}

	void goNewCommodity() {
		// TODO Auto-generated method stub
		Intent itnt = new Intent(this, NewCommodityActivity.class);
		startActivity(itnt);
	}

}
