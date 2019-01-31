package com.mmm.noureddine.mapp.components;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.mmm.noureddine.mapp.activities.AsyncActivity;
import com.mmm.noureddine.mapp.activities.CameraActivity;

public class AsyncTaskRunner extends AsyncTask<String, String, String> {


    private String resp;
    private Context context;
    ProgressDialog progressDialog;

    public AsyncTaskRunner(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        publishProgress("Sleeping..."); // Calls onProgressUpdate()
        try {
            int time = Integer.parseInt(params[0]) * 1000;

            Thread.sleep(time);
            resp = "Slept for " + params[0] + " seconds";
        } catch (InterruptedException e) {
            e.printStackTrace();
            resp = e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            resp = e.getMessage();
        }
        return resp;
    }


    @Override
    protected void onPostExecute(String result) {
        // execution of result of Long time consuming operation
        progressDialog.dismiss();
        //finalResult.setText(result);
    }


    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(this.context,
                "ProgressDialog", "Waiting..... ");
        // "Wait for " + time.getText().toString() + " seconds");
    }


    @Override
    protected void onProgressUpdate(String... text) {
        // finalResult.setText(text[0]);

    }
}

