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
import com.example.palmcampusmarket_client.api.entity.Page;
import com.example.palmcampusmarket_client.collect.CountOfCollected.OnCountResultListener;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
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

public class CollectionsActivity extends Activity {

	ListView listView;

	int page;	
	List<Collections> data;

	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

	int MID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collections);
		reload();

		listView = (ListView) findViewById(R.id.list_collections);
		listView.setAdapter(listAdapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				onItemClicked(position);
			}
		});


		onItemLongClicked();



	}





	@Override
	protected void onResume() {
		super.onResume();


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


	void onItemLongClicked() {
		//注：setOnCreateContextMenuListener是与下面onContextItemSelected配套使用的
		listView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {

			@Override
			public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
				menu.add(0,0,0,"进入详情");
				menu.add(0,1,0,"取消收藏");		
			}
		});

	}

	// 长按菜单响应函数
	public boolean onContextItemSelected(MenuItem item) {

		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		MID = (int) info.id;// 这里的info.id对应的就是数据库中_id的值

		Collections collections = data.get(MID);

		switch(item.getItemId()) {
		case 0:
			// 进入详情
			Toast.makeText(this,
					"进入详情",
					Toast.LENGTH_SHORT).show();
			break;

		case 1:
			// 取消收藏
			Toast.makeText(this,
					"取消收藏",
					Toast.LENGTH_SHORT).show();

			MultipartBody body = new MultipartBody
					.Builder()
					.addFormDataPart("collect", "false")
					.build();


			Request request = Server.requestBuilderWithApi("commodity/"+collections.getId().getCommodity().getId()+"/collect")
					.post(body)
					.build();

			Server.getSharedClient().newCall(request).enqueue(new Callback() {

				@Override
				public void onResponse(Call arg0, Response arg1) throws IOException {
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							reload();
						}
					});

				}

				@Override
				public void onFailure(Call arg0, IOException arg1) {
					// TODO Auto-generated method stub

					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							Toast.makeText(CollectionsActivity.this,
									"删除失败",
									Toast.LENGTH_SHORT).show();								

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

}
