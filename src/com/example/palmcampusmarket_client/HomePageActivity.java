package com.example.palmcampusmarket_client;

import com.example.palmcampusmarket_client.fragment.MainTabbarFragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomePageActivity extends Activity {
	
	MainTabbarFragment tabbar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_palmcampus);
		
			
	}
	
	void goAdd(){
		Intent intent = new Intent(this,NewCommodityActivity.class);
		startActivity(intent);
	}

}
