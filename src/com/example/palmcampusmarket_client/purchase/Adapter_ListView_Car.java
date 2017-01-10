package com.example.palmcampusmarket_client.purchase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.palmcampusmarket_client.R;
import com.example.palmcampusmarket_client.api.Server;
import com.example.palmcampusmarket_client.api.entity.Commodity;
import com.example.palmcampusmarket_client.api.entity.PurchaseHistory;
import com.example.palmcampusmarket_client.api.entity.User;
import com.example.palmcampusmarket_client.fragment.ImageView;


import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import android.widget.TextView;

public class Adapter_ListView_Car extends BaseAdapter {

	private Context context;
	private OnCheckedChangedListener listener;

	private List<PurchaseHistory> data;
	private SparseArray<Boolean> dataChecked = new SparseArray<Boolean>();


	public Adapter_ListView_Car(Context context) {
		this.context = context;

	}

	public void setAdapterCarList(List<PurchaseHistory> purchaseHistory){
		this.data = purchaseHistory;
		notifyDataSetChanged();

	}


	@Override
	public int getCount() {

		return data==null ? 0: data.size();
	}

	@Override
	public Object getItem(int position) {

		return null;
	}

	@Override
	public long getItemId(int position) {

		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		HolderView holderView = null;
		if(convertView==null){
			holderView=new HolderView();
			convertView = LayoutInflater.from(context).inflate(R.layout.fragment_purchase_order,null);
			holderView.buy_num=(TextView)convertView.findViewById(R.id.num);
			holderView.buyer_name=(TextView)convertView.findViewById(R.id.buyer_name);
			holderView.buyer_telephone=(TextView)convertView.findViewById(R.id.buyer_telephone);
			holderView.commodity_describe=(TextView)convertView.findViewById(R.id.commodity_describe);
			holderView.commodity_picture=(ImageView)convertView.findViewById(R.id.commodity_picture);
			holderView.price=(TextView)convertView.findViewById(R.id.price);
			holderView.pay=(Button)convertView.findViewById(R.id.pay);
			holderView.check=(CheckBox)convertView.findViewById(R.id.check);
			convertView.setTag(holderView);
		}else{
			holderView=(HolderView)convertView.getTag();
		}

		final PurchaseHistory purchaseHistory=data.get(position);
		final Commodity commodity=purchaseHistory.getCommodity();
		User user=purchaseHistory.getUser();
		holderView.buy_num.setText("x"+purchaseHistory.getBuyNumber());
		holderView.price.setText("总价："+purchaseHistory.getTotalPrice());
		holderView.buyer_name.setText("收货人："+user.getAccount());
		holderView.buyer_telephone.setText("联系电话："+user.getTelephone());
		holderView.commodity_describe.setText(commodity.getCommDescribe());
		holderView.commodity_picture.load(Server.serverAddress+commodity.getCommImage());
		holderView.check.setChecked(dataChecked.get(purchaseHistory.getId(),false));
		holderView.check.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				dataChecked.put(purchaseHistory.getId(), isChecked);


				listener.onItemCheckStatusChanged();
			}
		});


		return convertView;
	}

	public void toggleAllCheckState(boolean checked){
		for(PurchaseHistory history : data){
			dataChecked.put(history.getId(), checked);

		}

		notifyDataSetChanged();
	}

	public int getTotalPrice(){
		int totalPrice = 0;

		for(PurchaseHistory history : data){
			if(dataChecked.get(history.getId(), false))
			{	
				totalPrice += history.getTotalPrice();
			}					
		}

		return totalPrice;
	}

	public List<PurchaseHistory> getCheckedHistory(){
		List<PurchaseHistory> data1 = new ArrayList<PurchaseHistory>();

		for(PurchaseHistory history : data){
			if(dataChecked.get(history.getId(), false))
			{	
				data1.add(history);
			}					
		}

		return data1;
	}

	public class HolderView {

		private ImageView commodity_picture;
		private TextView commodity_describe;
		private TextView buyer_telephone;
		private TextView buyer_name;
		private TextView buy_num;
		private TextView price;
		private Button pay;
		private CheckBox check;

	}

	public int selectNumber(){
		int num_choice = 0;
		for (PurchaseHistory history : data) {

			if (dataChecked.get(history.getId(), false)) {

				num_choice += 1;

			}

		}

		return num_choice;
	}

	public int adapterSize(){
		int size=0;
		size=data.size();
		return size;
	}

	public interface OnCheckedChangedListener{
		void onItemCheckStatusChanged();
	}

	public void setOnCheckedChangedListener(OnCheckedChangedListener listener){
		this.listener=listener;
	}
}
