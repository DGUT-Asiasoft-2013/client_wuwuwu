package com.example.palmcampusmarket_client.fragment;

import com.example.palmcampusmarket_client.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;


//可能不需要？
public class PurchaseOrderFragment extends Fragment {

	CheckBox check;
	TextView commodity_describe,buyer_telephone,buyer_name;
	Button pay;
	ImageView commodity_picture;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		
		
		View view=inflater.inflate(R.layout.fragment_purchase_order, null);
		check=(CheckBox)view.findViewById(R.id.check);
		commodity_describe=(TextView)view.findViewById(R.id.commodity_describe);
		buyer_telephone=(TextView)view.findViewById(R.id.buyer_telephone);
		buyer_name=(TextView)view.findViewById(R.id.buyer_name);
		pay=(Button)view.findViewById(R.id.pay);
		commodity_picture=(ImageView)view.findViewById(R.id.commodity_picture);

		return view;
	}
}
