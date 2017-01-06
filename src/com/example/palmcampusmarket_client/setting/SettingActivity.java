package com.example.palmcampusmarket_client.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.palmcampusmarket_client.R;

/**
 * Created by Administrator on 2016/12/22.
 */
public class SettingActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		findViewById(R.id.me_item_address).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				goAddress();
			}
		});
	}

	void goAddress(){
		Intent intent = new Intent(this,AddressContentActivity.class);
		startActivity(intent);
	}
}
