package com.example.palmcampusmarket_client;


import com.example.palmcampusmarket_client.collect.NeedsFragment;
import com.example.palmcampusmarket_client.collect.NewFragment;
import com.example.palmcampusmarket_client.fragment.MainTabbarFragment;
import com.example.palmcampusmarket_client.fragment.MainTabbarFragment.OnNewClickedListener;
import com.example.palmcampusmarket_client.fragment.MainTabbarFragment.OnTabSelectedListener;

import com.example.palmcampusmarket_client.fragment.pages.CollectionListFragment;
import com.example.palmcampusmarket_client.fragment.pages.HomeListFragment;
import com.example.palmcampusmarket_client.fragment.pages.MeFragment;
import com.example.palmcampusmarket_client.fragment.pages.ShoppingPageFragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class HomePageActivity extends Activity {


	HomeListFragment contentHomeList = new HomeListFragment();
	NeedsFragment needsList = new NeedsFragment();
	PurchaseOrderActivity contentShoppingPage = new PurchaseOrderActivity();
	MeFragment contentMe = new MeFragment();
	MainTabbarFragment tabbar;
	View newFrame;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_palmcampus);

		newFrame = findViewById(R.id.frame_new_outside);
		newFrame.setVisibility(View.GONE);
		newFrame.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(newFrame.getVisibility() == View.VISIBLE){
					newFrame.setVisibility(View.GONE);
					tabbar.getActivity().findViewById(R.id.btn_new).setBackgroundResource(R.drawable.newup);
				}else{
					newFrame.setVisibility(View.VISIBLE);
				}
			}
		});

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
				toggleNewActivity();
			}
		});


	}



	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		if(newFrame.getVisibility() == View.VISIBLE){
			newFrame.setVisibility(View.GONE);
			tabbar.getActivity().findViewById(R.id.btn_new).setBackgroundResource(R.drawable.newup);
		}else{
			finish();
		}

	}




	void toggleNewActivity(){
		//		Intent itnt = new Intent(this, NewActivity.class);
		//		startActivity(itnt);
		//		overridePendingTransition(R.anim.slide_in_bottom, R.anim.none);

		//
		//		NewDialog newDialog = new NewDialog(this);		
		//		newDialog.show();
		//		overridePendingTransition(R.anim.slide_in_bottom, R.anim.none);

		//		NewPopupwindow newPopWindow = new NewPopupwindow(tabbar.getActivity());  
		//        newPopWindow.showPopupWindow(tabbar.getView());
		//        newPopWindow.setOnNewCommodityClickedListener(new OnNewCommodityClickedListener() {
		//			
		//			@Override
		//			public void onNewCommodityClicked() {
		//				// TODO Auto-generated method stub
		//				goNewCommodity();
		//			}
		//		});

		if(newFrame.getVisibility() == View.VISIBLE){
			newFrame.setVisibility(View.GONE);
			tabbar.getActivity().findViewById(R.id.btn_new).setBackgroundResource(R.drawable.newup);
			
		}else{
			newFrame.setVisibility(View.VISIBLE);
			tabbar.getActivity().findViewById(R.id.btn_new).setBackgroundResource(R.drawable.newdown);
		}
	}

	void goNewCommodity() {
		// TODO Auto-generated method stub
		Intent itnt = new Intent(this, NewCommodityActivity.class);
		startActivity(itnt);
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
		case 1: newFrag = needsList; break;
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
