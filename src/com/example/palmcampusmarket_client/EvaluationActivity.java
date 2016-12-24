package com.example.palmcampusmarket_client;

import java.io.IOException;

import com.example.palmcampusmarket_client.api.Server;
import com.example.palmcampusmarket_client.api.entity.Commodity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EvaluationActivity extends Activity {

	TextView commodityDescribe;
	ImageView commodityPicture;
	EditText evaluation;
	RatingBar ratingBar;
	Commodity commodity;


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_evaluation);
		commodityDescribe=(TextView)findViewById(R.id.commodity_describe);
		commodityPicture=(ImageView)findViewById(R.id.commodity_picture);
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
	}
	
	void goEvaluationSubmit(){
		Integer commodity_Id = commodity.getId();
		
		OkHttpClient client = Server.getSharedClient();
		
		MultipartBody requestBody =new MultipartBody.Builder()
				.addFormDataPart("commmodity_Id", commodity_Id.toString())
				.build();//
				
				
		Request request =Server.requestBuilderWithApi("evaluation")
				.method("post", null)
				.build();
		client.newCall(request).enqueue(new Callback() {
			
			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				
				
			}
			
			@Override
			public void onFailure(Call arg0, IOException arg1) {
				
				
			}
		});
	}
}
