package com.godino.emotion.newstatus;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.widget.SearchView;

import com.godino.emotion.R;

public class StatusFragment extends SherlockListFragment {
	private String[] statusesAux = {"HOLA", "FER"};

	private List<String> statuses;

	private CustomAdapter adapter;
	private ListView listView;
	
	private String title;


	public StatusFragment(){
		statuses = Arrays.asList(statusesAux);
	}
	
	public void setStatus(String status) {
		this.title = status;
	}
	
	public String getStatus() {
		return title;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_friends, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle state){
		super.onActivityCreated(state);

		// Establecemos el Adapter a la Lista del Fragment
		adapter =new CustomAdapter(getActivity(), statuses);
		listView = (ListView) getView().findViewById(android.R.id.list);

		listView.setAdapter(adapter);
	}

	@Override 
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.main, menu); 
		SearchView searchView = (SearchView)menu.findItem(R.id.search).getActionView();
	}

	public class CustomAdapter extends ArrayAdapter<String>{

		private Context mContext;
		private List<String> data;

		public CustomAdapter(Context context, List<String> statuses) {
			super(context, R.layout.friend_listview, statuses);
			mContext = context;
			this.data = statuses;

		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.status_listview, parent, false);
			TextView text = (TextView) rowView.findViewById(R.id.text_status);

			text.setText(data.get(position));
			
			rowView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent myIntent = new Intent(v.getContext(), SelectColor.class);
					startActivityForResult(myIntent, 0);				
				}
			});
			return rowView;
		}

	}
}
