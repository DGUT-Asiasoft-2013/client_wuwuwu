package com.example.palmcampusmarket_client.collect;

import com.example.palmcampusmarket_client.PurchaseActivity;
import com.example.palmcampusmarket_client.R;
import com.example.palmcampusmarket_client.api.entity.Commodity;
import com.example.palmcampusmarket_client.collect.CountOfCollected.OnCountResultListener;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CommodityContentFragment extends Fragment {

	View view;
	
//	Commodity commodity;
//	
//	
//	public Commodity commodity {
//		return commodity;
//	}
//
//
//	public void setCommodity(Commodity commodity) {
//		this.commodity = commodity;
//	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(view == null){
			view = inflater.inflate(R.layout.fragment_commodity_content, null);
			final Commodity commodity = (Commodity) getActivity().getIntent().getSerializableExtra("commodity");
			
			
			
			TextView name;
			name = (TextView) view.findViewById(R.id.name_commodity_content);
			name.setText(commodity.getCommName());
			
			final TextView countCollected;
			countCollected = (TextView) view.findViewById(R.id.collected_commodity_content);
			
			CountOfCollected.getCount(commodity, new OnCountResultListener() {
				
				@Override
				public void onResult(String result) {
					// TODO Auto-generated method stub
					countCollected.setText(result + "收藏");
				}
			});
			
			TextView sellerName;
			sellerName = (TextView) view.findViewById(R.id.seller_commodity_content);
			sellerName.setText(commodity.getUser().getNickname());
			
			TextView price;
			price = (TextView) view.findViewById(R.id.price_commodity_content);
			price.setText(commodity.getCommPrice());
			
			TextView num;
			num = (TextView) view.findViewById(R.id.num_commodity_content);
			num.setText(String.valueOf(commodity.getCommNumber()));
			
			TextView describe;
			describe = (TextView) view.findViewById(R.id.describe_commodity_content);
			describe.setText(commodity.getCommDescribe());
			
			Button btnBuy;
			btnBuy = (Button) view.findViewById(R.id.btn_commodity_content_buy);
			
			btnBuy.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent itnt = new Intent(getActivity(), PurchaseActivity.class);
					itnt.putExtra("infomation", commodity);
					startActivity(itnt);
				}
			});
			
		}		
		
		return view;
	}
}
