package com.example.palmcampusmarket_client;


import com.example.palmcampusmarket_client.fragment.pages.TypeBicycleFragment;

import android.app.Activity;
import android.os.Bundle;

public class DisplayBicycleActivity extends Activity {
	
	TypeBicycleFragment typeBicycleList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_bicycle);
		typeBicycleList=(TypeBicycleFragment)getFragmentManager().findFragmentById(R.id.type_bicycle);
	}
	
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		
	}

}
