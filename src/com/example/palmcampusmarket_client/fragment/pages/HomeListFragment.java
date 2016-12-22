package com.example.palmcampusmarket_client.fragment.pages;

import java.io.IOException;
import java.util.List;

import com.example.palmcampusmarket_client.NewCommodityActivity;
import com.example.palmcampusmarket_client.R;
import com.example.palmcampusmarket_client.api.Server;
import com.example.palmcampusmarket_client.api.entity.Commodity;
import com.example.palmcampusmarket_client.api.entity.Page;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class HomeListFragment extends Fragment {

	View view;
	ListView listView;
	View btnLoadMore;
	TextView textLoadMore;

	List<Commodity> data;
	int page = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if(view == null){
			view = inflater.inflate(R.layout.fragment_page_home_list, null);
			btnLoadMore = inflater.inflate(R.layout.widget_load_more_button, null);
			textLoadMore =(TextView) btnLoadMore.findViewById(R.id.text_more);

			listView = (ListView) view.findViewById(R.id.homelist);
			listView.addFooterView(btnLoadMore);
			listView.setAdapter(listAdapter);

			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

					onItemClicked(position);
				}


			});

			btnLoadMore.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					loadmore();

				}
			});
		}
		return view;



	}

	BaseAdapter listAdapter = new BaseAdapter() {
		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			if(convertView == null){
				LayoutInflater inflater = LayoutInflater.from(parent.getContext());
				view = inflater.inflate(R.layout.wight_feed_item, null);
			}else{
				view = convertView;
			}

			TextView CommContent = (TextView) view.findViewById(R.id.text);
			TextView CommName = (TextView) view.findViewById(R.id.title);
			TextView CommtsellerName = (TextView) view.findViewById(R.id.username);


			Commodity commodity = data.get(position);

			CommContent.setText(commodity.getCommDescribe());
			CommName.setText(commodity.getCommName());
			CommtsellerName.setText(commodity.getUser().getNickname());

			return view;

		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public Object getItem(int position) {

			return data.get(position);
		}

		@Override
		public int getCount() {

			return data==null ? 0:data.size();
		}
	};
	@Override
	public void onResume() {

		super.onResume();
		reload();
	}

	void reload(){
		Request request= Server.requestBuilderWithApi("feeds")
				.get()
				.build();

		Server.getSharedClient().newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				try{
					final Page<Commodity> data = new ObjectMapper()
							.readValue(arg1.body().string(),
									new TypeReference<Page<Commodity>>() {});

					getActivity().runOnUiThread(new Runnable() {

						@Override
						public void run() {
							HomeListFragment.this.page = data.getNumber();
							HomeListFragment.this.data = data.getContent();
							listAdapter.notifyDataSetInvalidated();

						}
					});
				}catch(final Exception e){
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
			public void onFailure(Call arg0,final IOException e) {
				getActivity().runOnUiThread(new Runnable() {

					@Override
					public void run() {
						new AlertDialog.Builder(getActivity())
						.setMessage(e.getMessage())
						.show();

					}
				});

			}
		});
	}

	void onItemClicked(int position){
//		Commodity comm = data.get(position);
//		Intent itnt = new Intent(getActivity(),NewCommodityActivity.class);
//		itnt.putExtra("Commodity", comm);
//		startActivity(itnt);
	}

	void loadmore(){
		btnLoadMore.setEnabled(false);
		textLoadMore.setText("载入中…");
		Request request = Server.requestBuilderWithApi("feeds/"+(page+1)).get().build();
		Server.getSharedClient().newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				getActivity().runOnUiThread(new Runnable() {

					@Override
					public void run() {
						btnLoadMore.setEnabled(true);
						textLoadMore.setText("加载更多");

					}
				});
				try{
					final Page<Commodity> home = new ObjectMapper().readValue(arg1.body().string(), new TypeReference<Page<Commodity>>() {});
					if(home.getNumber()>page){
						getActivity().runOnUiThread(new Runnable() {

							@Override
							public void run() {
								if(data==null){
									data = home.getContent();
								}else{
									data.addAll(home.getContent());
								}
								page = home.getNumber();
								listAdapter.notifyDataSetChanged();

							}
						});
					}
				}catch(Exception ex){
					ex.printStackTrace();
				}

			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				getActivity().runOnUiThread(new Runnable() {

					@Override
					public void run() {
						btnLoadMore.setEnabled(true);
						textLoadMore.setText("加载更多");

					}
				});

			}
		});
	}

}
