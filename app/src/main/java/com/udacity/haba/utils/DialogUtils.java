package com.udacity.haba.utils;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import com.udacity.haba.R;

public final class DialogUtils {

    private DialogUtils() {}

    public static void showError(Context context, String title, String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder
                .setTitle(title)
                .setMessage(text)
                .setPositiveButton(context.getString(R.string.ok), (dialog, which) -> {
                    dialog.dismiss();
                })
                .create()
                .show();
    }
}
