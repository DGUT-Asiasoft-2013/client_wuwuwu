package com.example;

import android.app.Activity;
import android.os.Bundle;

import com.example.palmcampusmarket_client.R;
import com.example.palmcampusmarket_client.fragment.pages.MeFragment;


/**
 * Created by Administrator on 2016/12/22.
 */
public class TestActivity extends Activity{
	MeFragment meFragment;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);

		meFragment = new MeFragment();
		getFragmentManager()
		.beginTransaction()
		.replace(R.id.test_content,meFragment)
		.commit();
	}
}
