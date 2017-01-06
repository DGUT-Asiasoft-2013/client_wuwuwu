package com.example.palmcampusmarket_client;


import com.example.palmcampusmarket_client.fragment.inputcell.SimpleTextInputCellFragment3;

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
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends Activity {
	SimpleTextInputCellFragment3 fragInputCellAccount;
	SimpleTextInputCellFragment3 fragInputTelephone;
	SimpleTextInputCellFragment3 fragInputNickname;
	SimpleTextInputCellFragment3 fragInputCellPassword;
	SimpleTextInputCellFragment3 fragInputCellPasswordRepeat;
	SimpleTextInputCellFragment3 fragInputCellAddress;
	PictureInputCellFragment fragInputAvatar;
	ImageButton btn_back;
	int money = 0;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);


		setContentView(R.layout.activity_register);
		fragInputCellAccount = (SimpleTextInputCellFragment3) getFragmentManager().findFragmentById(R.id.input_account);
		fragInputTelephone = (SimpleTextInputCellFragment3) getFragmentManager().findFragmentById(R.id.input_telephone);
		fragInputNickname = (SimpleTextInputCellFragment3) getFragmentManager().findFragmentById(R.id.input_nickname);
		fragInputCellPassword = (SimpleTextInputCellFragment3) getFragmentManager().findFragmentById(R.id.input_password);
		fragInputCellPasswordRepeat = (SimpleTextInputCellFragment3) getFragmentManager().findFragmentById(R.id.input_password_repeat);
		fragInputCellAddress = (SimpleTextInputCellFragment3) getFragmentManager().findFragmentById(R.id.input_address);
		fragInputAvatar = (PictureInputCellFragment) getFragmentManager().findFragmentById(R.id.input_avatar);
		btn_back = (ImageButton)findViewById(R.id.register_back);
		btn_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});



	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		fragInputCellAccount.setScr(R.drawable.user);
		{
			fragInputCellAccount.setHintText("请输入账户名");
		}

		fragInputTelephone.setScr(R.drawable.phone);
		{
			fragInputTelephone.setHintText("请输入手机号码");
		}

		fragInputNickname.setScr(R.drawable.name);
		{
			fragInputNickname.setHintText("请输入昵称");
		}

		fragInputCellPassword.setScr(R.drawable.password1);
		{
			fragInputCellPassword.setHintText("请输入密码");
			fragInputCellPassword.setIsPassword(true);
		}

		fragInputCellPasswordRepeat.setScr(R.drawable.lock);
		{
			fragInputCellPasswordRepeat.setHintText("请重复输入密码");
			fragInputCellPasswordRepeat.setIsPassword(true);
		}

		fragInputCellAddress.setScr(R.drawable.adress);
		{
			fragInputCellAddress.setHintText("请输入收货地址");
		}

		findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				submit();
			}
		});


	}

	void submit() {
		String password = fragInputCellPassword.getText();
		String passwordRepeat = fragInputCellPasswordRepeat.getText();

		if (!password.equals(passwordRepeat)) {

			new AlertDialog.Builder(RegisterActivity.this)
			.setMessage("两次密码不一致")
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setNegativeButton("好", null)
			.show();

			return;
		}

		password = MD5.getMD5(password);


		String account = fragInputCellAccount.getText();
		String nickname = fragInputNickname.getText();
		String telephone = fragInputTelephone.getText();
		String address = fragInputCellAddress.getText();

		OkHttpClient client = Server.getSharedClient();

		MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder()
				.setType(MultipartBody.FORM)
				.addFormDataPart("account", account)
				.addFormDataPart("nickname", nickname)
				.addFormDataPart("telephone", telephone)
				.addFormDataPart("passwordHash", password)
				.addFormDataPart("address", address)
				.addFormDataPart("money", String.valueOf(money));

		if (fragInputAvatar.getPngData() != null) {
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
			public void onResponse(final Call arg0, final Response arg1) throws IOException {
				final String s = arg1.body().string();
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						progressDialog.dismiss();

						try {
							RegisterActivity.this.onResponse(arg0, s);
						} catch (Exception e) {
							e.printStackTrace();
							RegisterActivity.this.onFailure(arg0, e);
							Toast.makeText(getBaseContext(), s, Toast.LENGTH_LONG).show();
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
		//.setMessage(responseBody)
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
		.setTitle("注册失败")
		.setMessage(arg1.getLocalizedMessage())
		.setNegativeButton("好", null)
		.show();

	}

}
