package com.example.palmcampusmarket_wallet;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.example.palmcampusmarket_client.R;
import com.example.palmcampusmarket_client.api.Server;
import com.example.palmcampusmarket_client.api.entity.User;
import com.example.palmcampusmarket_client.fragment.ImageDown;

import android.os.Handler;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

import java.net.URI;

/**
 * Created by Administrator on 2016/12/22.
 */
public class ImageShower extends Activity {
    ImageView imageView;
    Context context;
    ImageDown imageDown;
    User user;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageshower);

        context = getBaseContext();
        imageDown = new ImageDown(context);

        imageView = (ImageView) findViewById(R.id.me_iv);
        user = (User) getIntent().getSerializableExtra("user");

        final ImageLoadingDialog dialog = new ImageLoadingDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                imageDown.loadImage(imageView, user.getAvatar(), new ImageDown.ImageDownloadCallBack() {
                    public void onImageDownload(ImageView imageView, Bitmap bitmap) {
                        imageView.setImageBitmap(bitmap);
                    }
                });
                dialog.dismiss();
            }
        }, 1000 * 2);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }
}
