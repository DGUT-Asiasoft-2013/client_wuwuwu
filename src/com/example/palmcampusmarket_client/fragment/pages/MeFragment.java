package com.example.palmcampusmarket_client.fragment.pages;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.palmcampusmarket_client.R;
import com.example.palmcampusmarket_client.api.Server;
import com.example.palmcampusmarket_client.api.entity.User;
import com.example.palmcampusmarket_client.collect.CollectionsActivity;
import com.example.palmcampusmarket_client.fragment.AvatarView;
import com.example.palmcampusmarket_client.fragment.ImageDown;
import com.example.palmcampusmarket_wallet.ImageShower;
import com.example.palmcampusmarket_wallet.MyWalletActivity;
import com.example.palmcampusmarket_wallet.SettingActivity;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/12/22.
 */
public class MeFragment extends Fragment implements View.OnClickListener{
   View view;
   User current_user;
  // ImageDown my_avatarview;
  AvatarView my_avatarview;
    TextView my_nickname,photo_tongyong_text,collect_tongyong_text,wallet_tongyong_text,setting_tongyong_text;
    ImageView photo_tongyong_iv,wallet_tongyong_iv,collect_tongyong_iv,setting_tongyong_iv;
    RelativeLayout me,my_page_photo,my_page_collect,my_page_wallet,my_page_setting;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       if (view == null){
           view = inflater.inflate(R.layout.fragment_page_me,null);
           findview();

           me.setOnClickListener(this);
           my_page_photo.setOnClickListener(this);
           my_page_collect.setOnClickListener(this);
           my_page_wallet.setOnClickListener(this);
           my_page_setting.setOnClickListener(this);
           my_avatarview.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent intent = new Intent(getActivity(),ImageShower.class);
                   intent.putExtra("user",current_user);
                   startActivity(intent);
               }
           });
       }
        return view;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.my_page_photo:
                break;
            case R.id.my_page_collect:
            	intent.setClass(getActivity(),CollectionsActivity.class);
                startActivity(intent);
                break;
            case R.id.my_page_wallet:
                intent.setClass(getActivity(),MyWalletActivity.class);
                startActivity(intent);
                break;
            case R.id.my_page_setting:
                break;
            case R.id.me:
                intent.setClass(getActivity(),SettingActivity.class);

                startActivity(intent);
                break;
        }
    }

    public void findview(){
        me = (RelativeLayout) view.findViewById(R.id.me);
        my_avatarview = (AvatarView) view.findViewById(R.id.my_page_image);
        my_nickname = (TextView) view.findViewById(R.id.my_page_ninckname);
        my_page_photo = (RelativeLayout) view.findViewById(R.id.my_page_photo);
        my_page_collect = (RelativeLayout) view.findViewById(R.id.my_page_collect);
        my_page_wallet = (RelativeLayout) view.findViewById(R.id.my_page_wallet);
        my_page_setting = (RelativeLayout) view.findViewById(R.id.my_page_setting);


        photo_tongyong_iv = (ImageView) my_page_photo.findViewById(R.id.tongyong_image_view);
        photo_tongyong_text = (TextView) my_page_photo.findViewById(R.id.tongyong_textView);
        collect_tongyong_iv = (ImageView) my_page_collect.findViewById(R.id.tongyong_image_view);
        collect_tongyong_text = (TextView) my_page_collect.findViewById(R.id.tongyong_textView);
        wallet_tongyong_iv = (ImageView) my_page_wallet.findViewById(R.id.tongyong_image_view);
        wallet_tongyong_text = (TextView) my_page_wallet.findViewById(R.id.tongyong_textView);
        setting_tongyong_iv = (ImageView) my_page_setting.findViewById(R.id.tongyong_image_view);
        setting_tongyong_text = (TextView) my_page_setting.findViewById(R.id.tongyong_textView);

        photo_tongyong_iv.setImageResource(R.drawable.picture);
        photo_tongyong_text.setText("相片");
        collect_tongyong_iv.setImageResource(R.drawable.collect);
        collect_tongyong_text.setText("收藏");
        wallet_tongyong_iv.setImageResource(R.drawable.money);
        wallet_tongyong_text.setText("钱包");
        setting_tongyong_iv.setImageResource(R.drawable.setting);
        setting_tongyong_text.setText("设置");

    }

    @Override
    public void onResume() {
        super.onResume();
        OkHttpClient client = Server.getSharedClient();
        Request request = Server.requestBuilderWithApi("me").get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MeFragment.this.onFailuer(call,e);
                    }
                });

            }
            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                try {
                    final User user = new ObjectMapper().readValue(response.body().bytes(), User.class);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MeFragment.this.onResponse(call, user);
                        }
                    });
                }catch (final Exception e){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MeFragment.this.onFailuer(call,e);
                        }
                    });
                }

            }
        });
    }

    protected void onResponse(Call call, User user){
        current_user = user;
        my_avatarview.load(Server.serverAddress+user.getAvatar());
        my_nickname.setText(user.getAccount());




    }

    protected void onFailuer(Call call, Exception ex){

    }
}
