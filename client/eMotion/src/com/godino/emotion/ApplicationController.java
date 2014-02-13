package com.godino.emotion;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import com.godino.emotion.ssl.SslHttpStack;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

public class ApplicationController extends Application {

	/**
	 * Log or request TAG
	 */
	public static final String TAG = "VolleyPatterns";

	/**
	 * Global request queue for Volley
	 */
	private RequestQueue mRequestQueue;
	private RequestQueue mRequestQueueSec;

	/**
	 * A singleton instance of the application class for easy access in other places
	 */
	private static ApplicationController sInstance;

	@Override
	public void onCreate() {
		super.onCreate();

		// initialize the singleton
		sInstance = this;
	}

	/**
	 * @return ApplicationController singleton instance
	 */
	public static synchronized ApplicationController getInstance() {
		return sInstance;
	}

	/**
	 * @return The Volley Request queue, the queue will be created if it is null
	 */
	public RequestQueue getRequestQueue() {
		// lazy initialize the request queue, the queue instance will be
		// created when it is accessed for the first time
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}

		return mRequestQueue;
	}

	public RequestQueue getSecureRequestQueue(){

		if (mRequestQueueSec == null) {
			mRequestQueueSec = Volley.newRequestQueue(this,new SslHttpStack(true));
		}

		return mRequestQueueSec;
	}

	/**
	 * Adds the specified request to the global queue, if tag is specified
	 * then it is used else Default TAG is used.
	 * 
	 * @param req
	 * @param tag
	 */
	public <T> void addToRequestQueue(Request<T> req, String tag) {
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

		VolleyLog.d("Adding request to queue: %s", req.getUrl());

		getRequestQueue().add(req);
	}

	public <T> void addToSecureRequestQueue(Request<T> req, String tag) {
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

		VolleyLog.d("Adding request to queue: %s", req.getUrl());

		getRequestQueue().add(req);

		getSecureRequestQueue().add(req);
	}


	/**
	 * Adds the specified request to the global queue using the Default TAG.
	 * 
	 * @param req
	 * @param tag
	 */
	public <T> void addToRequestQueue(Request<T> req) {
		// set the default tag if tag is empty
		req.setTag(TAG);

		getRequestQueue().add(req);
	}

	public <T> void addToSecureRequestQueue(Request<T> req) {
		// set the default tag if tag is empty
		req.setTag(TAG);

		getSecureRequestQueue().add(req);
	}

	/**
	 * Cancels all pending requests by the specified TAG, it is important
	 * to specify a TAG so that the pending/ongoing requests can be cancelled.
	 * 
	 * @param tag
	 */
	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}

	public void cancelPendingSecureRequests(Object tag) {
		if (mRequestQueueSec != null) {
			mRequestQueueSec.cancelAll(tag);
		}
	}
}