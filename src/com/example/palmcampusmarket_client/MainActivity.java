package com.example.palmcampusmarket_client;


import com.example.palmcampusmarket_client.fragment.MainTabbarFragment;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_boot);

		startPurchaseActivity();

	}

	void startPurchaseActivity(){
		Intent itnt = new Intent(this, PurchaseActivity.class);



	}

	@Override
	protected void onResume() {

		super.onResume();

		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				goHomePage();

			}
		},1000);
	}

	void goHomePage(){
		Intent itnt = new Intent(this,HomePageActivity.class);
		startActivity(itnt);
		finish();
	}


	void startLoginActivity(){
		Intent itnt = new Intent(this, LoginActivity.class);

		startActivity(itnt);
		finish();
	}
}
