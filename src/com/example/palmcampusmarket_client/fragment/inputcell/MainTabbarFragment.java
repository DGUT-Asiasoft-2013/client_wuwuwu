package com.example.palmcampusmarket_client.fragment.inputcell;


import com.example.palmcampusmarket_client.NewCommodityActivity;
import com.example.palmcampusmarket_client.R;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainTabbarFragment extends Fragment {
	
	View btnNew;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_page_home, null);
		
		
		btnNew = view.findViewById(R.id.btn_new);
		
		btnNew.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onNewClicked();
				
			}
		});
		
		return view;
		
	}
	
	public static interface OnNewClickedListener{
		void onNewClicked();
	}
	
	OnNewClickedListener onNewClickedListener;
	
	void onNewClicked(){
		if(onNewClickedListener!=null)
			onNewClickedListener.onNewClicked();
	}


}
