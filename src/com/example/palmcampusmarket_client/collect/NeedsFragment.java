package com.example.palmcampusmarket_client.collect;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.example.palmcampusmarket_client.R;
import com.example.palmcampusmarket_client.R.id;
import com.example.palmcampusmarket_client.R.layout;
import com.example.palmcampusmarket_client.api.Server;
import com.example.palmcampusmarket_client.api.entity.Need;
import com.example.palmcampusmarket_client.api.entity.Commodity;
import com.example.palmcampusmarket_client.api.entity.Need;
import com.example.palmcampusmarket_client.api.entity.Page;
import com.example.palmcampusmarket_client.collect.CountOfCollected.OnCountResultListener;
import com.example.palmcampusmarket_client.fragment.AvatarView;
import com.example.palmcampusmarket_client.fragment.ImageView;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NeedsFragment extends Fragment {

	ListView listView;
	
	View btnLoadMore;
	TextView textLoadMore;

	int page = 0;	
	List<Need> data;

	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

	int MID;

	View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(view == null){
			view = inflater.inflate(R.layout.activity_needs,null);
			
			btnLoadMore = inflater.inflate(R.layout.widget_load_more_button, null);
			textLoadMore =(TextView) btnLoadMore.findViewById(R.id.text_more);
			
			listView = (ListView) view.findViewById(R.id.list_needs);
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


			onItemLongClicked();
		}
		return view;
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

			final TextView time;

			avatar = (AvatarView) view.findViewById(R.id.avatar_needs_author);
			name = (TextView) view.findViewById(R.id.name_needs_author);
			title = (TextView) view.findViewById(R.id.title_needs);
			text = (TextView) view.findViewById(R.id.text_needs);
			time = (TextView) view.findViewById(R.id.time_needs);

			Need needs = data.get(position);

			avatar.load(Server.serverAddress + needs.getUser().getAvatar());
			name.setText(needs.getUser().getNickname());
			title.setText(needs.getTitle());
			text.setText(needs.getText());
			
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
			time.setText(getGapCount(needs.getCreateDate(), needs.getEndDate())+"天");



			return view;
		}
		
		/**
		* 获取两个日期之间的间隔天数
		* @return
		*/
		public int getGapCount(Date startDate, Date endDate) {
		        Calendar fromCalendar = Calendar.getInstance();  
		        fromCalendar.setTime(startDate);  
		        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);  
		        fromCalendar.set(Calendar.MINUTE, 0);  
		        fromCalendar.set(Calendar.SECOND, 0);  
		        fromCalendar.set(Calendar.MILLISECOND, 0);  
		  
		        Calendar toCalendar = Calendar.getInstance();  
		        toCalendar.setTime(endDate);  
		        toCalendar.set(Calendar.HOUR_OF_DAY, 0);  
		        toCalendar.set(Calendar.MINUTE, 0);  
		        toCalendar.set(Calendar.SECOND, 0);  
		        toCalendar.set(Calendar.MILLISECOND, 0);  
		  
		        return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
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

		Toast.makeText(getActivity(), "reload", Toast.LENGTH_SHORT).show();

		OkHttpClient client = Server.getSharedClient();
		Request request = Server.requestBuilderWithCs("needs")
				.get()
				.build();

		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(final Call arg0, Response arg1) throws IOException {

				try {
					final Page<Need> needs = new ObjectMapper().readValue(arg1.body().string(), 
							new TypeReference<Page<Need>>() {});


					getActivity().runOnUiThread(new Runnable() {
						public void run() {
							NeedsFragment.this.page = needs.getNumber();
							NeedsFragment.this.data = needs.getContent();
							listAdapter.notifyDataSetInvalidated();
						}
					});					
				} catch (final Exception e) {
					getActivity().runOnUiThread(new Runnable() {
						public void run() {
							new AlertDialog.Builder(getActivity())
							.setMessage(e.getMessage())
							.show();
						}
					});
				}
			}

			@Override
			public void onFailure(final Call arg0, final IOException arg1) {
				getActivity().runOnUiThread(new Runnable() {
					public void run() {
						new AlertDialog.Builder(getActivity())
						.setMessage(arg1.getMessage())
						.show();
					}
				});
			}
		});
	}


	void onItemClicked(int position){
		Intent itnt = new Intent(getActivity(), CommodityContentActivity.class);

		itnt.putExtra("need", data.get(position));
		startActivity(itnt);
	}


	void onItemLongClicked() {

		listView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {

			@Override
			public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
				menu.add(0,0,0,"查看商品详情");
				menu.add(0,1,0,"取消收藏");		
			}
		});

	}


	public boolean onContextItemSelected(MenuItem item) {

		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		MID = (int) info.id;

		Need needs = data.get(MID);

		switch(item.getItemId()) {
		case 0:
			Intent itnt = new Intent(getActivity(), CommodityContentActivity.class);

			itnt.putExtra("commodity", needs.getId());
			startActivity(itnt);


			break;

		case 1:

			

			MultipartBody body = new MultipartBody
					.Builder()
					.addFormDataPart("collect", "false")
					.build();


			Request request = Server.requestBuilderWithCs("commodity/"+needs.getId()+"/collect")
					.post(body)
					.build();

			Server.getSharedClient().newCall(request).enqueue(new Callback() {

				@Override
				public void onResponse(Call arg0, Response arg1) throws IOException {
					getActivity().runOnUiThread(new Runnable() {

						@Override
						public void run() {
							Toast.makeText(getActivity(),
									"已删除该收藏",
									Toast.LENGTH_SHORT).show();
							reload();
						}
					});

				}

				@Override
				public void onFailure(Call arg0, IOException arg1) {
					// TODO Auto-generated method stub

					getActivity().runOnUiThread(new Runnable() {

						@Override
						public void run() {						

						}
					});

				}
			});


			break;

		default:
			break;
		}

		return super.onContextItemSelected(item);
	}
	
	void loadmore(){
		btnLoadMore.setEnabled(false);
		textLoadMore.setText("载入中");
		Request request = Server.requestBuilderWithCs("needs/"+(page+1)).get().build();
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
					final Page<Need> need = new ObjectMapper().readValue(arg1.body().string(), new TypeReference<Page<Commodity>>() {});
					if(need.getNumber()>page){
						getActivity().runOnUiThread(new Runnable() {

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
