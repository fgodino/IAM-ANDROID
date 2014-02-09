package com.godino.emotion.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.godino.emotion.FriendsFragment;
import com.godino.emotion.MeFragment;
import com.godino.emotion.WorldFragment;


public class TabsPagerAdapter extends FragmentStatePagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			return new FriendsFragment();
		case 1:
			return new MeFragment();
		case 2:
			// Movies fragment activity
			return new WorldFragment();
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}

}
