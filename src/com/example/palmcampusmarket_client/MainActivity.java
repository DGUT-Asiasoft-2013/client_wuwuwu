package com.example.palmcampusmarket_client;

import java.io.IOException;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.palmcampusmarket_wallet.MyBillActivity;
import com.example.palmcampusmarket_wallet.RechargeActivity;

public class MainActivity extends Activity {

	ImageView wallet_back;
	RelativeLayout wallet_recharge,wallet_bill,wallet_asset,wallet_ye;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_wallet);
		findViewById(R.id.wallet_back).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
			}
		});
		findViewById(R.id.wallet_recharge).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this, RechargeActivity.class);
				startActivity(intent);
			}
		});
		findViewById(R.id.wallet_bill).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(MainActivity.this,"dianjil",Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(MainActivity.this, MyBillActivity.class);
				startActivity(intent);
			}
		});
		findViewById(R.id.wallet_asset).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

			}
		});
		findViewById(R.id.wallet_ye).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

			}
		});

	}

	/*@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			private int abcd = 0;

			public void run() {
				startLoginActivity();
			}
		}, 1000);



	}*/

	void startLoginActivity(){
		Intent itnt = new Intent(this, LoginActivity.class);
		startActivity(itnt);
		finish();
	}
}
