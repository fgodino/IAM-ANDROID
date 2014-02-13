package com.godino.emotion.utils;

import java.util.HashMap;
import java.util.Map;

import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;

public abstract class SecureRequest<T> extends Request<T>{

	private boolean secure;
	
	private static final String YOUR_USERNAME = "fgodino";
	private static final String YOUR_PASSWORD = "password";
	
	public SecureRequest(int method, String url, ErrorListener listener, boolean secure) {
		super(method, url, listener);
		this.secure = secure;
	}
	
	private Map<String,String> getAuthHeaders() throws AuthFailureError{
		Map<String,String> headers = new HashMap<String, String>();
		String credentials = YOUR_USERNAME + ":" + YOUR_PASSWORD;  
		String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
		headers.put("Authorization", "Basic " + base64EncodedCredentials);
		
		return headers;
	}
	
	
	@Override
	public Map<String,String> getHeaders() throws AuthFailureError {
		return getAuthHeaders();
	}
}
