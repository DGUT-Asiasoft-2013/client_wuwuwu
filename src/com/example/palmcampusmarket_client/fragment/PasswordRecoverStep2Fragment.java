package com.example.palmcampusmarket_client.fragment;



import com.example.palmcampusmarket_client.R;
import com.example.palmcampusmarket_client.fragment.inputcell.SimpleTextInputCellFragment3;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class PasswordRecoverStep2Fragment extends Fragment {
	View view;
	SimpleTextInputCellFragment3 fragPassword;
	SimpleTextInputCellFragment3 fragPasswordRepeat;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		if(view==null){
			view = inflater.inflate(R.layout.fragment_password_recover_step2,null);
			fragPassword=(SimpleTextInputCellFragment3)getFragmentManager().findFragmentById(R.id.input_password);
			fragPasswordRepeat=(SimpleTextInputCellFragment3)getFragmentManager().findFragmentById(R.id.input_password_repeat);
		}

		view.findViewById(R.id.password_recover_setp2_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getFragmentManager().popBackStack();
			}
		});

		view.findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onSubmitClicked();
			}
		});

		return view;


	}

	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();



		fragPassword.setScr(R.drawable.password1);{
			fragPassword.setHintText("请输入密码");
			fragPassword.setIsPassword(true);
		}


		fragPasswordRepeat.setScr(R.drawable.lock);{
			fragPasswordRepeat.setHintText("请重新输入密码");

			fragPasswordRepeat.setIsPassword(true);
		}

	}

	public String getText(){
		return fragPassword.getText().toString();
	}

	public static interface OnSubmitClickedListener{
		void onSubmitClicked();
	}

	OnSubmitClickedListener onSubmitClickedListener;

	public void setOnSubmitClickedListener(OnSubmitClickedListener onSubmitClickedListener) {
		this.onSubmitClickedListener = onSubmitClickedListener;
	}

	void onSubmitClicked(){
		if(fragPassword.getText().equals(fragPasswordRepeat.getText())){
			if(onSubmitClickedListener!=null){
				onSubmitClickedListener.onSubmitClicked();
			}
		}else{
			new AlertDialog.Builder(getActivity())

			.setMessage("密码不一致")

			.show();
		}
	}
}

