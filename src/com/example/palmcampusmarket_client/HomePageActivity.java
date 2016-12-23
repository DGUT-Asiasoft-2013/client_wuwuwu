package com.example.palmcampusmarket_client;


import com.example.palmcampusmarket_client.fragment.MainTabbarFragment;
import com.example.palmcampusmarket_client.fragment.MainTabbarFragment.OnNewClickedListener;
import com.example.palmcampusmarket_client.fragment.MainTabbarFragment.OnTabSelectedListener;

import com.example.palmcampusmarket_client.fragment.pages.CollectionListFragment;
import com.example.palmcampusmarket_client.fragment.pages.HomeListFragment;
import com.example.palmcampusmarket_client.fragment.pages.MeFragment;
import com.example.palmcampusmarket_client.fragment.pages.ShoppingPageFragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

public class HomePageActivity extends Activity {


	HomeListFragment contentHomeList = new HomeListFragment();
	CollectionListFragment contentCollectionList = new CollectionListFragment();
	ShoppingPageFragment contentShoppingPage = new ShoppingPageFragment();
	MeFragment contentMe = new MeFragment();
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

	

	
	void bringUpEditor(){
		Intent itnt = new Intent(this, NewCommodityActivity.class);
		startActivity(itnt);
		overridePendingTransition(R.anim.slide_in_bottom, R.anim.none);
	}


	@Override
	protected void onResume() {
		super.onResume();

		if(tabbar.getSelectedIndex()<0){
			tabbar.setSelectedItem(0);	
		}
	}

	void changeContentFragment(int index){
		Fragment newFrag = null;

		switch(index){
		case 0: newFrag = contentHomeList; break;
		case 1: newFrag = contentCollectionList; break;
		case 2: newFrag = contentShoppingPage; break;
		case 3: newFrag = contentMe; break;

		default:break;
		}

		if(newFrag==null) return;

		getFragmentManager()
		.beginTransaction()
		.replace(R.id.content, newFrag)
		.commit();
	}

}
