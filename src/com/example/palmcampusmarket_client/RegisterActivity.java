package com.example.palmcampusmarket_client;


import com.example.palmcampusmarket_client.fragment.inputcell.SimpleTextInputCellFragment;

import java.io.IOException;

import com.example.palmcampusmarket_client.MD5;
import com.example.palmcampusmarket_client.RegisterActivity;
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
		fragInputAvatar = (PictureInputCellFragment) getFragmentManager().findFragmentById(R.id.input_avatar);
		
		findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				submit();
			}
		});
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		fragInputCellAccount.setLabelText("�˻���");{
			fragInputCellAccount.setHintText("�������˻���");
		}

		fragInputTelephone.setLabelText("�ֻ�����");{
			fragInputTelephone.setHintText("�������ֻ�����");
		}

		fragInputNickname.setLabelText("�ǳ�");{
			fragInputNickname.setHintText("�������ǳ�");
		}

		fragInputCellPassword.setLabelText("����");{
			fragInputCellPassword.setHintText("����������");
			fragInputCellPassword.setIsPassword(true);
		}

		fragInputCellPasswordRepeat.setLabelText("�ظ�����");{
			fragInputCellPasswordRepeat.setHintText("���ظ���������");
			fragInputCellPasswordRepeat.setIsPassword(true);
		}



	}
	void submit(){
		String password = fragInputCellPassword.getText();
		String passwordRepeat = fragInputCellPasswordRepeat.getText();

		if(!password.equals(passwordRepeat)){

			new AlertDialog.Builder(RegisterActivity.this)
			.setMessage("�ظ����벻һ��")
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setNegativeButton("��", null)
			.show();

			return;
		}

		password = MD5.getMD5(password);


		String account =fragInputCellAccount.getText();
		String nickname = fragInputNickname.getText();
		String telephone = fragInputTelephone.getText();

		OkHttpClient client=new OkHttpClient();

		MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder()
				.setType(MultipartBody.FORM)
				.addFormDataPart("account", account)
				.addFormDataPart("nickname", nickname)
				.addFormDataPart("telephone", telephone)
				.addFormDataPart("passwordHash", password);

		if(fragInputAvatar.getPngData()!=null){
			requestBodyBuilder
			.addFormDataPart(
					"avatar", 
					"avatar",
					RequestBody
					.create(MediaType.parse("image/png"), 
							fragInputAvatar.getPngData()));
		}


		Request request=new Request.Builder()
				.url("http://172.27.0.42:8080/membercenter/api/register")
				.method("post", null)
				.post(requestBodyBuilder.build())
				.build();

		final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
		progressDialog.setMessage("���Ժ�");
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
		.setTitle("ע��ɹ�")
		.setMessage(responseBody)
		.setPositiveButton("��", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();

			}
		})
		.show();

	}

	void onFailure(Call arg0, Exception arg1) {
		new AlertDialog.Builder(this)
		.setTitle("����ʧ��")
		.setMessage(arg1.getLocalizedMessage())
		.setNegativeButton("��", null)
		.show();

	}





}
