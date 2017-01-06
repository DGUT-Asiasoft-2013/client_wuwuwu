package com.example.palmcampusmarket_client;

import com.example.palmcampusmarket_client.fragment.pages.TypeBookFragment;

import android.app.Activity;
import android.os.Bundle;

public class DisplayBookActivity extends Activity {
	
	TypeBookFragment typeBookList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_book);
		typeBookList=(TypeBookFragment)getFragmentManager().findFragmentById(R.id.type_book);
	}
	
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		
	}

}

