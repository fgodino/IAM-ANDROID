package com.godino.emotion.newstatus;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar;

import com.godino.emotion.R;
import com.godino.emotion.adapter.StatusTabsAdapter;;

public class SelectStatus extends SherlockFragmentActivity implements
TabListener {

	private ViewPager viewPager;
	private StatusTabsAdapter mAdapter;
	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.slide_in_up,R.anim.stay);

		setContentView(R.layout.activity_status);
		// Initilization
		actionBar = getSupportActionBar();
		viewPager = (ViewPager) findViewById(R.id.status_pager);
		mAdapter = new StatusTabsAdapter(getSupportFragmentManager());

		viewPager.setAdapter(mAdapter);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		int[] tabs = {R.drawable.ic_group, R.drawable.ic_person};
		// Adding Tabs
		for (int tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setIcon(tab_name)
					.setTabListener(this));
		}

		/**
		 * on swiping the viewpager make respective tab selected
		 * */
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// on tab selected
		// show respected fragment view
		viewPager.setCurrentItem(tab.getPosition());
		//TODO tab.getIcon().setColorFilter(Color.RED, Mode.LIGTHEN);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		tab.getIcon().clearColorFilter();
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		//closing transition animations
		overridePendingTransition(R.anim.stay,R.anim.slide_out_down);
	}

}
