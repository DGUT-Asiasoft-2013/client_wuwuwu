package com.example.palmcampusmarket_client;

import java.io.IOException;



import com.example.palmcampusmarket_client.api.Server;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BootActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_boot);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		OkHttpClient client=Server.getSharedClient();
		Request request=Server.requestBuilderWithApi("hello")
				.method("GET", null)
				.build();

		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, final Response arg1) throws IOException {
				//				Log.d("response", arg1.toString());
				BootActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						try{
							Toast.makeText(BootActivity.this, arg1.body().string(), Toast.LENGTH_SHORT).show();
						}catch (Exception e) {
							e.printStackTrace();
						}
						startLoginActivity();
					}
				});
			}

			@Override
			public void onFailure(Call arg0, final IOException arg1) {
				BootActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {

						Toast.makeText(BootActivity.this, arg1.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

					}
				});

			}
		});

	}

	void startLoginActivity(){
		Intent itnt = new Intent(this, LoginActivity.class);
		startActivity(itnt);
		finish();
	}
}
