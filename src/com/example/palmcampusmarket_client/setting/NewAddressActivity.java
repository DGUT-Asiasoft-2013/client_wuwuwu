package com.example.palmcampusmarket_client.setting;

import java.io.IOException;

import com.example.palmcampusmarket_client.NewCommodityActivity;
import com.example.palmcampusmarket_client.R;
import com.example.palmcampusmarket_client.api.Server;
import com.example.palmcampusmarket_client.fragment.inputcell.CommodityBaseInputCellFragment;
import com.example.palmcampusmarket_client.fragment.inputcell.CommoditySimpleTextInputCellFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewAddressActivity extends Activity {
	CommoditySimpleTextInputCellFragment fragInputCellName;
	CommoditySimpleTextInputCellFragment fragInputCellTelephone;
	CommoditySimpleTextInputCellFragment fragInputCellAddress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_inputcell_address);

		fragInputCellName = (CommoditySimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.address_input_name);
		fragInputCellTelephone = (CommoditySimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.address_input_telephone);
		fragInputCellAddress = (CommoditySimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.address_input_add);

		findViewById(R.id.btn_determine).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				add();

			}
		});


	}

	void add(){
		String name = fragInputCellName.getText();
		String telephone = fragInputCellTelephone.getText();
		String address = fragInputCellAddress.getText();

		OkHttpClient client = Server.getSharedClient();
		MultipartBody.Builder requestBulderBody = new MultipartBody.Builder()
				.addFormDataPart("AddName", name)
				.addFormDataPart("AddTelephone", telephone)
				.addFormDataPart("AddAddress", address);

		Request request = Server.requestBuilderWithApi("address")
				.post(requestBulderBody.build())
				.build();

		final ProgressDialog progressDialog = new ProgressDialog(NewAddressActivity.this);
		progressDialog.setMessage("请稍后");
		progressDialog.setCancelable(false);
		progressDialog.setCanceledOnTouchOutside(false);

		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(final Call arg0,final Response arg1) throws IOException {
				final String responseString = arg1.body().string();
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						progressDialog.dismiss();

						try{
							NewAddressActivity.this.onResponse(arg0,responseString);
						}catch (Exception e) {
							e.printStackTrace();
							NewAddressActivity.this.onFailure(arg0,e);
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
						onFailure(arg0, arg1);

					}
				});

			}
		});
	}

	void onResponse(Call arg0,String responseBody){
		new AlertDialog.Builder(this)
		.setTitle("添加成功")
		.setPositiveButton("好", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();

			}
		}).show();
	}

	void onFailure(Call agr0,Exception arg1){
		new AlertDialog.Builder(this)
		.setTitle("上架失败")
		.setNegativeButton("好", null)
		.show();
	}

	@Override
	protected void onResume() {

		super.onResume();

		fragInputCellName.setLabelText("收货人");{
			fragInputCellName.setHintText("请输入收货人姓名");
		}
		fragInputCellTelephone.setLabelText("手机号");{
			fragInputCellTelephone.setHintText("请输入手机号");
		}
		fragInputCellAddress.setLabelText("地址");{
			fragInputCellAddress.setHintText("请输入地址");
		}
	}

}
