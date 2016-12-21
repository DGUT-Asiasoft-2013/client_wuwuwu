package com.example.palmcampusmarket_client;

import com.example.palmcampusmarket_client.fragment.inputcell.CommodityPictureInputCellFragment;
import com.example.palmcampusmarket_client.fragment.inputcell.CommoditySimpleTextInputCellFragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.View;

public class NewCommodityActivity extends Activity {
	
	CommoditySimpleTextInputCellFragment fragInputCellName;   
	CommoditySimpleTextInputCellFragment fragInputCellPrice;  
	CommoditySimpleTextInputCellFragment fragInPutCellNumber;
	CommoditySimpleTextInputCellFragment fragInputCellDescrible;
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
		
		fragInputCellName.setLabelText("��Ʒ��");{
			fragInputCellName.setHintText("��������Ʒ��");
		}
		fragInputCellPrice.setLabelText("��Ʒ�۸�");{
			fragInputCellPrice.setHintText("��������Ʒ�۸�");
		}
		
		fragInPutCellNumber.setLabelText("��Ʒ����");{
			fragInPutCellNumber.setHintText("��������Ʒ����");
		}
		
		fragInputCellDescrible.setLabelText("��Ʒ����");{
			fragInputCellDescrible.setHintText("��������Ʒ����");
		}
	}

}
