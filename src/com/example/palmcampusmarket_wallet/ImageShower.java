package com.example.palmcampusmarket_wallet;

import android.app.Activity;
import android.os.Bundle;

import com.example.palmcampusmarket_client.R;

import android.os.Handler;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2016/12/22.
 */
public class ImageShower extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageshower);

        final ImageLoadingDialog dialog = new ImageLoadingDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
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
