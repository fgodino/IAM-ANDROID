package com.godino.emotion.utils;

import org.apache.http.HttpResponse;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class ImageUploaderTask extends AsyncTask<String, Integer, HttpResponse> {
	
	
	private Context activity;
	private ProgressDialog simpleWaitDialog;
	
	public ImageUploaderTask (Context cls){
		this.activity = cls;
	}
	@Override
	protected void onPreExecute(){
		simpleWaitDialog = ProgressDialog.show(activity, "Wait", "Uploading Image");
	}
	@Override
	protected HttpResponse doInBackground(String... params) {
		try {
			HttpResponse res = ImageUploadUtility.doUploadinBackground(params[0]);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	protected void onPostExecute(HttpResponse result){
		simpleWaitDialog.dismiss();
	}
}