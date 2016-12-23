package com.example.palmcampusmarket_client;


import com.example.palmcampusmarket_client.fragment.inputcell.SimpleTextInputCellFragment;

import java.io.IOException;

import com.example.palmcampusmarket_client.MD5;
import com.example.palmcampusmarket_client.RegisterActivity;
import com.example.palmcampusmarket_client.api.Server;
import com.example.palmcampusmarket_client.R;
import com.example.palmcampusmarket_client.fragment.inputcell.PictureInputCellFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends Activity {
	SimpleTextInputCellFragment fragInputCellAccount;
	SimpleTextInputCellFragment fragInputTelephone;
	SimpleTextInputCellFragment fragInputNickname;
	SimpleTextInputCellFragment fragInputCellPassword;
	SimpleTextInputCellFragment fragInputCellPasswordRepeat;
	SimpleTextInputCellFragment fragInputCellAddress;
	PictureInputCellFragment fragInputAvatar;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_register);
		fragInputCellAccount = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.input_account);
		fragInputTelephone = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.input_telephone);
		fragInputNickname = (SimpleTextInputCellFragment)getFragmentManager().findFragmentById(R.id.input_nickname);
		fragInputCellPassword = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.input_password);
		fragInputCellPasswordRepeat = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.input_password_repeat);
		fragInputCellAddress=(SimpleTextInputCellFragment)getFragmentManager().findFragmentById(R.id.input_address);
		fragInputAvatar = (PictureInputCellFragment) getFragmentManager().findFragmentById(R.id.input_avatar);



	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		fragInputCellAccount.setLabelText("账户名");{
			fragInputCellAccount.setHintText("请输入账户名");
		}

		fragInputTelephone.setLabelText("手机号码");{
			fragInputTelephone.setHintText("请输入手机号码");
		}

		fragInputNickname.setLabelText("昵称");{
			fragInputNickname.setHintText("请输入昵称");
		}

		fragInputCellPassword.setLabelText("密码");{
			fragInputCellPassword.setHintText("请输入密码");
			fragInputCellPassword.setIsPassword(true);
		}

		fragInputCellPasswordRepeat.setLabelText("重复密码");{
			fragInputCellPasswordRepeat.setHintText("请重复输入密码");
			fragInputCellPasswordRepeat.setIsPassword(true);
		}

		fragInputCellAddress.setLabelText("收货地址");{
			fragInputCellAddress.setHintText("请输入收货地址");
		}

		findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				submit();
			}
		});



	}
	void submit(){
		String password = fragInputCellPassword.getText();
		String passwordRepeat = fragInputCellPasswordRepeat.getText();

		if(!password.equals(passwordRepeat)){

			new AlertDialog.Builder(RegisterActivity.this)
			.setMessage("重复密码不一致")
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setNegativeButton("好", null)
			.show();

			return;
		}

		password = MD5.getMD5(password);


		String account =fragInputCellAccount.getText();
		String nickname = fragInputNickname.getText();
		String telephone = fragInputTelephone.getText();
		String address =fragInputCellAddress.getText();

		OkHttpClient client=Server.getSharedClient();

		MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder()
				.setType(MultipartBody.FORM)
				.addFormDataPart("account", account)
				.addFormDataPart("nickname", nickname)
				.addFormDataPart("telephone", telephone)
				.addFormDataPart("passwordHash", password)
				.addFormDataPart("address", address);

		if(fragInputAvatar.getPngData()!=null){
			requestBodyBuilder
			.addFormDataPart(
					"avatar", 
					"avatar",
					RequestBody
					.create(MediaType.parse("image/png"), 
							fragInputAvatar.getPngData()));
		}



		Request request=Server.requestBuilderWithApi("register")   //修改了链接

				.method("post", null)
				.post(requestBodyBuilder.build())
				.build();

		final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
		progressDialog.setMessage("请稍候");
		progressDialog.setCancelable(false);
		progressDialog.setCanceledOnTouchOutside(false);

		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(final Call arg0,final Response arg1) throws IOException {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						progressDialog.dismiss();

						try{
							RegisterActivity.this.onResponse(arg0, arg1.body().string());
						}catch (Exception e) {
							e.printStackTrace();
							RegisterActivity.this.onFailure(arg0, e);
						}

					}
				});

			}

			@Override
			public void onFailure(final Call arg0, final IOException arg1) {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						progressDialog.dismiss();

						RegisterActivity.this.onFailure(arg0, arg1);

					}
				});

			}
		});



	}



	void onResponse(Call arg0, String responseBody) {
		new AlertDialog.Builder(this)
		.setTitle("注册成功")
		.setMessage(responseBody)
		.setPositiveButton("好", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();

			}
		})
		.show();

	}

	void onFailure(Call arg0, Exception arg1) {
		new AlertDialog.Builder(this)
		.setTitle("请求失败")
		.setMessage(arg1.getLocalizedMessage())
		.setNegativeButton("好", null)
		.show();

	}





}
