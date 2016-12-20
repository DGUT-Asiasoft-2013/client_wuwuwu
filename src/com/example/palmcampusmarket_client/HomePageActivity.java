package com.example.palmcampusmarket_client;

import com.example.palmcampusmarket_client.fragment.MainTabbarFragment;
import com.example.palmcampusmarket_client.fragment.MainTabbarFragment.OnNewClickedListener;

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
		
		tabbar = (MainTabbarFragment) getFragmentManager().findFragmentById(R.id.frag_tabbar);
		tabbar.setOnNewClickedListener(new OnNewClickedListener() {
			
			@Override
			public void onNewClicked() {
				ToAddCommodity();
				
			}
		});
			
	}
	
	void ToAddCommodity(){
		Intent intent = new Intent(this,NewCommodityActivity.class);
		startActivity(intent);
	}

}
