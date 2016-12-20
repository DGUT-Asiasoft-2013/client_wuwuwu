package com.example.palmcampusmarket_wallet;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.palmcampusmarket_client.R;

/**
 * Created by Administrator on 2016/12/20.
 */
public class MyBillActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bill);

    }

BaseAdapter listadapter = new BaseAdapter() {
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup pareant) {
        View view;
        if (convertview == null){
            LayoutInflater layoutInflater = LayoutInflater.from(pareant.getContext());
            view = layoutInflater.inflate(R.layout.bill_list_item,null);
        }else {
            view = convertview;
        }

        return view;
    }
};
}
