package com.example.palmcampusmarket_client;

import java.io.IOException;

import com.example.palmcampusmarket_client.fragment.inputcells.PictureInputCellFragment;
import com.example.palmcampusmarket_client.fragment.inputcells.SimpleTextInputCellFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
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

public class NewCommodityActivity extends Activity {
	
	SimpleTextInputCellFragment fragInputCellName;   
	SimpleTextInputCellFragment fragInputCellPrice;  
	SimpleTextInputCellFragment fragInPutCellNumber;
	SimpleTextInputCellFragment fragInputCellDescrible;
	PictureInputCellFragment fragInputImage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.fragment_page_addcommodity);
		fragInputCellName = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.comm_name);
		fragInputCellPrice = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.comm_price);
		fragInPutCellNumber = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.comm_number);
		fragInputCellDescrible = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.comm_describle);
		fragInputImage = (PictureInputCellFragment) getFragmentManager().findFragmentById(R.id.comm_image);
		
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
		
		OkHttpClient client = new OkHttpClient();
		MultipartBody.Builder requestBulderBody = new MultipartBody.Builder()
				.setType(MultipartBody.FORM)
				.addFormDataPart("name", name)
				.addFormDataPart("price", price)
				.addFormDataPart("number", number)
				.addFormDataPart("describle", describle);
		
		if(fragInputImage.getPngData()!=null){
			requestBulderBody
			.addFormDataPart(
					"image", 
					"image",
					RequestBody
					.create(MediaType.parse("image/png"),
							fragInputImage.getPngData()));
		}
		
		Request request = new Request.Builder()
				.url("http://172.27.0.33:8080/membercenter/api/NewCommodity")
					.method("post", null)
					.post(requestBulderBody.build())
					.build();
		
		
		final ProgressDialog progressDialog = new ProgressDialog(NewCommodityActivity.this);
		progressDialog.setMessage("请稍后");
		progressDialog.setCancelable(false);
		progressDialog.setCanceledOnTouchOutside(false);
		
		client.newCall(request).enqueue(new Callback() {
			
			@Override
			public void onResponse(final Call arg0, final Response arg1) throws IOException {
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						progressDialog.dismiss();
						
						try{
							NewCommodityActivity.this.onResponse(arg0,arg1.body().string());
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
		.setMessage(responseBody)
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
		.setMessage(arg1.getLocalizedMessage())
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
