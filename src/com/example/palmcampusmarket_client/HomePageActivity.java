package com.example.palmcampusmarket_client;


import com.example.palmcampusmarket_client.fragment.MainTabbarFragment;
import com.example.palmcampusmarket_client.fragment.MainTabbarFragment.OnNewClickedListener;
import com.example.palmcampusmarket_client.fragment.MainTabbarFragment.OnTabSelectedListener;
import com.example.palmcampusmarket_client.fragment.pages.CollectionFragment;
import com.example.palmcampusmarket_client.fragment.pages.HomeListFragment;
import com.example.palmcampusmarket_client.fragment.pages.MyInformationFragment;
import com.example.palmcampusmarket_client.fragment.pages.ShoppingCartFragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomePageActivity extends Activity {
	
	HomeListFragment contentHomeList = new HomeListFragment();
	CollectionFragment contentCollection = new CollectionFragment();
	ShoppingCartFragment contentShoppingCart = new ShoppingCartFragment();
	MyInformationFragment contentMyInformation = new MyInformationFragment();
	
	
	MainTabbarFragment tabbar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_palmcampus);
		
		tabbar = (MainTabbarFragment) getFragmentManager().findFragmentById(R.id.frag_tabbar);
	    tabbar.setOnTabSelectedListener(new OnTabSelectedListener() {
			
			@Override
			public void onTabSelected(int index) {
				changeContentFragment(index);
				
			}
		});
		tabbar.setOnNewClickedListener(new OnNewClickedListener() {
			
			@Override
			public void onNewClicked() {
				bringUpEditor();
				
			}
		});
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		tabbar.setSelectedItem(0);
	}
	
	void changeContentFragment(int index){
		Fragment newfrag = null;
		switch(index){
			case 0:newfrag = contentHomeList;break;
			case 1:newfrag = contentCollection;break;
			case 2:newfrag = contentShoppingCart;break;
			case 3:newfrag = contentMyInformation;break;
			
			default:
				break;
		}
		
		if(newfrag == null)return;
		getFragmentManager()
		.beginTransaction()
		.replace(R.id.content, newfrag)
		.commit();
	}
	
	void bringUpEditor(){
		Intent itnt = new Intent(this, NewCommodityActivity.class);
		startActivity(itnt);
		overridePendingTransition(R.anim.slide_in_bottom, R.anim.none);
	}

}
