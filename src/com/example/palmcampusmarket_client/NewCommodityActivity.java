package com.example.palmcampusmarket_client;

import com.example.palmcampusmarket_client.fragment.inputcells.PictureInputCellFragment;
import com.example.palmcampusmarket_client.fragment.inputcells.SimpleTextInputCellFragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.View;

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
