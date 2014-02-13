package com.godino.emotion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.godino.emotion.R;

public class WorldFragment extends SherlockFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_world, container, false);
		
		return rootView;
	}
	
	@Override
	public void onActivityCreated(Bundle state){
		super.onActivityCreated(state);
		Toast.makeText(getSherlockActivity(), "Soy Word", Toast.LENGTH_SHORT).show();
	}
	
}
