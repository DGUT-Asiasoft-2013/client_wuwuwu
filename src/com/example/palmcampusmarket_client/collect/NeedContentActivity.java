package com.example.palmcampusmarket_client.collect;

import java.text.SimpleDateFormat;

import com.example.palmcampusmarket_client.R;
import com.example.palmcampusmarket_client.api.Server;
import com.example.palmcampusmarket_client.api.entity.Need;
import com.example.palmcampusmarket_client.fragment.AvatarView;

import android.app.Activity;
import android.app.Fragment;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class NeedContentActivity extends Activity {
	
	ImageButton btnReply;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_need_content);

		final Need need = (Need) getIntent().getSerializableExtra("need");


		btnReply = (ImageButton) findViewById(R.id.btn_reply_need);

		btnReply.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		TextView title = (TextView) findViewById(R.id.title_need_content);
		title.setText(need.getTitle());

		TextView time = (TextView) findViewById(R.id.time_need_content);
		double t = need.getEndDate().getTime()-System.currentTimeMillis();
		int day = (int) Math.ceil(t/1000/60/60/24);
		time.setText(day+"天");

		AvatarView avatar = (AvatarView) findViewById(R.id.avatar_need_content);
		avatar.load(need.getUser());

		TextView name = (TextView) findViewById(R.id.name_need_content);
		name.setText(need.getUser().getNickname());
		
		TextView createDate = (TextView) findViewById(R.id.createdate_need_content);
		createDate.setText(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(need.getCreateDate()));
		
		TextView endDate = (TextView) findViewById(R.id.enddate_need_content);
		endDate.setText(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(need.getEndDate()));

		TextView text = (TextView) findViewById(R.id.text_need_content);
		text.setText(need.getContent());

		getFragmentManager()
		.beginTransaction()
		.replace(R.id.need_content_checked, new AuthorContentFragment())
		.commit();

	}

}
