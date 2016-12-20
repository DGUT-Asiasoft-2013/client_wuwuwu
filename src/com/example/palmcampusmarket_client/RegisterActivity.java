package com.example.palmcampusmarket_client;


import com.example.palmcampusmarket_client.fragment.inputcell.PictureInputCellFragment;

import android.app.Activity;
import android.os.Bundle;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RegisterActivity extends Activity {
	PictureInputCellFragment fragInputAvatar;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_register);
		
		
		fragInputAvatar = (PictureInputCellFragment) getFragmentManager().findFragmentById(R.id.input_avatar);
		
		
	}


}
