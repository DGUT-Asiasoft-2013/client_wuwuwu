package com.example.palmcampusmarket_client.setting;

import java.io.IOException;
import java.util.List;

import com.example.palmcampusmarket_client.R;
import com.example.palmcampusmarket_client.api.Server;
import com.example.palmcampusmarket_client.api.entity.Address;
import com.example.palmcampusmarket_client.api.entity.Commodity;
import com.example.palmcampusmarket_client.api.entity.Page;
import com.example.palmcampusmarket_client.api.entity.User;
import com.example.palmcampusmarket_client.collect.CommodityContentActivity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class AddressListFragment extends Fragment {
	View view;
	ImageButton imageButton;
	ListView listView;
	Button btnAddAddress;
	List<Address> data;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(view == null){
			view = inflater.inflate(R.layout.fragment_page_myaddresslist_item, null);

			listView = (ListView) view.findViewById(R.id.addresslist);
			listView.setAdapter(listAdapter);

			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

					onItemClicked(position);
				}


			});

			imageButton = (ImageButton) view.findViewById(R.id.addresslist_back);

			imageButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setClass(getActivity(), SettingActivity.class);
					startActivity(intent);

				}
			});

			btnAddAddress = (Button) view.findViewById(R.id.add_address);

			btnAddAddress.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					intent.setClass(getActivity(), NewAddressActivity.class);
					startActivity(intent);
				}
			});

		}
		return view;
	}

	BaseAdapter listAdapter = new BaseAdapter() {
		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view =null;
			if(convertView == null){
				LayoutInflater inflater = LayoutInflater.from(parent.getContext());
				view = inflater.inflate(R.layout.fragment_page_myaddress_item, null);
			}else{
				view = convertView;
			}

			TextView addName = (TextView) view.findViewById(R.id.name_address);
			TextView addTelephone = (TextView) view.findViewById(R.id.telephone_address);
			TextView addAddress = (TextView) view.findViewById(R.id.play_address);
			TextView adddefault = (TextView) view.findViewById(R.id.default_address);


			final Address address = data.get(position);
			Log.v("isDefaultInfo"+address.getAddress_name(), String.valueOf(address.isDefaultInfo())); 

			if(address.isDefaultInfo()){
				//Log.v("isDefaultInfo"+address.getAddress_name(), String.valueOf(address.isDefaultInfo())); 
				adddefault.setVisibility(View.VISIBLE);
				adddefault.setText("默认地址");
			}else
				adddefault.setVisibility(View.INVISIBLE);


			addName.setText("收货人: "+address.getAddress_name());
			addTelephone.setText("电话: "+address.getAddress_telephone());
			addAddress.setText("地址: "+address.getAddress());

			return view;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return data.get(position);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data==null ? 0:data.size();
		}
	};

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		reload();
	}

	void onItemClicked(int position){
		//		user = data.get(position);
		Address address = data.get(position);
		Intent itnt = new Intent(getActivity(),AddressContentActivity.class);
		itnt.putExtra("address", address);
		startActivity(itnt);
	}

	void reload(){

		Request request = Server.requestBuilderWithApi("addresslist")
				.get()
				.build();

		Server.getSharedClient().newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				try{
					data = new ObjectMapper()
							.readValue(arg1.body().string(), 
									new TypeReference<List<Address>>() {});

					getActivity().runOnUiThread(new Runnable() {

						@Override
						public void run() {
							//	AddressListFragment.this.data=data.getContent();
							//							Toast.makeText(getActivity(), "数据解析成功", Toast.LENGTH_LONG).show();
							//listAdapter.notifyDataSetInvalidated();
							listAdapter.notifyDataSetChanged();

						}
					});
				}catch (final Exception e) {
					getActivity().runOnUiThread(new Runnable() {

						@Override
						public void run() {
							new AlertDialog.Builder(getActivity())
							.setMessage(e.getMessage())
							.show();

						}
					});
				}

			}

			@Override
			public void onFailure(Call arg0, final IOException e) {
				new AlertDialog.Builder(getActivity())
				.setMessage(e.getMessage())
				.show();

			}
		});
	}

}
