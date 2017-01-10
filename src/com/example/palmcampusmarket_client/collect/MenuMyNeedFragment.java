package com.example.palmcampusmarket_client.collect;

import java.io.IOException;
import java.text.SimpleDateFormat;

import com.example.palmcampusmarket_client.R;
import com.example.palmcampusmarket_client.api.Server;
import com.example.palmcampusmarket_client.api.entity.Need;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MenuMyNeedFragment extends Fragment {
	View view;
	Need need;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		if(view == null){

			view = inflater.inflate(R.layout.fragment_need_content_menu, null);

			need = (Need) getActivity().getIntent().getSerializableExtra("need");

			TextView edit = (TextView) view.findViewById(R.id.edit_myneed);
			TextView finish = (TextView) view.findViewById(R.id.finish_myneed);
			TextView cancel = (TextView) view.findViewById(R.id.cancel_myneed);

			edit.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					editNeed(need.getId());

				}
			});

			finish.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					finistNeed(need.getId());
				}
			});

			cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					delNeed(need.getId());
				}
			});



		}

		return view;
	}

	protected void delNeed(Integer id) {
		// TODO Auto-generated method stub
		OkHttpClient client = Server.getSharedClient();
		MultipartBody.Builder body = new MultipartBody.Builder()
				.setType(MultipartBody.FORM)				
				.addFormDataPart("need_id", id.toString());

		Request request = Server.requestBuilderWithCs("need/del")
				.method("post", null)
				.post(body.build())
				.build();

		final ProgressDialog progressDialog = new ProgressDialog(getActivity());
		progressDialog.setMessage("请稍后");
		progressDialog.setCancelable(false);
		progressDialog.setCanceledOnTouchOutside(false);

		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(final Call arg0, Response arg1) throws IOException {
				// TODO Auto-generated method stub
				final String responseString = arg1.body().string();
				getActivity().runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						progressDialog.dismiss();

						try {
							new AlertDialog.Builder(getActivity())
							.setTitle("需求已取消")
							.setPositiveButton("返回", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									getActivity().finish();
								}
							}).show();
						} catch (Exception e) {
							// TODO: handle exception
							onFailure(arg0, (IOException) e);
						}
					}
				});
			}

			@Override
			public void onFailure(final Call arg0, final IOException arg1) {
				// TODO Auto-generated method stub
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						progressDialog.dismiss();
						new AlertDialog.Builder(getActivity())
						.setTitle("无法取消")
						//		.setMessage(arg1.getMessage())
						.setNegativeButton("返回", null)
						.show();
					}
				});
			}
		});
	}

	protected void finistNeed(Integer id) {
		// TODO Auto-generated method stub

		OkHttpClient client = Server.getSharedClient();
		MultipartBody.Builder body = new MultipartBody.Builder()
				.setType(MultipartBody.FORM)				
				.addFormDataPart("need_id", id.toString())
				.addFormDataPart("state", "1");

		Request request = Server.requestBuilderWithCs("need/state")
				.method("post", null)
				.post(body.build())
				.build();

		final ProgressDialog progressDialog = new ProgressDialog(getActivity());
		progressDialog.setMessage("请稍后");
		progressDialog.setCancelable(false);
		progressDialog.setCanceledOnTouchOutside(false);

		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(final Call arg0, Response arg1) throws IOException {
				// TODO Auto-generated method stub
				final String responseString = arg1.body().string();
				getActivity().runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						progressDialog.dismiss();

						try {
							new AlertDialog.Builder(getActivity())
							.setTitle("需求已完成")
							.setPositiveButton("返回", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									getActivity().finish();
								}
							}).show();
						} catch (Exception e) {
							// TODO: handle exception
							onFailure(arg0, (IOException) e);
						}
					}
				});
			}

			@Override
			public void onFailure(final Call arg0, final IOException arg1) {
				// TODO Auto-generated method stub
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						progressDialog.dismiss();
						new AlertDialog.Builder(getActivity())
						.setTitle("无法完成")
						//		.setMessage(arg1.getMessage())
						.setNegativeButton("返回", null)
						.show();
					}
				});
			}
		});
	}

	protected void editNeed(Integer id) {
		// TODO Auto-generated method stub
		OkHttpClient client = Server.getSharedClient();

		MultipartBody.Builder body = new MultipartBody.Builder()
				.setType(MultipartBody.FORM)
				.addFormDataPart("need_id",String.valueOf(need.getId()));

		Request request = Server.requestBuilderWithCs("need/findOne")
				.method("post", null)
				.post(body.build())
				.build();

		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				// TODO Auto-generated method stub
				try {
					need = new ObjectMapper().readValue(arg1.body().bytes(), Need.class);
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Intent itnt = new Intent(getActivity(), EditNeedActivity.class);
							itnt.putExtra("need", need);
							startActivity(itnt);

						}
					});
				} catch (Exception e) {
					// TODO: handle exception
					
				}
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				// TODO Auto-generated method stub

				getActivity().runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
					}
				});
				
			}
		});


	}


}
