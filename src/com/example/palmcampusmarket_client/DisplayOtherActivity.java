package com.example.palmcampusmarket_client;

import com.example.palmcampusmarket_client.fragment.pages.TypeBookFragment;
import com.example.palmcampusmarket_client.fragment.pages.TypeOtherFragment;

import android.app.Activity;
import android.os.Bundle;

public class DisplayOtherActivity extends Activity {
	
	TypeOtherFragment typeOtherList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_other);
		typeOtherList=(TypeOtherFragment)getFragmentManager().findFragmentById(R.id.type_other);
	}
	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		
	}

}

