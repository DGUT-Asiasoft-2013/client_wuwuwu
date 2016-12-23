package com.example.palmcampusmarket_wallet;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.example.palmcampusmarket_client.R;

/**
 * Created by Administrator on 2016/12/22.
 */
public class ImageLoadingDialog extends Dialog {
    public ImageLoadingDialog(Context context) {
        super(context, R.style.ImageloadingDialogStyle);
    }

    public ImageLoadingDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_imageloading);
    }
}
