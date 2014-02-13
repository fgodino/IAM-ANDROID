package com.godino.emotion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar;

import com.godino.emotion.R;
import com.godino.emotion.adapter.TabsPagerAdapter;
import com.godino.emotion.newstatus.SelectStatus;

public class MainActivity extends SherlockFragmentActivity implements
 TabListener {

	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;

	private ImageButton buttonNewStatus;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent myIntent = new Intent(this, LoginActivity.class);
		startActivityForResult(myIntent, 0);
		
		setContentView(R.layout.activity_main);

		// Initilization
		viewPager = (ViewPager) findViewById(R.id.main_pager);
		actionBar = getSupportActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		buttonNewStatus = (ImageButton) findViewById(R.id.button_new_status);

		viewPager.setAdapter(mAdapter);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		int[] tabs = {R.drawable.ic_group, R.drawable.ic_person, R.drawable.ic_map };
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

		buttonNewStatus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent( v.getContext(), SelectStatus.class);
				startActivityForResult(myIntent, 0);
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

}
