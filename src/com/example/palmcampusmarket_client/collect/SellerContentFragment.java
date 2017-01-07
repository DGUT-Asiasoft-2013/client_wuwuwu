package com.example.palmcampusmarket_client.collect;


import com.example.palmcampusmarket_client.R;
import com.example.palmcampusmarket_client.api.entity.Commodity;
import com.example.palmcampusmarket_client.fragment.AvatarView;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class SellerContentFragment extends Fragment {

	View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(view == null){
			view = inflater.inflate(R.layout.fragment_seller_content, null);
			final Commodity commodity = (Commodity) getActivity().getIntent().getSerializableExtra("commodity");


			AvatarView avatar;
			avatar = (AvatarView) view.findViewById(R.id.avatar_seller_content);
			avatar.load(commodity.getUser());			

			TextView name;
			name = (TextView) view.findViewById(R.id.name_seller_content);
			name.setText(commodity.getUser().getNickname());


			TextView account;
			account = (TextView) view.findViewById(R.id.account_seller_content);
			account.setText(commodity.getUser().getAccount());

			TextView telephone;
			telephone = (TextView) view.findViewById(R.id.telephone_seller_content);
			telephone.setText(commodity.getUser().getTelephone());
			view.findViewById(R.id.btn_seller_telephone).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + Integer.parseInt(commodity.getUser().getTelephone())));
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
				}
			});

			TextView address;
			address = (TextView) view.findViewById(R.id.address_seller_content);
			address.setText(commodity.getUser().getAddress());


		}		

		return view;
	}
}
