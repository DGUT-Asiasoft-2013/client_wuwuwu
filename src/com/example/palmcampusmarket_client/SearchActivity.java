package com.example.palmcampusmarket_client;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.example.palmcampusmarket_client.api.Server;
import com.example.palmcampusmarket_client.api.entity.Page;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.media.Image;
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

public class SearchActivity extends Activity {

	EditText edit;
	Button search;

	View image;
	TextView name;
	TextView describe;
	TextView price;

	ListView listView;

	int page;	
	//	List<Goods> data;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		edit = (EditText) findViewById(R.id.edit_search);
		search = (Button) findViewById(R.id.btn_search);



		listView = (ListView) findViewById(R.id.list_search);
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

		findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() {				
			@Override
			public void onClick(View v) {
				search();					
			}
		});
	}

	void search() {
		reload();		
	}

	BaseAdapter listAdapter = new BaseAdapter() {

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;

			if(convertView==null){
				LayoutInflater inflater = LayoutInflater.from(parent.getContext());
				view = inflater.inflate(R.layout.fragment_search_item, null);	
			}else{
				view = convertView;
			}

			image = findViewById(R.id.image_search);
			name = (TextView) findViewById(R.id.name_search);
			describe = (TextView) findViewById(R.id.describe_search);
			price = (TextView) findViewById(R.id.price_search);


			//			Goods goods = data.get(position);
			//			
			//			image.load();
			//			name.setText(goods.getName());
			//			describe.setText(goods.getDescribe());
			//			price.setText(goods.getPrice());


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
		Request request = Server.requestBuilderWithApi("goods/s/"+edit.getText())
				.build();

		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(final Call arg0, Response arg1) throws IOException {
				try {
					//					Page<Goods> article = new ObjectMapper().readValue(arg1.body().string(), 
					//							new TypeReference<Page<Goods>>() {});

					//					SearchActivity.this.page = goods.getNumber();
					//					SearchActivity.this.data = goods.getContent();
					SearchActivity.this.runOnUiThread(new Runnable() {
						public void run() {
							listAdapter.notifyDataSetInvalidated();
						}
					});					
				} catch (final Exception e) {
					SearchActivity.this.runOnUiThread(new Runnable() {
						public void run() {							
							new AlertDialog.Builder(SearchActivity.this)
							.setMessage(e.getMessage())
							.show();
						}
					});
				}
			}

			@Override
			public void onFailure(final Call arg0, final IOException arg1) {
				SearchActivity.this.runOnUiThread(new Runnable() {
					public void run() {
						new AlertDialog.Builder(SearchActivity.this)
						.setMessage(arg1.getMessage())
						.show();
					}
				});
			}
		});
	}


	void onItemClicked(int position){
		//		Intent itnt = new Intent(this, GoodsContentActivity.class);
		//
		//		itnt.putExtra("goods", data.get(position));
		//		startActivity(itnt);
	}

}
