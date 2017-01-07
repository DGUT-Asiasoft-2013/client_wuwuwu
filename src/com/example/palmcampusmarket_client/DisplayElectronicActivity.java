package com.example.palmcampusmarket_client;



import com.example.palmcampusmarket_client.fragment.pages.TypeElectronicFragment;

import android.app.Activity;
import android.os.Bundle;

public class DisplayElectronicActivity extends Activity {
	
	TypeElectronicFragment typeElectronicList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_electronic);
		typeElectronicList=(TypeElectronicFragment)getFragmentManager().findFragmentById(R.id.type_electronic);
	}
	
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		
	}

}

