package com.example.palmcampusmarket_client.setting;

import java.io.IOException;
import java.util.List;

import com.example.palmcampusmarket_client.PasswordRecoverActivity;
import com.example.palmcampusmarket_client.R;
import com.example.palmcampusmarket_client.api.Server;
import com.example.palmcampusmarket_client.api.entity.Address;
import com.example.palmcampusmarket_client.fragment.inputcell.CommoditySimpleTextInputCellFragment;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.Response;

public class AddressContentActivity extends Activity {
	Address address;
	List<Address> data;
	EditText etName,etTelephone,etAddress;
	Button btn_address_save;
	CheckBox cbSetDefault;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_address_content);

		address = (Address) getIntent().getSerializableExtra("address");

		etName = (EditText) findViewById(R.id.address_content_name);
		etTelephone = (EditText) findViewById(R.id.address_content_telephone);
		etAddress = (EditText) findViewById(R.id.address_content_address);

		etName.setText(address.getAddress_name());
		etTelephone.setText(address.getAddress_telephone());
		etAddress.setText(address.getAddress());

		findViewById(R.id.address_content_back).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});

		findViewById(R.id.address_default).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				setDefault();

			}
		});

		//		cbSetDefault.setChecked(address.isDefaultInfo());
		//		if(cbSetDefault.isChecked()){
		//			cbSetDefault.setText("默认地址");
		//		}else{
		//			cbSetDefault.setText("设为默认");
		//		}

		findViewById(R.id.address_delete).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				goDelete();

			}
		});

		findViewById(R.id.btn_address_save).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				onSave();

			}
		});
	}

	void setDefault(){
		Request request = Server.requestBuilderWithApi("/address/setdefault/" + address.getId()).get().build();
		Server.getSharedClient().newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						getAddress();

					}
				});

			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				// TODO Auto-generated method stub

			}
		});
	}

	void goDelete(){
		new AlertDialog.Builder(AddressContentActivity.this).setMessage("确认删除地址？")
		.setPositiveButton("确认", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				onDelete();

			}
		})
		.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();

			}
		}).show();
	}

	void onDelete(){
		Request request = Server.requestBuilderWithApi("/address/delete/" + address.getId()).get().build();
		Server.getSharedClient().newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						AddressContentActivity.this.onSave();;


					}
				});

			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				// TODO Auto-generated method stub

			}
		});
	}

	void getAddress(){
		Intent intent = new Intent(this, AddressListActivity.class);
		startActivity(intent);
	}

	void onSave(){
		String name = etName.getText().toString();
		String telephone = etTelephone.getText().toString();
		String newaddress = etAddress.getText().toString();

		MultipartBody body = new MultipartBody.Builder()
				.addFormDataPart("name", name)
				.addFormDataPart("telephone", telephone)
				.addFormDataPart("address", newaddress)
				.build();

		Request request = Server.requestBuilderWithApi("/address/change/" + address.getId()).post(body).build();

		final ProgressDialog progressDialog = new ProgressDialog(AddressContentActivity.this);
		progressDialog.setMessage("请稍候");
		progressDialog.setCancelable(false);
		progressDialog.setCanceledOnTouchOutside(false);

		Server.getSharedClient().newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(final Call arg0, final Response arg1) throws IOException {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						progressDialog.dismiss();

						try{
							AddressContentActivity.this.onResponse(arg0, arg1.body().string());
						}catch (Exception e) {
							e.printStackTrace();
							AddressContentActivity.this.onFailure(arg0, e);
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
						AddressContentActivity.this.onFailure(arg0, arg1);

					}
				});

			}
		});

	}

	void onResponse(Call arg0, String responseBody) {
		new AlertDialog.Builder(this)
		.setTitle("修改成功")
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
		.setTitle("修改失败")
		.setMessage(arg1.getLocalizedMessage())
		.setNegativeButton("好", null)
		.show();

	}





}
