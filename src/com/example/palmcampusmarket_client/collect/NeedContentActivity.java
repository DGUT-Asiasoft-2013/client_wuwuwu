package com.example.palmcampusmarket_client.collect;

import java.io.IOException;
import java.text.SimpleDateFormat;

import com.example.palmcampusmarket_client.R;
import com.example.palmcampusmarket_client.api.Server;
import com.example.palmcampusmarket_client.api.entity.Need;
import com.example.palmcampusmarket_client.api.entity.User;
import com.example.palmcampusmarket_client.fragment.AvatarView;
import com.example.palmcampusmarket_client.fragment.pages.MeFragment;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.Activity;
import android.app.Fragment;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NeedContentActivity extends Activity {

	ImageButton btnReply;
	ImageButton btnMenu;
	User current_user;
	View menuFrag;
	TextView title;
	TextView time;
	TextView endDate;
	TextView text;
	Need need;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_need_content);
		need = (Need) getIntent().getSerializableExtra("need");
		getCurrent_user(need);
		menuFrag = (View)findViewById(R.id.frame_menu_need_outside);
		menuFrag.setVisibility(View.GONE);
		if(need.getState()== 0){

			btnMenu = (ImageButton) findViewById(R.id.btn_menu_need);

			btnReply = (ImageButton) findViewById(R.id.btn_reply_need);


			btnMenu.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(menuFrag.getVisibility() == View.VISIBLE){
						menuFrag.setVisibility(View.GONE);

					}else{
						menuFrag.setVisibility(View.VISIBLE);
					}
				}
			});

			menuFrag.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(menuFrag.getVisibility() == View.VISIBLE){
						menuFrag.setVisibility(View.GONE);
					}				
				}
			});


			btnReply.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

				}
			});
		}

		
		title = (TextView) findViewById(R.id.title_need_content);
		title.setText(need.getTitle());

		time = (TextView) findViewById(R.id.time_need_content);
		double t = need.getEndDate().getTime()-System.currentTimeMillis();
		int day = (int) Math.ceil(t/1000/60/60/24);
		if(need.getState() == 0){
			if(day > 0){
				time.setText(day+"天");
			}else if(day <= 0){
				time.setText("已过期");
			}
		}else{
			time.setText("已完成");
		}

		AvatarView avatar = (AvatarView) findViewById(R.id.avatar_need_content);
		avatar.load(need.getUser());

		TextView name = (TextView) findViewById(R.id.name_need_content);
		name.setText(need.getUser().getNickname());

		TextView createDate = (TextView) findViewById(R.id.createdate_need_content);
		createDate.setText(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(need.getCreateDate()));

		endDate = (TextView) findViewById(R.id.enddate_need_content);
		endDate.setText(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(need.getEndDate()));

		text = (TextView) findViewById(R.id.text_need_content);
		text.setText(need.getContent());

		getFragmentManager()
		.beginTransaction()
		.replace(R.id.need_content_checked, new AuthorContentFragment())
		.commit();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();		
		refresh();

	}




	private void refresh() {
		// TODO Auto-generated method stub
		Toast.makeText(this,"11111111", Toast.LENGTH_LONG).show();
		OkHttpClient client = Server.getSharedClient();

		MultipartBody.Builder body = new MultipartBody.Builder()
				.setType(MultipartBody.FORM)
				.addFormDataPart("need_id",String.valueOf(need.getId()));

		Request request = Server.requestBuilderWithCs("need/findOne")
				.method("post", null)
				.post(body.build())
				.build();

		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				// TODO Auto-generated method stub
				try {
					need = new ObjectMapper().readValue(arg1.body().bytes(), Need.class);

					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(NeedContentActivity.this,"3333", Toast.LENGTH_LONG).show();
							title.setText(need.getTitle());

							double t = need.getEndDate().getTime()-System.currentTimeMillis();
							int day = (int) Math.ceil(t/1000/60/60/24);
							if(need.getState() == 0){
								if(day > 0){
									time.setText(day+"天");
								}else if(day <= 0){
									time.setText("已过期");
								}
							}else{
								time.setText("已完成");
							}

							endDate.setText(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(need.getEndDate()));

							text.setText(need.getContent());

						}
					});
				} catch (Exception e) {
					// TODO: handle exception
					
				}
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				// TODO Auto-generated method stub

				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub

						Toast.makeText(NeedContentActivity.this,"555", Toast.LENGTH_LONG).show();
					}
				});
				
			}
		});

	}

	private void getCurrent_user(final Need need) {
		// TODO Auto-generated method stub
		OkHttpClient client = Server.getSharedClient();
		Request request = Server.requestBuilderWithApi("me").get().build();
		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(final Call call, final IOException e) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						NeedContentActivity.this.onFailuer(call,e);
					}
				});

			}
			@Override
			public void onResponse(final Call call, final Response response) throws IOException {
				try {
					final User user = new ObjectMapper().readValue(response.body().bytes(), User.class);
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							NeedContentActivity.this.onResponse(need,call, user);
						}
					});
				}catch (final Exception e){
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							NeedContentActivity.this.onFailuer(call,e);
						}
					});
				}

			}
		});
	}


	protected void onResponse(Need need,Call call, User user){
		current_user = user;
		if(need.getState() == 0){
			if(need.getUser().getId().equals(current_user.getId())){
				btnMenu.setVisibility(View.VISIBLE);
				btnReply.setVisibility(View.GONE);
			}else{
				btnMenu.setVisibility(View.GONE);
				btnReply.setVisibility(View.VISIBLE);
			}
		}
	}

	protected void onFailuer(Call call, Exception ex){

	}

}
