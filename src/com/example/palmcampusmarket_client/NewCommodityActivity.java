package com.example.palmcampusmarket_client;



import java.io.IOException;

import com.example.palmcampusmarket_client.api.Server;
import com.example.palmcampusmarket_client.fragment.inputcell.CommodityPictureInputCellFragment;
import com.example.palmcampusmarket_client.fragment.inputcell.CommoditySimpleTextInputCellFragment;
import com.example.palmcampusmarket_client.fragment.inputcell.CommodityTypeInputFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NewCommodityActivity extends Activity {


	CommoditySimpleTextInputCellFragment fragInputCellName;   
	CommoditySimpleTextInputCellFragment fragInputCellPrice;  
	CommoditySimpleTextInputCellFragment fragInPutCellNumber;
	CommoditySimpleTextInputCellFragment fragInputCellDescrible;
    CommodityTypeInputFragment fragmentInputCellType;                  //修改：类型




	CommodityPictureInputCellFragment fragInputImage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.fragment_page_addcommodity);

		fragInputCellName = (CommoditySimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.comm_name);
		fragInputCellPrice = (CommoditySimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.comm_price);
		fragInPutCellNumber = (CommoditySimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.comm_number);
		fragInputCellDescrible = (CommoditySimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.comm_describle);
		fragInputImage = (CommodityPictureInputCellFragment) getFragmentManager().findFragmentById(R.id.comm_image);
		fragmentInputCellType = (CommodityTypeInputFragment) getFragmentManager().findFragmentById(R.id.type);   //修改：类型



		findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				submit();

			}
		});

	}

	void submit(){
		String name = fragInputCellName.getText();
		String price = fragInputCellPrice.getText();
		String number = fragInPutCellNumber.getText();
		String describle = fragInputCellDescrible.getText();
		byte[] image = fragInputImage.getPngData();
		String type = fragmentInputCellType.getTypeItem();        //类型

		OkHttpClient client = Server.getSharedClient();
		MultipartBody.Builder requestBulderBody = new MultipartBody.Builder()
				.setType(MultipartBody.FORM)

				.addFormDataPart("CommName", name)
				.addFormDataPart("CommPrice", price)
				.addFormDataPart("CommNumber", number)
				.addFormDataPart("CommDescrible", describle)
				.addFormDataPart("CommType", type);


		if(fragInputImage.getPngData()!=null){
			requestBulderBody
			.addFormDataPart(
					"CommImage", 
					"image",
					RequestBody
					.create(MediaType.parse("image/png"),
							fragInputImage.getPngData()));
		}


		Request request = Server.requestBuilderWithApi("commodity")
				.post(requestBulderBody.build())
				.build();


		final ProgressDialog progressDialog = new ProgressDialog(NewCommodityActivity.this);
		progressDialog.setMessage("请稍后");
		progressDialog.setCancelable(false);
		progressDialog.setCanceledOnTouchOutside(false);

		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(final Call arg0, final Response arg1) throws IOException {

				final String responseString = arg1.body().string();
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						progressDialog.dismiss();

						try{
							NewCommodityActivity.this.onResponse(arg0,responseString);
						}catch(Exception e){
							e.printStackTrace();
							NewCommodityActivity.this.onFailure(arg0,e);
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
		.setTitle("上架成功")
		//		.setMessage(responseBody)
		.setPositiveButton("好",new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();

			}
		}).show();
	}

	void onFailure(Call arg0,Exception arg1){
		new AlertDialog.Builder(this)
		.setTitle("上架失败")
		//		.setMessage(arg1.getMessage())
		.setNegativeButton("好", null)
		.show();

	}

	@Override
	protected void onResume() {

		super.onResume();

		fragInputCellName.setLabelText("商品名");{
			fragInputCellName.setHintText("请输入商品名");
		}
		fragInputCellPrice.setLabelText("商品价格");{
			fragInputCellPrice.setHintText("请输入商品价格");
		}

		fragInPutCellNumber.setLabelText("商品数量");{
			fragInPutCellNumber.setHintText("请输入商品数量");
		}

		fragInputCellDescrible.setLabelText("商品描述");{
			fragInputCellDescrible.setHintText("请输入商品描述");
		}
	}

}
