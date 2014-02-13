package com.godino.emotion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.widget.SearchView;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.godino.emotion.models.Person;
import com.godino.emotion.models.Status;
import com.godino.emotion.utils.DateTypeAdapter;
import com.godino.emotion.utils.GsonRequest;
import com.godino.emotion.utils.VolleyLoaders;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FriendsFragment extends SherlockListFragment {

	private CustomAdapter adapter;
	private ListView listView;
	private ImageButton btnRefresh;

	private View _fragmentView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setRetainInstance(true);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		_fragmentView = inflater.inflate(R.layout.fragment_friends, container, false);
		return _fragmentView;
	}

	@Override
	public void onActivityCreated(Bundle state){
		super.onActivityCreated(state);
		Toast.makeText(getSherlockActivity(), "Soy Friends", Toast.LENGTH_SHORT).show();

		if(state == null){
			getFriends();
		}
		adapter =new CustomAdapter(getActivity());

		listView = (ListView) getView().findViewById(android.R.id.list);
		btnRefresh = (ImageButton) getActivity().findViewById(R.id.button_refresh);

		btnRefresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getFriends();
			}
		});

		listView.setAdapter(adapter);

	}


	@Override 
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.main, menu); 
		SearchView searchView = (SearchView)menu.findItem(R.id.search).getActionView();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);

		// Mostramos un mensaje con el elemento pulsado
		/*Toast.makeText(getActivity(), "Ha pulsado " + people.get(position).getName(),
				Toast.LENGTH_SHORT).show();*/
	}

	public class CustomAdapter extends ArrayAdapter<Person>{

		private LayoutInflater inflater;

		public CustomAdapter(Context context) {
			super(context, R.layout.friend_listview, new ArrayList<Person>());
			inflater = LayoutInflater.from(context);
		}

		@Override
		public View getView(int position, View rowView, ViewGroup parent) {

			Person person = getItem(position);
			Status status;

			if ( rowView == null ) {
				rowView = inflater.inflate(R.layout.friend_listview, null);
			}

			TextView name = (TextView) rowView.findViewById(R.id.friend_name);
			TextView tStatus = (TextView) rowView.findViewById(R.id.friend_status);
			ImageView tag = (ImageView) rowView.findViewById(R.id.friend_colortag);
			TextView update = (TextView) rowView.findViewById(R.id.friend_update);
			NetworkImageView picture = (NetworkImageView) rowView.findViewById(R.id.friend_image);


			System.out.println(picture.getMeasuredWidth());
			name.setText(person.getName());
			picture.setImageUrl("http://upload.wikimedia.org/wikipedia/commons/f/fc/Nemer_Saade_Profile_Picture.jpg", VolleyLoaders.mImageLoader);

			if ((status = person.getStatus() )!= null){
				tStatus.setText(status.getTitle());
				int color = Color.parseColor(status.getColor());   
				tag.setBackgroundColor(color);
				update.setText(status.getDifferenceDate());
			}
			return rowView;
		}

	}

	public static final String SERVER_URL = "https://10.0.2.2:6001/friends";

	public void getFriends(){
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Date.class, new DateTypeAdapter());

		Gson gson = gsonBuilder.create();
		GsonRequest<Person[]> myReq = new GsonRequest<Person[]>(Method.GET,
				SERVER_URL,
				Person[].class,
				createMyReqSuccessListener(),
				createMyReqErrorListener(),
				gson,
				true);

		myReq.setShouldCache(true);

		ApplicationController.getInstance().addToSecureRequestQueue(myReq);
	}

	private Response.Listener<Person[]> createMyReqSuccessListener() {
		return new Response.Listener<Person[]>() {
			@Override
			public void onResponse(Person[] response) {
				List<Person> people = Arrays.asList(response);
				adapter.addAll(people);
				adapter.notifyDataSetChanged();
			}
		};
	}

	private Response.ErrorListener createMyReqErrorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(getSherlockActivity(), "Unable to get friends", Toast.LENGTH_SHORT).show();
			}
		};
	}

}
