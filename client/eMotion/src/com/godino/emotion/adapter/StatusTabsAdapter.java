package com.godino.emotion.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.godino.emotion.newstatus.StatusFragment;


public class StatusTabsAdapter extends FragmentStatePagerAdapter {

	private String [] sections = {"HOLA", "FEO"};
	
	public StatusTabsAdapter(FragmentManager fm) {
		super(fm);
		
	}

	@Override
	public Fragment getItem(int index) {
		StatusFragment sf = new StatusFragment();
		sf.setStatus(sections[index]);
		return sf;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return sections.length;
	}

	public String [] getSections() {
		return sections;
	}

}
