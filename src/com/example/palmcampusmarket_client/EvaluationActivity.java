package com.example.palmcampusmarket_client;

import java.io.IOException;

import com.example.palmcampusmarket_client.api.Server;
import com.example.palmcampusmarket_client.api.entity.Commodity;
import com.example.palmcampusmarket_client.fragment.AvatarView;
import com.example.palmcampusmarket_client.fragment.ImageDown;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.MultipartBody.Builder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EvaluationActivity extends Activity {

	TextView commodityDescribe;
	ImageDown commodityPicture;
	EditText evaluation;
	RatingBar ratingBar;
	Commodity commodity;


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_evaluation);
		commodityDescribe=(TextView)findViewById(R.id.commodity_describe);
		commodityPicture=(ImageDown)findViewById(R.id.commodity_picture);
		evaluation=(EditText)findViewById(R.id.evaluation);
		ratingBar=(RatingBar)findViewById(R.id.ratingBar);

		commodity=(Commodity)getIntent().getSerializableExtra("infomation");//从商品详情页面获取商品信息

		findViewById(R.id.evaluation_submit).setOnClickListener(new View.OnClickListener() { ///按下提交评价按钮

			@Override
			public void onClick(View v) {
				goEvaluationSubmit();

			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();

		commodityDescribe.setText(commodity.getCommDescribe());
		commodityPicture.load(Server.serverAddress+commodity.getCommImage());
	}

	void goEvaluationSubmit(){
		Integer commodity_Id = commodity.getId();

		OkHttpClient client = Server.getSharedClient();

		MultipartBody.Builder requestBody =new MultipartBody.Builder()
				.addFormDataPart("commodity_Id", commodity_Id.toString())
				.addFormDataPart("commName",commodity.getCommName())
				.addFormDataPart("rank", ratingBar.toString())
				.addFormDataPart("evaluation", evaluation.getText().toString());//


		Request request =Server.requestBuilderWithApi("evaluation")
				.method("post", null)
				.post(requestBody.build())
				.build();
		
		final ProgressDialog progressDialog = new ProgressDialog(EvaluationActivity.this);
		progressDialog.setMessage("请稍候");
		progressDialog.setCancelable(false);
		progressDialog.setCanceledOnTouchOutside(false);
		
		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(final Call arg0, Response arg1) throws IOException {

				final String s = arg1.body().string();
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						progressDialog.dismiss();
						
						try{
							EvaluationActivity.this.onSubmitResponse(arg0, s);
						}catch(Exception e){
							Toast.makeText(getBaseContext(),s,Toast.LENGTH_LONG).show();
						}
					}
				});

			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {


			}
		});
	}
	
	protected void onSubmitResponse(Call arg0, String s) {
		new AlertDialog.Builder(this)
		.setTitle("评价成功")
		.setMessage(s)
		.setPositiveButton("好", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();

			}
		})
		.show();
	}
}
