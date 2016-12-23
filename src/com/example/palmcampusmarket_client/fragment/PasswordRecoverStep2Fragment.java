package com.example.palmcampusmarket_client.fragment;



import com.example.palmcampusmarket_client.R;
import com.example.palmcampusmarket_client.fragment.inputcell.SimpleTextInputCellFragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PasswordRecoverStep2Fragment extends Fragment {
	View view;
	SimpleTextInputCellFragment fragPassword;
	SimpleTextInputCellFragment fragPasswordRepeat;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		if(view==null){
			view = inflater.inflate(R.layout.fragment_password_recover_step2,null);
			fragPassword=(SimpleTextInputCellFragment)getFragmentManager().findFragmentById(R.id.input_password);
			fragPasswordRepeat=(SimpleTextInputCellFragment)getFragmentManager().findFragmentById(R.id.input_password_repeat);
		}

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



		fragPassword.setLabelText("密码");{
			fragPassword.setHintText("请输入密码");
			fragPassword.setIsPassword(true);
		}


		fragPasswordRepeat.setLabelText("确认密码");{
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

