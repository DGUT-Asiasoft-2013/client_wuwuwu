package com.example.palmcampusmarket_client;
import com.example.palmcampusmarket_client.fragment.pages.TypeFoodFragment;

import android.app.Activity;
import android.os.Bundle;

public class DisplayFoodActivity extends Activity {

	TypeFoodFragment typeFoodList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_food);
		typeFoodList=(TypeFoodFragment)getFragmentManager().findFragmentById(R.id.type_food);
	}



	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();


	}

}

