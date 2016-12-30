package com.example.palmcampusmarket_client;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class AddCommodityType extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_page_addcommodity);
		List<String> list =new ArrayList<String>();
		list.add("书");
		list.add("车");
		list.add("电子");
		list.add("生活用品");
		list.add("食品");
		list.add("其他");

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item,list);
		adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
		Spinner sp = (Spinner) findViewById(R.id.type);
		sp.setAdapter(adapter);
		sp.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override

			// parent： 为控件Spinner   view：显示文字的TextView   position：下拉选项的位置从0开始
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


				TextView commType = (TextView) findViewById(R.id.comm_type);
				//获取Spinner控件的适配器
				ArrayAdapter<String> adapter = (ArrayAdapter<String>)parent.getAdapter();
				commType.setText(adapter.getItem(position));

			}


			//没有选中时的处理
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
	}
}
