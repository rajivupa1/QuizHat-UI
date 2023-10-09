package com.winbee.quizhat.Utils;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressBarUtil {
    private ProgressDialog progressBar;
    public ProgressBarUtil(Context context){
        if(progressBar==null) {
            progressBar = new ProgressDialog(context);
        }
    }
    public void showProgress(){
        if(progressBar!=null) {
            progressBar.setCancelable(false);
            progressBar.setMessage("Please wait . . .");
            progressBar.show();
        }
    }
    public void hideProgress(){
        if(progressBar!=null){
            progressBar.dismiss();
        }
    }
}
