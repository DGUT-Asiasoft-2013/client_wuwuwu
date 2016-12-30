package com.example.palmcampusmarket_client.collect;

import com.example.palmcampusmarket_client.NewCommodityActivity;
import com.example.palmcampusmarket_client.R;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class NewFragment extends Fragment {

	View view;
	TextView newCommodity;
	TextView newNeed;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(view == null){

			view = inflater.inflate(R.layout.activity_new, null);

			newCommodity = (TextView) view.findViewById(R.id.btn_commodity_new);
			newCommodity.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent itnt = new Intent(NewFragment.this.getActivity(), NewCommodityActivity.class);
					startActivity(itnt);
				}
			});

			newNeed = (TextView) view.findViewById(R.id.btn_need_new);
			newNeed.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent itnt = new Intent(NewFragment.this.getActivity(), NewNeedActivity.class);
					startActivity(itnt);
				}
			});

		}


		return view;
	}

}
