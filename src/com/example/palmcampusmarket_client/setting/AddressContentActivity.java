package com.example.palmcampusmarket_client.setting;

import com.example.palmcampusmarket_client.R;
import com.example.palmcampusmarket_client.api.entity.Address;

import android.app.Activity;
import android.os.Bundle;

public class AddressContentActivity extends Activity {
	AddressListFragment addressListFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addresscontent);
		final Address address = (Address) getIntent().getSerializableExtra("address");
		addressListFragment = (AddressListFragment) getFragmentManager().findFragmentById(R.id.address_content);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

}
