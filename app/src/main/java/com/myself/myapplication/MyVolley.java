/**
 * Copyright 2013 Ognyan Bankov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.myself.myapplication;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.Volley;

/**
 * Helper class that is used to provide references to initialized
 * RequestQueue(s) and ImageLoader(s)
 * 
 * @author Ognyan Bankov
 * 
 */
public class MyVolley {
	private static RequestQueue mRequestQueue;
	private static ImageLoader mImageLoader;


	private MyVolley() {
		// no instances
	}

	static void init(Context context) {
		mRequestQueue = Volley.newRequestQueue(context);
		int memClass = ((ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
		// Use 1/8th of the available memory for this memory cache.
		int cacheSize = 1024 * 1024 * memClass / 8;
        mImageLoader = new ImageLoader(mRequestQueue, new BitmapLruImageCache(cacheSize));
	}

	
	public static RequestQueue getRequestQueue() {
		if (mRequestQueue != null) {
			return mRequestQueue;
		} else {
			throw new IllegalStateException("RequestQueue not initialized");
		}
	}

	/**
	 * Returns instance of ImageLoader initialized with {@see FakeImageCache}
	 * which effectively means that no memory caching is used. This is useful
	 * for images that you know that will be show only once.
	 * 
	 * @return
	 */
	public static ImageLoader getImageLoader() {
		if (mImageLoader != null) {
			return mImageLoader;
		} else {
			throw new IllegalStateException("ImageLoader not initialized");
		}
	}
	
    private static class BitmapLruImageCache extends LruCache<String, Bitmap> implements ImageCache {

        public BitmapLruImageCache(int maxSize){
            super(maxSize);
        }

        @Override
        protected int sizeOf(String key, Bitmap value){
            return value.getRowBytes() * value.getHeight();
        }

        @Override
        public Bitmap getBitmap(String url){
            return get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap){
            put(url, bitmap);
        }
    }
    
//    public static void doVolleyPostRequest(String requestUrl, final Map<String, String> params, Response.Listener<String> succussListener, Response.ErrorListener errorListener){
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, succussListener, errorListener){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError{
//                return params;
//            }
//
//        };
//        getRequestQueue().add(stringRequest);
//    }
}
