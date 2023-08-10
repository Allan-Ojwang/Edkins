package com.ojwang.edkins.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class ConfirmAlertDialog {

        private final Context mContext;
        private final String mMessage;
        private final DialogInterface.OnClickListener mClickListener;

        public ConfirmAlertDialog(Context context, String message, DialogInterface.OnClickListener clickListener) {
            mContext = context;
            mMessage = message;
            mClickListener = clickListener;
        }

        public void showDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setMessage(mMessage)
                    .setPositiveButton("Yes", mClickListener)
                    .setNegativeButton("No", mClickListener);
            builder.create().show();
        }

}
