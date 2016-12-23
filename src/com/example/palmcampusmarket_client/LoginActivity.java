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
import com.example.palmcampusmarket_client.collect.SearchActivity;
import com.example.palmcampusmarket_client.fragment.inputcell.SimpleTextInputCellFragment;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends Activity {
	SimpleTextInputCellFragment fragPassword,fragAccount;

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

		fragAccount=(SimpleTextInputCellFragment)getFragmentManager().findFragmentById(R.id.input_account);
		fragPassword=(SimpleTextInputCellFragment)getFragmentManager().findFragmentById(R.id.input_password); 
	}
	@Override
	protected void onResume() {

		super.onResume();

		fragAccount.setLabelText("账户名");
		fragAccount.setHintText("请输入账户名");                    
		fragPassword.setLabelText("密码");
		fragPassword.setHintText("请输入密码");
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

		


		String account = fragAccount.getText();       //获取用户输入的账号
		String password = fragPassword.getText();


		password = MD5.getMD5(password);                   //密码变成哈希数

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
		progressDialog.setMessage("请稍候");
		progressDialog.setCancelable(false);
		progressDialog.setCanceledOnTouchOutside(false);
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

							new AlertDialog.Builder(LoginActivity.this)
							.setTitle("登录成功")
							.setMessage("Hello,"+user.getNickname())
							.setPositiveButton("好", new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
									Intent itnt = new Intent(LoginActivity.this,SearchActivity.class);
									startActivity(itnt);
								}
							})
							.show();
						}
					});	
				}catch(final Exception e){
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							progressDialog.dismiss();

							new AlertDialog.Builder(LoginActivity.this)
							.setTitle("登录失败")
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
		.setTitle("登录失败")
		.setMessage(arg1.getLocalizedMessage())
		.setNegativeButton("好", null)
		.show();

	}

}
