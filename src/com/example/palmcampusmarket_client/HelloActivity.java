package com.example.palmcampusmarket_client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HelloActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_hello);
	/*	
		findViewById(R.id.btn_changuser).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				goChangeUser();
			}
		});*/
	}
	
	void goChangeUser(){
		Intent itnt = new Intent(this,LoginActivity.class);
		startActivity(itnt);
		finish();
	}
}
