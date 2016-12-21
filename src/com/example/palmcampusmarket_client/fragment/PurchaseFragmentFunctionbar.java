package com.example.palmcampusmarket_client.fragment;



import com.example.palmcampusmarket_client.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PurchaseFragmentFunctionbar extends Fragment {

	View btn_buy;
	TextView functionPrice;
	
 	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_purchase_functionbar, null);
		functionPrice=(TextView) view.findViewById(R.id.price_big);
		btn_buy=view.findViewById(R.id.btn_buy);
		
		return view;
	}
	
	public void setPrice(CharSequence charSequence){
		functionPrice.setText(charSequence);
	}
}
