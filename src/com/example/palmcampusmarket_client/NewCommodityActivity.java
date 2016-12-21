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
