package com.example.palmcampusmarket_client;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import com.example.palmcampusmarket_client.api.Server;
import com.example.palmcampusmarket_client.api.entity.Page;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CollectionsActivity extends Activity {
 
	View image;
	TextView name;
	TextView describe;
	TextView price;

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

			image = findViewById(R.id.image_collections);
			name = (TextView) findViewById(R.id.name_collections);
			describe = (TextView) findViewById(R.id.describe_collections);
			price = (TextView) findViewById(R.id.price_collections);


			Collections collections = data.get(position);

//			image.load();
//			name.setText(collections.getCommodity().getName());
//			describe.setText(collections.getCommodity().getDescribe());
//			price.setText(collections.getCommodity().getPrice());


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
			return 1;
			//			return data==null ? 0 : data.size();
		}
	};

	void reload(){
		OkHttpClient client = Server.getSharedClient();
		Request request = Server.requestBuilderWithApi("collections")
				.build();

		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(final Call arg0, Response arg1) throws IOException {
				try {
					Page<Collections> collections = new ObjectMapper().readValue(arg1.body().string(), 
							new TypeReference<Page<Collections>>() {});
					//							.readValue(arg1.body().bytes(), Article.class);

					CollectionsActivity.this.page = collections.getNumber();
					CollectionsActivity.this.data = collections.getContent();
					CollectionsActivity.this.runOnUiThread(new Runnable() {
						public void run() {
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
