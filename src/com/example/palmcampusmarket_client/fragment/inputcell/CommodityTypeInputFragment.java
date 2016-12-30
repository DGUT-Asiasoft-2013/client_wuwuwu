package com.example.palmcampusmarket_client.fragment.inputcell;

import com.example.palmcampusmarket_client.R;

import android.app.Activity;
import android.app.Fragment;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;


//修改：类型


public class CommodityTypeInputFragment extends Fragment {
	
	TextView commType;
	String typeItem;
	
	
	public String getTypeItem() {
		return typeItem;
	}

	public void setTypeItem(String typeItem) {
		this.typeItem = typeItem;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_input_type, container);
	//	label_type = (TextView) view.findViewById(R.id.label_type);
//		commodityType = (Spinner) view.findViewById(R.id.type);
		
//		commodityType.setOnItemClickListener(new Spinner.OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				String[] array = getResources().getStringArray(R.array.commoditytype);
//				typeNumber = getResources().getStringArray(R.array.commoditytype)[position];
//				
//				//设置显示当前的项
//				parent.setVisibility(View.VISIBLE);
//			}
//		});
		 final Spinner sp = (Spinner) view.findViewById(R.id.type);
		
		sp.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				
				commType = (TextView) view.findViewById(R.id.label_type);
				//获取Spinner控件的适配器
//				ArrayAdapter<String> adapter = (ArrayAdapter<String>)parent.getAdapter();
//				commType.setText(adapter.getItem(position));
				String item =(String) sp.getItemAtPosition(position);
				setTypeItem(item);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
		
		return view;
	}
//	
//	public String getText(){
//		return sp.getSelectedItem().toString();
//	}	
}
