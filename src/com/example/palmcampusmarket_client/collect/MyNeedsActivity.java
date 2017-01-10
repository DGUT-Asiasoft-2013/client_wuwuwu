package com.example.palmcampusmarket_client.collect;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.example.palmcampusmarket_client.R;
import com.example.palmcampusmarket_client.api.Server;
import com.example.palmcampusmarket_client.api.entity.Need;
import com.example.palmcampusmarket_client.api.entity.Commodity;
import com.example.palmcampusmarket_client.api.entity.Page;
import com.example.palmcampusmarket_client.fragment.AvatarView;
import com.example.palmcampusmarket_client.fragment.pages.HomeListFragment;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyNeedsActivity extends Activity {

	ListView listView;

	View btnLoadMore;
	TextView textLoadMore;

	int page = 0;	
	List<Need> data;

	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

	int MID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_needs);
		super.onCreate(savedInstanceState);
		findViewById(R.id.needs_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();

			}
		});
		btnLoadMore = getLayoutInflater().inflate(R.layout.widget_load_more_button, null);
		textLoadMore =(TextView) btnLoadMore.findViewById(R.id.text_more);

		listView = (ListView) findViewById(R.id.list_needs);
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




	@Override
	public void onResume() {
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
				view = inflater.inflate(R.layout.fragment_needs_item, null);	
			}else{
				view = convertView;
			}

			AvatarView avatar;
			TextView name;
			TextView title;
			TextView text;

			TextView time;

			avatar = (AvatarView) view.findViewById(R.id.avatar_needs_author);
			name = (TextView) view.findViewById(R.id.name_needs_author);
			title = (TextView) view.findViewById(R.id.title_needs);
			text = (TextView) view.findViewById(R.id.text_needs);
			time = (TextView) view.findViewById(R.id.time_needs);

			Need needs = data.get(position);

			avatar.load(Server.serverAddress + needs.getUser().getAvatar());
			name.setText(needs.getUser().getNickname());
			title.setText(needs.getTitle());
			text.setText(needs.getContent());
			double t = needs.getEndDate().getTime()-System.currentTimeMillis();
			int day = (int) Math.ceil(t/1000/60/60/24);
			if(needs.getState()==null || needs.getState() == 0){
				if(day > 0){
					time.setText(day+"天");
				}else if(day <= 0){
					time.setText("已过期");
				}
			}else{
				time.setText("已完成");
			}



			return view;
		}

		/**
		 * 获取两个日期之间的间隔天数,进一法。
		 * @return
		 */
		public int getGapCount(Date endDate) {
			double millisecond = endDate.getTime()-System.currentTimeMillis();

			return (int) Math.ceil(millisecond/1000/60/60/24);
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

	SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");       
	Date curDate = new Date(System.currentTimeMillis());//获取当前时间       
	String now = formatter.format(curDate);  

	void reload(){

		Toast.makeText(this, "reload", Toast.LENGTH_SHORT).show();

		OkHttpClient client = Server.getSharedClient();
		Request request = Server.requestBuilderWithCs("need/my")
				.get()
				.build();

		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(final Call arg0, Response arg1) throws IOException {

				try {
					final Page<Need> needs = new ObjectMapper().readValue(arg1.body().string(), 
							new TypeReference<Page<Need>>() {});


					runOnUiThread(new Runnable() {
						public void run() {
							MyNeedsActivity.this.page = needs.getNumber();
							MyNeedsActivity.this.data = needs.getContent();
							listAdapter.notifyDataSetInvalidated();
						}
					});					
				} catch (final Exception e) {
					runOnUiThread(new Runnable() {
						public void run() {
							new AlertDialog.Builder(MyNeedsActivity.this)
							.setMessage(e.getMessage())
							.show();
						}
					});
				}
			}

			@Override
			public void onFailure(final Call arg0, final IOException arg1) {
				runOnUiThread(new Runnable() {
					public void run() {
						new AlertDialog.Builder(MyNeedsActivity.this)
						.setMessage(arg1.getMessage())
						.show();
					}
				});
			}
		});
	}


	void onItemClicked(int position){
		Intent itnt = new Intent(this, NeedContentActivity.class);

		itnt.putExtra("need", data.get(position));
		startActivity(itnt);
	}


	void loadmore(){
		btnLoadMore.setEnabled(false);
		textLoadMore.setText("载入中");
		Request request = Server.requestBuilderWithCs("needs/my/"+(page+1)).get().build();
		Server.getSharedClient().newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						btnLoadMore.setEnabled(true);
						textLoadMore.setText("加载更多");

					}
				});
				try{
					final Page<Need> need = new ObjectMapper().readValue(arg1.body().string(), new TypeReference<Page<Commodity>>() {});
					if(need.getNumber()>page){
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								if(data==null){
									data = need.getContent();
								}else{
									data.addAll(need.getContent());
								}
								page = need.getNumber();
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
				runOnUiThread(new Runnable() {

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
