package com.example.palmcampusmarket_client;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class EvaluationActivity extends Activity {

	TextView commodityDescribe;
	ImageView commodityPicture;
	EditText evaluation;
	RatingBar ratingBar;


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_evaluation);
		commodityDescribe=(TextView)findViewById(R.id.commodity_describe);
		commodityPicture=(ImageView)findViewById(R.id.commodity_picture);
		evaluation=(EditText)findViewById(R.id.evaluation);
		ratingBar=(RatingBar)findViewById(R.id.ratingBar);

		findViewById(R.id.evaluation_submit).setOnClickListener(new View.OnClickListener() { //按下提交评价按钮

			@Override
			public void onClick(View v) {
				goEvaluationSubmit();

			}
		});
	}
	
	void goEvaluationSubmit(){
		
	}
}
