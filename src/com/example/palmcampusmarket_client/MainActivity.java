package com.example.palmcampusmarket_client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.palmcampusmarket_wallet.MyBillActivity;

public class MainActivity extends Activity {

	ImageView wallet_back;
	RelativeLayout wallet_bill,wallet_asset,wallet_ye;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_wallet);

		findViewById(R.id.wallet_back).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

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
}
