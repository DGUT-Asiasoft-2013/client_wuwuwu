package com.example.palmcampusmarket_wallet;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.example.palmcampusmarket_client.R;
import com.example.palmcampusmarket_client.api.Server;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/12/20.
 */
public class RechargeActivity extends Activity {
    EditText recharge_edit;
    Button recharge_btn;
    String [] datas;
    GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        datas = new String[]{
                "10","20","30","40","50","60","70","80","90"
        };
        recharge_edit = (EditText) findViewById(R.id.recharge_edit);
        gridView = (GridView) findViewById(R.id.recharge_gridView);
        gridView.setAdapter(gridviewadapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                recharge_edit.setText(datas[position]);
            }
        });
        findViewById(R.id.recharge_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //recharge();

            }
        });
    }

    public void recharge(){
        OkHttpClient client = Server.getSharedClient();
       MultipartBody body = new MultipartBody.Builder()
               .setType(MultipartBody.FORM)
               .addFormDataPart("money",recharge_edit.getText().toString())
               .build();
        Request request = Server.requestBuilderWithApi("")
                .post(body).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

    }
    BaseAdapter gridviewadapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return datas.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.gridview_item, null);
            TextView city = (TextView) convertView.findViewById(R.id.city);
            city.setText(datas[position]);
            return convertView;
        }
    };
}
