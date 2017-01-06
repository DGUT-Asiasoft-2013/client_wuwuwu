package com.example.palmcampusmarket_client;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
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
    private CheckBox rem_pw, auto_login;      
    private SharedPreferences sp;

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
		sp = this.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);
		rem_pw = (CheckBox) findViewById(R.id.remember_password);  
        auto_login = (CheckBox) findViewById(R.id.autologin);
        if(sp.getBoolean("ISCHECK", false))  
        {  
          //设置默认是记录密码状态  
          rem_pw.setChecked(true);  
          fragAccount.setEditText(sp.getString("USER_NAME", ""));  
          fragPassword.setEditText(sp.getString("PASSWORD", ""));  
          //判断自动登陆多选框状态  
          if(sp.getBoolean("AUTO_ISCHECK", false))  
          {  
                 //设置默认是自动登录状态  
                 auto_login.setChecked(true);  
                //跳转界面  
                Intent intent = new Intent(LoginActivity.this,HomePageActivity.class);  
                LoginActivity.this.startActivity(intent);  
                  
          }  
        }
        rem_pw.setOnCheckedChangeListener(new OnCheckedChangeListener() {  
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {  
                if (rem_pw.isChecked()) {  
                      
                    System.out.println("记住密码已选中");  
                    sp.edit().putBoolean("ISCHECK", true).commit();  
                      
                }else {  
                      
                    System.out.println("记住密码没有选中");  
                    sp.edit().putBoolean("ISCHECK", false).commit();  
                      
                }  
  
            }  
        });
        auto_login.setOnCheckedChangeListener(new OnCheckedChangeListener() {  
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {  
                if (auto_login.isChecked()) {  
                    System.out.println("自动登录已选中");  
                    sp.edit().putBoolean("AUTO_ISCHECK", true).commit();  
  
                } else {  
                    System.out.println("自动登录没有选中");  
                    sp.edit().putBoolean("AUTO_ISCHECK", false).commit();  
                }  
            }  
        });
	}
	@Override
	protected void onResume() {

		super.onResume();


		fragAccount.setHintText("请输入账户名");                    

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



	    final String account = fragAccount.getText();       //获取用户输入的账号
		final String password = fragPassword.getText();


		String password5 = MD5.getMD5(fragPassword.getText());                   //密码变成哈希数

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
							//登录成功和记住密码框为选中状态才保存用户信息  
		                    if(rem_pw.isChecked())  
		                    {  
		                     //记住用户名、密码、  
		                      Editor editor = sp.edit();  
		                      editor.putString("USER_NAME",account);  
		                      editor.putString("PASSWORD",password);  
		                      editor.commit();  
		                    }  
		                    //跳转界面  
							startHomePageActivity();
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
	void startHomePageActivity(){
		Intent itnt = new Intent(LoginActivity.this,HomePageActivity.class);
		startActivity(itnt);
	}

}
