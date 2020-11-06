package com.fighterdiet.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.fighterdiet.R;


public class ProgressDialog {
    private static AlertDialog alertDialog;

    private ProgressDialog() {}

    public static void showProgressDialog(Context context) {
        try {
            if (alertDialog != null && alertDialog.isShowing()) {
                return;
            }

            View view = LayoutInflater.from(context).inflate(R.layout.progress_dialog_layout, null, false);
            alertDialog = new AlertDialog.Builder(context)
                    .setView(view)
                    .setCancelable(false)
                    .create();

            if (alertDialog.getWindow() != null) {
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }

            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hideProgressDialog() {
        try {
            if (alertDialog != null && alertDialog.isShowing()) {
                alertDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
