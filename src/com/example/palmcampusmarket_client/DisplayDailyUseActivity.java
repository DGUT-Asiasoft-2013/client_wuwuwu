package com.example.palmcampusmarket_client;


import com.example.palmcampusmarket_client.fragment.pages.TypeDailyUseFragment;

import android.app.Activity;
import android.os.Bundle;

public class DisplayDailyUseActivity extends Activity {
	
	TypeDailyUseFragment typeDailyUseList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dispaly_daily_use);
		typeDailyUseList=(TypeDailyUseFragment)getFragmentManager().findFragmentById(R.id.type_daily_use);
	}
	
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		
	}

}

