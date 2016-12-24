package com.example.palmcampusmarket_client.collect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.palmcampusmarket_client.R;
import com.example.palmcampusmarket_client.R.id;
import com.example.palmcampusmarket_client.R.layout;
import com.example.palmcampusmarket_client.api.Server;
import com.example.palmcampusmarket_client.api.entity.Collections;
import com.example.palmcampusmarket_client.api.entity.Commodity;
import com.example.palmcampusmarket_client.api.entity.Page;
import com.example.palmcampusmarket_client.collect.CountOfCollected.OnCountResultListener;
import com.example.palmcampusmarket_client.fragment.AvatarView;
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

public class CollectionsFragment extends Fragment {

	ListView listView;
	
	View btnLoadMore;
	TextView textLoadMore;

	int page = 0;	
	List<Collections> data;

	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

	int MID;

	View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(view == null){
			view = inflater.inflate(R.layout.activity_collections,null);
			
			btnLoadMore = inflater.inflate(R.layout.widget_load_more_button, null);
			textLoadMore =(TextView) btnLoadMore.findViewById(R.id.text_more);
			
			listView = (ListView) view.findViewById(R.id.list_collections);
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
				view = inflater.inflate(R.layout.fragment_collections_item, null);	
			}else{
				view = convertView;
			}

			AvatarView image;
			TextView name;
			TextView describe;
			TextView price;

			final TextView countCollected;

			image = (AvatarView) view.findViewById(R.id.image_collections);
			name = (TextView) view.findViewById(R.id.name_collections);
			describe = (TextView) view.findViewById(R.id.describe_collections);
			price = (TextView) view.findViewById(R.id.price_collections);
			countCollected = (TextView) view.findViewById(R.id.collected_collections);

			final Collections collections = data.get(position);

			image.load(Server.serverAddress + collections.getId().getCommodity().getCommImage());
			name.setText(collections.getId().getCommodity().getCommName());
			describe.setText(collections.getId().getCommodity().getCommDescribe());
			price.setText(collections.getId().getCommodity().getCommPrice());

			countCollected.setTag(collections);

			CountOfCollected.getCount(collections.getId().getCommodity(), new OnCountResultListener() {
				@Override
				public void onResult(String result) {
					Object countingTag = countCollected.getTag();

					if(countingTag == collections){
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

	void reload(){

		Toast.makeText(getActivity(), "reload", Toast.LENGTH_SHORT).show();

		OkHttpClient client = Server.getSharedClient();
		Request request = Server.requestBuilderWithCs("collections")
				.get()
				.build();

		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(final Call arg0, Response arg1) throws IOException {

				try {
					final Page<Collections> collections = new ObjectMapper().readValue(arg1.body().string(), 
							new TypeReference<Page<Collections>>() {});


					getActivity().runOnUiThread(new Runnable() {
						public void run() {
							CollectionsFragment.this.page = collections.getNumber();
							CollectionsFragment.this.data = collections.getContent();
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

		itnt.putExtra("commodity", data.get(position).getId().getCommodity());
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

		Collections collections = data.get(MID);

		switch(item.getItemId()) {
		case 0:
			Intent itnt = new Intent(getActivity(), CommodityContentActivity.class);

			itnt.putExtra("collections", collections);
			startActivity(itnt);


			break;

		case 1:

			

			MultipartBody body = new MultipartBody
					.Builder()
					.addFormDataPart("collect", "false")
					.build();


			Request request = Server.requestBuilderWithCs("commodity/"+collections.getId().getCommodity().getId()+"/collect")
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
		Request request = Server.requestBuilderWithCs("collections/"+(page+1)).get().build();
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
					final Page<Collections> collection = new ObjectMapper().readValue(arg1.body().string(), new TypeReference<Page<Commodity>>() {});
					if(collection.getNumber()>page){
						getActivity().runOnUiThread(new Runnable() {

							@Override
							public void run() {
								if(data==null){
									data = collection.getContent();
								}else{
									data.addAll(collection.getContent());
								}
								page = collection.getNumber();
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
