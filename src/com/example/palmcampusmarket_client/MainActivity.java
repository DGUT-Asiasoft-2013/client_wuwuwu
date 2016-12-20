package com.example.palmcampusmarket_client;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		startPurchaseActivity();
	
	}
	
	void startPurchaseActivity(){
		Intent itnt = new Intent(this, PurchaseActivity.class);
		startActivity(itnt);
		finish();
	}
}
