package com.example.palmcampusmarket_client;


import com.example.palmcampusmarket_client.api.Server;
import com.example.palmcampusmarket_client.fragment.MainTabbarFragment;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends Activity {



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
				MainActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						try{
							Toast.makeText(MainActivity.this, arg1.body().string(), Toast.LENGTH_SHORT).show();
						}catch (Exception e) {
							e.printStackTrace();
						}
						startLoginActivity();
					}
				});
			}

			@Override
			public void onFailure(Call arg0, final IOException arg1) {
				MainActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {

						Toast.makeText(MainActivity.this, arg1.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

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
