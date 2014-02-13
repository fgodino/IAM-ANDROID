package com.godino.emotion.utils;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;
import com.godino.emotion.ApplicationController;

public class VolleyLoaders {
	
	public static ImageLoader mImageLoader = new ImageLoader(ApplicationController.getInstance().getRequestQueue(), new ImageLoader.ImageCache() {
	    private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
	    public void putBitmap(String url, Bitmap bitmap) {
	        mCache.put(url, bitmap);
	    }
	    public Bitmap getBitmap(String url) {
	        return mCache.get(url);
	    }
	});
}
