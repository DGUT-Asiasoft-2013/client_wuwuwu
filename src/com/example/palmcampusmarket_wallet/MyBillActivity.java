package com.example.palmcampusmarket_wallet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.palmcampusmarket_client.R;
import com.example.palmcampusmarket_client.api.Server;
import com.example.palmcampusmarket_client.api.entity.Bill;
import com.example.palmcampusmarket_client.api.entity.Page;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/12/20.
 */
public class MyBillActivity extends Activity {
    TextView textView;
    List<Bill> dates;
    ListView bill_listview;
    int page;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bill);

        textView = (TextView) findViewById(R.id.title);
        textView.setText("我的账单");
        bill_listview = (ListView) findViewById(R.id.bill_list);
        bill_listview.setAdapter(listadapter);

        bill_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                particulars(position);
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        reload();
    }

    void particulars(int position){
        Bill bill = new Bill();
        bill = dates.get(position);
        Intent intent = new Intent(MyBillActivity.this,ImageShower.class);
        intent.putExtra("bill",bill);
        startActivity(intent);

    }

    void reload() {

        OkHttpClient client = Server.getSharedClient();
        Request request = Server.requestBuilderWithWallet("my_bill").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                try {
                    final String responseString = response.body().string();
                    Page<Bill> data = new ObjectMapper().readValue(responseString, new TypeReference<Page<Bill>>() {
                    });
                    MyBillActivity.this.page = data.getNumber();
                    MyBillActivity.this.dates = data.getContent();
                    MyBillActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listadapter.notifyDataSetInvalidated();
                        }
                    });
                } catch (final Exception e) {
                    MyBillActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialog.Builder(MyBillActivity.this)
                                    .setMessage(e.getMessage())
                                    .show();
                        }
                    });
                }

            }
        });
    }

    BaseAdapter listadapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return dates == null ? 0 : dates.size();
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
        public View getView(int position, View convertview, ViewGroup pareant) {
            ViewHoder viewHoder = null;
            if (convertview == null) {
                convertview = View.inflate(pareant.getContext(), R.layout.bill_list_item, null);
                viewHoder = new ViewHoder();
                viewHoder.imageView = (ImageView) convertview.findViewById(R.id.bill_list_iv);
                viewHoder.bill_list_item_name = (TextView) convertview.findViewById(R.id.bill_list_item_name);
                viewHoder.bill_list_item_time = (TextView) convertview.findViewById(R.id.bill_list_item_time);
                convertview.setTag(viewHoder);
            } else {
                viewHoder = (ViewHoder) convertview.getTag();
            }
            viewHoder.imageView.setImageResource(R.drawable.ic_launcher);
            viewHoder.bill_list_item_name.setText(dates.get(position).getCommodity().getCommName());
            viewHoder.bill_list_item_time.setText(DateFormat.format("yyyy-mm-dd hh:mm", dates.get(position).getCreateDate()));
            return convertview;
        }
        class ViewHoder {
            ImageView imageView;
            TextView bill_list_item_name;
            TextView bill_list_item_time;
        }

    };
}
