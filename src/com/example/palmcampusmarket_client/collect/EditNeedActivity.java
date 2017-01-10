package com.example.palmcampusmarket_client.collect;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.example.palmcampusmarket_client.NewCommodityActivity;
import com.example.palmcampusmarket_client.R;
import com.example.palmcampusmarket_client.api.Server;
import com.example.palmcampusmarket_client.api.entity.Need;
import com.example.palmcampusmarket_client.fragment.inputcell.CommoditySimpleTextInputCellFragment;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EditNeedActivity extends Activity {

	CommoditySimpleTextInputCellFragment fragInputCellTitle;
	EditText content;
	EditText day;
	Button send;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.fragment_pag_addneeds);
		final Need need = (Need) getIntent().getSerializableExtra("need");
		fragInputCellTitle = (CommoditySimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.need_name);
		fragInputCellTitle.setLabelText("标题");{
			fragInputCellTitle.setHintText("请输入标题");
			fragInputCellTitle.setText(need.getTitle());
		}
		
		day = (EditText) findViewById(R.id.need_day);
		double t = need.getEndDate().getTime()-System.currentTimeMillis();
		int d = (int) Math.ceil(t/1000/60/60/24);
		day.setText(d+"");

		content = (EditText) findViewById(R.id.need_content);
		content.setText(need.getContent());

		send = (Button) findViewById(R.id.btn_send_need);
		send.setText("修改");
		send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				submit(need);
				
			}
		});
		

	}
	
	void submit(Need need){
		
		String title = fragInputCellTitle.getText();
		if(title.equals(null)){
			title = need.getTitle();
		}
		
		String text = content.getText().toString();
		if(text.equals(null)){
			text = need.getContent();
		}
		
		String dtime;
		if(day.getText().toString().equals(null)){
			day = (EditText) findViewById(R.id.need_day);
			double t = need.getEndDate().getTime()-System.currentTimeMillis();
			int d = (int) Math.ceil(t/1000/60/60/24);
			dtime = d+"";
		}else{
			dtime = day.getText().toString();
		}
		
		
		
		
		
		
		OkHttpClient client = Server.getSharedClient();
		
		MultipartBody.Builder body = new MultipartBody.Builder()
				.setType(MultipartBody.FORM)
				.addFormDataPart("need_id",need.getId().toString())
				.addFormDataPart("title", title)
				.addFormDataPart("content", text)
				.addFormDataPart("day", dtime);
		
		Request request = Server.requestBuilderWithCs("need/edit")
				.method("post", null)
				.post(body.build())
				.build();
		
		final ProgressDialog progressDialog = new ProgressDialog(EditNeedActivity.this);
		progressDialog.setMessage("请稍后");
		progressDialog.setCancelable(false);
		progressDialog.setCanceledOnTouchOutside(false);
		
		client.newCall(request).enqueue(new Callback() {
			
			@Override
			public void onResponse(final Call arg0, Response arg1) throws IOException {
				// TODO Auto-generated method stub
				final String responseString = arg1.body().string();
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						progressDialog.dismiss();
						
						try {
							
							EditNeedActivity.this.onResponse(arg0,responseString);
							
						} catch (Exception e) {
							// TODO: handle exception
							
							e.printStackTrace();
							EditNeedActivity.this.onFailure(arg0,e);
							
						}
						
					}
				});
				
				
			}
			
			@Override
			public void onFailure(final Call arg0, final IOException arg1) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						progressDialog.dismiss();
						onFailure(arg0, arg1);
					}
				});
			}
		});
		
		
		
	}

	
	protected void onFailure(Call arg0, Exception e) {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(this)
		.setTitle("修改失败")
		//		.setMessage(arg1.getMessage())
		.setNegativeButton("返回", null)
		.show();
	}

	protected void onResponse(Call arg0, String responseString) {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(this)
		.setTitle("修改成功")
		.setPositiveButton("退出", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				finish();
			}
		}).show();
		
	}

}
