package com.godino.emotion.newstatus;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockActivity;
import com.godino.emotion.R;
import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.ColorPicker.OnColorChangedListener;


public class SelectColor extends SherlockActivity {
	
	private ColorPicker mColorPicker;
	private View preview;
	private ImageView previewTag;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_color);
		
		 mColorPicker = (ColorPicker) findViewById(R.id.color_picker);
		 preview = findViewById( R.id.layoutPreview); // root View id from that link
		 previewTag = (ImageView) preview.findViewById( R.id.friend_colortag );
		 
		 mColorPicker.setOnColorChangedListener(new OnColorChangedListener() {
	            @Override
	            public void onColorChanged(int color) {
	            	mColorPicker.setOldCenterColor(color);
	            	previewTag.setBackgroundColor(color);
	            }
	        });
	}
}