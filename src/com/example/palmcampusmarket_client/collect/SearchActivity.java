package com.example.palmcampusmarket_client.collect;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.example.palmcampusmarket_client.R;
import com.example.palmcampusmarket_client.R.id;
import com.example.palmcampusmarket_client.R.layout;
import com.example.palmcampusmarket_client.api.Server;
import com.example.palmcampusmarket_client.api.entity.Commodity;
import com.example.palmcampusmarket_client.api.entity.Page;
import com.example.palmcampusmarket_client.collect.CountOfCollected.OnCountResultListener;
import com.example.palmcampusmarket_client.fragment.AvatarView;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchActivity extends Activity {

	EditText edit;
	Button search;


	RadioGroup radioGroup;
	String sort = "time";
	ListView listView;
	int page;	
	List<Commodity> data;



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
				search(sort);
			}
		});
		radioGroup = (RadioGroup) findViewById(R.id.sort);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub

				int radioButtonId = group.getCheckedRadioButtonId();

				RadioButton rb = (RadioButton) findViewById(radioButtonId);
				if(rb.getText().equals("最新")){
					sort = "time";
					search(sort);
				}else if(rb.getText().equals("最贵")){
					sort = "highprice";
					search(sort);
				}else if(rb.getText().equals("最便宜")){
					sort = "lowprice";
					search(sort);
				}
			}
		});
	}



	void search(String sort) {
		if(!edit.getText().toString().equals("")){
			reload(sort);
		}else{
			Toast.makeText(SearchActivity.this, "输入商品名称", Toast.LENGTH_SHORT).show();
			return;}
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

			AvatarView image;
			TextView name;
			TextView describe;
			TextView price;

			final TextView countCollected;

			image = (AvatarView) view.findViewById(R.id.image_search);
			name = (TextView) view.findViewById(R.id.name_search);
			describe = (TextView) view.findViewById(R.id.describe_search);
			price = (TextView) view.findViewById(R.id.price_search);


			CountOfCollected count = new CountOfCollected();
			countCollected = (TextView) view.findViewById(R.id.collected_search);

			final Commodity commodity = data.get(position);

			image.load(Server.serverAddress + commodity.getCommImage());
			name.setText(commodity.getCommName());
			describe.setText(commodity.getCommDescribe());
			price.setText(commodity.getCommPrice());
			countCollected.setTag(commodity);

			CountOfCollected.getCount(commodity, new OnCountResultListener() {
				@Override
				public void onResult(String result) {
					Object countingTag = countCollected.getTag();

					if(countingTag == commodity){
						countCollected.setText(result +"收藏");	
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

	void reload(String sort){
		OkHttpClient client = Server.getSharedClient();
		Request request = Server.requestBuilderWithCs("commodity/s/"+edit.getText()+"/"+sort)
				.build();

		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(final Call arg0, Response arg1) throws IOException {
				try {
					Page<Commodity> commodity = new ObjectMapper().readValue(arg1.body().string(), 
							new TypeReference<Page<Commodity>>() {});

					SearchActivity.this.page = commodity.getNumber();
					SearchActivity.this.data = commodity.getContent();
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
				Intent itnt = new Intent(this, CommodityContentActivity.class);
		
				itnt.putExtra("commodity", data.get(position));
				startActivity(itnt);
	}

}
