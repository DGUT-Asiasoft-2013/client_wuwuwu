package com.example.palmcampusmarket_client;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;


import com.example.palmcampusmarket_client.LoginActivity;
import com.example.palmcampusmarket_client.MD5;
import com.example.palmcampusmarket_client.R;
import com.example.palmcampusmarket_client.api.Server;
import com.example.palmcampusmarket_client.api.entity.User;
import com.example.palmcampusmarket_client.fragment.inputcell.SimpleTextInputCellFragment2;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends Activity {
	SimpleTextInputCellFragment2 fragPassword,fragAccount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);


		findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				goRegister();
			}
		});

		findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				goLogin();
			}
		});

		findViewById(R.id.btn_forgot_password).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				goRecoverPassword();
			}
		});

		fragAccount=(SimpleTextInputCellFragment2)getFragmentManager().findFragmentById(R.id.input_account);
		fragPassword=(SimpleTextInputCellFragment2)getFragmentManager().findFragmentById(R.id.input_password); 
	}
	@Override
	protected void onResume() {

		super.onResume();

		
		fragAccount.setHintText("«Î ‰»Î’Àªß√˚");                    
	
		fragPassword.setHintText("«Î ‰»Î√‹¬Î");
		fragPassword.setIsPassword(true);
	}



	void goRegister(){
		Intent itnt = new Intent(this,RegisterActivity.class);
		startActivity(itnt);
	}

	void goRecoverPassword(){
		Intent itnt = new Intent(this, PasswordRecoverActivity.class);
		startActivity(itnt);
	}

	void goLogin(){

		


		String account = fragAccount.getText();       //ªÒ»°”√ªß ‰»Îµƒ’À∫≈
		String password = fragPassword.getText();


		password = MD5.getMD5(password);                   //√‹¬Î±‰≥…π˛œ£ ˝

		OkHttpClient client=Server.getSharedClient();

		MultipartBody requestBody = new MultipartBody.Builder()
				.addFormDataPart("account", fragAccount.getText())
				.addFormDataPart("passwordHash", MD5.getMD5(fragPassword.getText()))
				.build();

		Request request=Server.requestBuilderWithApi("login")
				.method("post", null)
				.post(requestBody)
				.build();

		final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
		progressDialog.setMessage("«Î…‘∫Ú");
		progressDialog.setCancelable(true);
		progressDialog.setCanceledOnTouchOutside(true);
		progressDialog.show();

		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(final Call arg0,final Response arg1) throws IOException {

				try{
					final String responseString = arg1.body().string();
					ObjectMapper mapper = new ObjectMapper();
					final User user = mapper.readValue(responseString, User.class);

					runOnUiThread(new Runnable() {

					@Override
					public void run() {
						progressDialog.dismiss();
						startHomePageActivity();
						}
					});	
				}catch(final Exception e){
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							progressDialog.dismiss();
							new AlertDialog.Builder(LoginActivity.this)
							.setTitle("µ«¬º ß∞‹")
							.setMessage(e.getMessage())
							.show();
						}
					});
					
				}
			}

			@Override
			public void onFailure(final Call arg0, final IOException arg1) {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						progressDialog.dismiss();
						LoginActivity.this.onFailure(arg0, arg1);

					}
				});

			}
		});




	}
	void onFailure(Call arg0, Exception arg1) {
		new AlertDialog.Builder(this)
		.setTitle("µ«¬º ß∞‹")
		.setMessage(arg1.getLocalizedMessage())
		.setNegativeButton("∫√", null)
		.show();

	}	
	void startHomePageActivity(){
		Intent itnt = new Intent(LoginActivity.this,HomePageActivity.class);
		startActivity(itnt);
	}

}
