package com.mmm.noureddine.mapp.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class PopUp {

    public static void displayAlert(Context context, String message, String positifBtn, String negatifBtn)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setInverseBackgroundForced(true);
        builder.setMessage(message).setCancelable(
                false).setPositiveButton(positifBtn,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        // code here
                        //  AlertDialogActivity.this.finish();
                        dialog.cancel();
                    }
                }).setNegativeButton(negatifBtn,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        //AlertDialogActivity.this.finish();
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}