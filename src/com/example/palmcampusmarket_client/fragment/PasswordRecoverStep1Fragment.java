package com.example.palmcampusmarket_client.fragment;

import com.example.palmcampusmarket_client.R;
import com.example.palmcampusmarket_client.fragment.inputcell.SimpleTextInputCellFragment3;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class PasswordRecoverStep1Fragment extends Fragment {
	SimpleTextInputCellFragment3 fragTelephone; 
	View view;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(view==null){
			view = inflater.inflate(R.layout.fragment_password_recover_step1, null);

			fragTelephone = (SimpleTextInputCellFragment3) getFragmentManager().findFragmentById(R.id.input_telephone);
			view.findViewById(R.id.password_recover_setp1_back).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					getActivity().finish();
					
				}
			});

			view.findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					goNext();
				}
			});
		}

		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();


		fragTelephone.setScr(R.drawable.phone);
		fragTelephone.setHintText("输入手机号码");

	}

	public static interface OnGoNextListener{
		void onGoNext();
	}

	OnGoNextListener onGoNextListener;

	public void setOnGoNextListener(OnGoNextListener onGoNextListener) {
		this.onGoNextListener = onGoNextListener;
	}

	void goNext(){
		if(onGoNextListener!=null){
			onGoNextListener.onGoNext();
		}
	}

	public String getText() {
		// TODO Auto-generated method stub
		return fragTelephone.getText().toString();

	}
}
