package com.example.palmcampusmarket_client.collect;

import com.example.palmcampusmarket_client.R;
import com.example.palmcampusmarket_client.fragment.inputcell.CommoditySimpleTextInputCellFragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class NewNeedActivity extends Activity {

	CommoditySimpleTextInputCellFragment fragInputCellName;
	EditText needContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.fragment_pag_addneeds);

		fragInputCellName = (CommoditySimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.need_name);
		fragInputCellName.setLabelText("商品名");{
			fragInputCellName.setHintText("请输入商品名");
		}

		needContent = (EditText) findViewById(R.id.need_content);

		findViewById(R.id.btn_send_need).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				submit();
				
			}
		});
		

	}
	
	void submit(){
		
	}

}
