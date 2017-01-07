package com.example.palmcampusmarket_client.collect;


import com.example.palmcampusmarket_client.R;
import com.example.palmcampusmarket_client.api.entity.Need;
import com.example.palmcampusmarket_client.fragment.AvatarView;

import android.R.integer;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class AuthorContentFragment extends Fragment {

	View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(view == null){
			view = inflater.inflate(R.layout.fragment_author_content, null);
			final Need need = (Need) getActivity().getIntent().getSerializableExtra("need");

			TextView telephone;
			telephone = (TextView) view.findViewById(R.id.telephone_author_content);
			telephone.setText(need.getUser().getTelephone());
			view.findViewById(R.id.btn_author_telephone).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + Integer.parseInt(need.getUser().getTelephone())));
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
				}
			});
			
			TextView address;
			address = (TextView) view.findViewById(R.id.address_author_content);
			address.setText(need.getUser().getAddress());

			
		}		
		
		return view;
	}
}
