package com.godino.emotion.utils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.apache.commons.*;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.net.Uri;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
class ImageUploadUtility {
	
	public static final String SERVER_URL = "http://10.0.2.2:6001/me";

	private static final String YOUR_USERNAME = "fgodino";
	private static final String YOUR_PASSWORD = "password";

	public static HttpResponse doUploadinBackground(String filename) throws Exception{

        HttpClient client = new DefaultHttpClient();
        HttpPut post = new HttpPut(SERVER_URL);
        
        String credentials = YOUR_USERNAME + ":" + YOUR_PASSWORD;  
		String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);  
		post.addHeader("Authorization", "Basic " + base64EncodedCredentials);
		
        MultipartEntityBuilder mpEntity = MultipartEntityBuilder.create();
        mpEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        //Path of the file to be uploaded
        File file = new File(filename);
        ContentBody cbFile = new FileBody(file);         

        //Add the data to the multipart entity
        mpEntity.addPart("image", cbFile);  
        final HttpEntity yourEntity = mpEntity.build();
        
        class ProgressiveEntity implements HttpEntity {
            @Override
            public void consumeContent() throws IOException {
                yourEntity.consumeContent();                
            }
            @Override
            public InputStream getContent() throws IOException,
                    IllegalStateException {
                return yourEntity.getContent();
            }
            @Override
            public Header getContentEncoding() {             
                return yourEntity.getContentEncoding();
            }
            @Override
            public long getContentLength() {
                return yourEntity.getContentLength();
            }
            @Override
            public Header getContentType() {
                return yourEntity.getContentType();
            }
            @Override
            public boolean isChunked() {             
                return yourEntity.isChunked();
            }
            @Override
            public boolean isRepeatable() {
                return yourEntity.isRepeatable();
            }
            @Override
            public boolean isStreaming() {             
                return yourEntity.isStreaming();
            } // CONSIDER put a _real_ delegator into here!

            @Override
            public void writeTo(OutputStream outstream) throws IOException {

                class ProxyOutputStream extends FilterOutputStream {
                    /**
                     * @author Stephen Colebourne
                     */

                    public ProxyOutputStream(OutputStream proxy) {
                        super(proxy);    
                    }
                    public void write(int idx) throws IOException {
                        out.write(idx);
                    }
                    public void write(byte[] bts) throws IOException {
                        out.write(bts);
                    }
                    public void write(byte[] bts, int st, int end) throws IOException {
                        out.write(bts, st, end);
                    }
                    public void flush() throws IOException {
                        out.flush();
                    }
                    public void close() throws IOException {
                        out.close();
                    }
                } // CONSIDER import this class (and risk more Jar File Hell)

                class ProgressiveOutputStream extends ProxyOutputStream {
                    public ProgressiveOutputStream(OutputStream proxy) {
                        super(proxy);
                    }
                    public void write(byte[] bts, int st, int end) throws IOException {
                        out.write(bts, st, end);
                    }
                }

                yourEntity.writeTo(new ProgressiveOutputStream(outstream));
            }

        };
        ProgressiveEntity myEntity = new ProgressiveEntity();

        post.setEntity(myEntity);
        HttpResponse response = client.execute(post);        

        return response;

    } 

}