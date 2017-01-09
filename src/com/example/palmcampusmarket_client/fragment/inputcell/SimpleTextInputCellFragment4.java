package com.example.palmcampusmarket_client.fragment.inputcell;
import com.example.palmcampusmarket_client.R;
import com.example.palmcampusmarket_client.R.id;
import com.example.palmcampusmarket_client.R.layout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class SimpleTextInputCellFragment4 extends BaseInputCellFragment {

	TextView label;
	EditText edit;
	ImageButton imageButton;
	View view;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_inputcell_simpletext4, container);
		label = (TextView) view.findViewById(R.id.label);
		edit = (EditText) view.findViewById(R.id.edit);
		imageButton = (ImageButton)view.findViewById(R.id.showpassword);
		imageButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				edit.setTransformationMethod(null);
			}
		});
		return view;
	}

	public void setLabelText(String labelText) {
		label.setText(labelText);
	}

	public void setHintText(String hintText) {
		edit.setHint(hintText);
	}
	public void setEditText(String edittext){
		edit.setText(edittext);
	}

	public void setIsPassword(boolean isPassword) {
		// TODO Auto-generated method stub

		if(isPassword){
			edit.setInputType(EditorInfo.TYPE_CLASS_TEXT|EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
		}else{
			edit.setInputType(EditorInfo.TYPE_CLASS_TEXT);
		}
	}

	public String getText() {
		return edit.getText().toString();
	}
}