package com.example.palmcampusmarket_client.collect;

import java.io.IOException;
import java.util.List;

import com.example.palmcampusmarket_client.R;
import com.example.palmcampusmarket_client.R.id;
import com.example.palmcampusmarket_client.R.layout;
import com.example.palmcampusmarket_client.api.Server;
import com.example.palmcampusmarket_client.api.entity.Collections;
import com.example.palmcampusmarket_client.api.entity.Page;
import com.example.palmcampusmarket_client.collect.CountOfCollected.OnCountResultListener;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CollectionsActivity extends Activity {

	ListView listView;

	int page;	
	List<Collections> data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collections);

		listView = (ListView) findViewById(R.id.list_collections);
		listView.setAdapter(listAdapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				onItemClicked(position);
			}
		});
	}


	@Override
	protected void onResume() {
		super.onResume();

		reload();
	}


	BaseAdapter listAdapter = new BaseAdapter() {

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;

			if(convertView==null){
				LayoutInflater inflater = LayoutInflater.from(parent.getContext());
				view = inflater.inflate(R.layout.fragment_collections_item, null);	
			}else{
				view = convertView;
			}

			View image;
			TextView name;
			TextView describe;
			TextView price;

			final TextView countCollected;

			image = view.findViewById(R.id.image_collections);
			name = (TextView) view.findViewById(R.id.name_collections);
			describe = (TextView) view.findViewById(R.id.describe_collections);
			price = (TextView) view.findViewById(R.id.price_collections);
			countCollected = (TextView) view.findViewById(R.id.collected_collections);

			final Collections collections = data.get(position);

			//			image.load();
			name.setText(collections.getId().getCommodity().getCommName());
			describe.setText(collections.getId().getCommodity().getCommDescribe());
			price.setText(collections.getId().getCommodity().getCommPrice());

			countCollected.setTag(collections);

			CountOfCollected.getCount(collections.getId().getCommodity(), new OnCountResultListener() {
				@Override
				public void onResult(String result) {
					Object countingTag = countCollected.getTag();

					if(countingTag == collections){
						countCollected.setText("已有" + result +"人收藏");	
					}		
				}
			});

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
			return null;
		}

		@Override
		public int getCount() {
			return data==null ? 0 : data.size();
		}
	};

	void reload(){

		Toast.makeText(this, "reload", Toast.LENGTH_SHORT).show();

		OkHttpClient client = Server.getSharedClient();
		Request request = Server.requestBuilderWithApi("collections")
				.get()
				.build();

		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(final Call arg0, Response arg1) throws IOException {

				try {
					final Page<Collections> collections = new ObjectMapper().readValue(arg1.body().string(), 
							new TypeReference<Page<Collections>>() {});


					CollectionsActivity.this.runOnUiThread(new Runnable() {
						public void run() {
							CollectionsActivity.this.page = collections.getNumber();
							CollectionsActivity.this.data = collections.getContent();
							listAdapter.notifyDataSetInvalidated();
						}
					});					
				} catch (final Exception e) {
					CollectionsActivity.this.runOnUiThread(new Runnable() {
						public void run() {
							new AlertDialog.Builder(CollectionsActivity.this)
							.setMessage(e.getMessage())
							.show();
						}
					});
				}
			}

			@Override
			public void onFailure(final Call arg0, final IOException arg1) {
				CollectionsActivity.this.runOnUiThread(new Runnable() {
					public void run() {
						new AlertDialog.Builder(CollectionsActivity.this)
						.setMessage(arg1.getMessage())
						.show();
					}
				});
			}
		});
	}


	void onItemClicked(int position){
		//		Intent itnt = new Intent(this, CommodityContentActivity.class);
		//
		//		itnt.putExtra("collections", data.get(position));
		//		startActivity(itnt);
	}

}
