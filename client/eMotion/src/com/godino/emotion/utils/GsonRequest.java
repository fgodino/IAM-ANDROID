package com.godino.emotion.utils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;


public class GsonRequest<T> extends SecureRequest<T> {
	private final Gson mGson;
	private final Class<T> mClazz;
	private final Listener<T> mListener;
	private Map<String, String> headers;
	


	public GsonRequest(int method,
			String url,
			Class<T> clazz,
			Listener<T> listener,
			ErrorListener errorListener,
			boolean secure) {
		super(Method.GET, url, errorListener, secure);
		this.mClazz = clazz;
		this.mListener = listener;
		mGson = new Gson();
	}
	
	public GsonRequest(int method,
			String url,
			Class<T> clazz,
			Listener<T> listener,
			ErrorListener errorListener,
			Gson gson,
			boolean secure) {
		super(Method.GET, url, errorListener, secure);
		this.mClazz = clazz;
		this.mListener = listener;
		mGson = gson;	
	}
	
	public void setHeader(String title, String content) {
        headers.put(title, content);
    }


	@Override
	protected void deliverResponse(T response) {
		mListener.onResponse(response);
	}


	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
			return Response.success(mGson.fromJson(json, mClazz),
					HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JsonSyntaxException e) {
			return Response.error(new ParseError(e));
		}
	}
}