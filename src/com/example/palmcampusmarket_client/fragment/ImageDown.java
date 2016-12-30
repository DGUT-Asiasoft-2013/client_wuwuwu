package com.example.palmcampusmarket_client.fragment;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.*;
import android.widget.ImageView;

import com.example.palmcampusmarket_client.R;
import com.example.palmcampusmarket_client.api.Server;
import com.example.palmcampusmarket_client.api.entity.User;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/12/24.
 */
public class ImageDown {

    Handler mainThreadHander = new Handler();
    Context context;

    public ImageDown(Context context) {
        this.context = context;
    }

    public void loadImage(final ImageView imageView,final String url1,final ImageDownloadCallBack imageDownloadCallBack){
        String url = Server.serverAddress+url1;
        OkHttpClient client = Server.getSharedClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try{
                    byte[] bytes = response.body().bytes();
                    final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    mainThreadHander.post(new Runnable() {
                        @Override
                        public void run() {
                             if (bitmap!=null && imageDownloadCallBack != null){
                                 imageDownloadCallBack.onImageDownload(imageView,bitmap);
                             }
                        }
                    });
                }catch(Exception ex){
                   mainThreadHander.post(new Runnable() {
                       public void run() {
                           Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_launcher);
                           imageDownloadCallBack.onImageDownload(imageView,bitmap);
                       }
                   });
                }

            }
        });
    }

    //通过回调机制设置图片时的接口(类似于Button的Onclick)
    public interface ImageDownloadCallBack{
        //ImageView 你所想要设定的imageview Bitmap 想要设定的图片
        void onImageDownload(android.widget.ImageView imageView,Bitmap bitmap);
    }
}
