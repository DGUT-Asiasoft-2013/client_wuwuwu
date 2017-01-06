package com.example.palmcampusmarket_client;


import com.example.palmcampusmarket_client.fragment.pages.TypeBookFragment;

import android.app.Activity;
import android.os.Bundle;

public class DisplayFoodActivity extends Activity {
	
	TypeBookFragment typeFoodList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_food);
		typeFoodList=(TypeBookFragment)getFragmentManager().findFragmentById(R.id.type_food);
	}
	
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		
	}

}

