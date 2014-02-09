package com.godino.emotion;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.widget.SearchView;

import com.godino.emotion.R;
import com.godino.emotion.models.Person;
import com.godino.emotion.models.Status;
import com.godino.emotion.utils.DateTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.squareup.picasso.Picasso;

public class FriendsFragment extends SherlockListFragment {

	private List<Person> people;

	private CustomAdapter adapter;
	private ListView listView;
	private ImageButton btnRefresh;

	public FriendsFragment(){
		this.people = new ArrayList<Person>();
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
		adapter =new CustomAdapter(getActivity(), people);
		listView = (ListView) getView().findViewById(android.R.id.list);
		btnRefresh = (ImageButton) getActivity().findViewById(R.id.button_refresh);

		btnRefresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				PeopleFetcher fetcher = new PeopleFetcher();
				fetcher.execute();
			}
		});

		listView.setAdapter(adapter);
		PeopleFetcher fetcher = new PeopleFetcher();
		fetcher.execute();
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
		Toast.makeText(getActivity(), "Ha pulsado " + people.get(position).getName(),
				Toast.LENGTH_SHORT).show();
	}

	private void handleFriendsList(List<Person> people) {
		this.people = people;
		adapter.updateFriends(people);

		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				adapter.notifyDataSetChanged();
				for(Person p : FriendsFragment.this.people) {
					Toast.makeText(getSherlockActivity(), p.getName(), Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private void failedLoadingFriends() {
		getActivity().runOnUiThread(new Runnable() {
			@Override 
			public void run() {
				Toast.makeText(getSherlockActivity(), "Failed to load Posts. Have a look at LogCat.", Toast.LENGTH_SHORT).show();
			}
		});
	}

	public class CustomAdapter extends ArrayAdapter<Person>{

		private Context mContext;
		private List<Person> data;

		public void updateFriends(List<Person> data){
			this.data = data;
		}

		public CustomAdapter(Context context, List<Person> friends) {
			super(context, R.layout.friend_listview, friends);
			mContext = context;
			this.data = friends;

		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			Person person = data.get(position);
			Status status;

			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.friend_listview, parent, false);
			TextView name = (TextView) rowView.findViewById(R.id.friend_name);
			TextView tStatus = (TextView) rowView.findViewById(R.id.friend_status);
			ImageView tag = (ImageView) rowView.findViewById(R.id.friend_colortag);
			TextView update = (TextView) rowView.findViewById(R.id.friend_update);
			ImageView picture = (ImageView) rowView.findViewById(R.id.friend_image);

			
			System.out.println(picture.getMeasuredWidth());
			name.setText(person.getName());
			Picasso.with(getSherlockActivity())
			.load(person.getPicture())
			.into(picture);

			if ((status = person.getStatus() )!= null){
				tStatus.setText(status.getTitle());
				int color = Color.parseColor(status.getColor());   
				tag.setBackgroundColor(color);
				update.setText(status.getDifferenceDate());
			}
			return rowView;
		}

		@Override
		public int getCount(){
			return data.size();

		}

	}


	private class PeopleFetcher extends AsyncTask<Void, Void, String> {
		private static final String TAG = "PostFetcher";
		public static final String SERVER_URL = "http://10.0.2.2:6001/friends";

		private static final String YOUR_USERNAME = "fgodino";
		private static final String YOUR_PASSWORD = "password";

		@Override
		protected String doInBackground(Void... params) {
			try {
				//Create an HTTP client
				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet(SERVER_URL);

				String credentials = YOUR_USERNAME + ":" + YOUR_PASSWORD;  
				String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);  
				get.addHeader("Authorization", "Basic " + base64EncodedCredentials);

				//Perform the request and check the status code
				HttpResponse response = client.execute(get);
				StatusLine statusLine = response.getStatusLine();
				if(statusLine.getStatusCode() == 200) {
					HttpEntity entity = response.getEntity();
					InputStream content = entity.getContent();

					try {
						//Read the server response and attempt to parse it as JSON
						Reader reader = new InputStreamReader(content);

						GsonBuilder gsonBuilder = new GsonBuilder();
						gsonBuilder.registerTypeAdapter(Date.class, new DateTypeAdapter());

						Gson gson = gsonBuilder.create();
						List<Person> friends = new ArrayList<Person>();
						friends = Arrays.asList(gson.fromJson(reader, Person[].class));
						content.close();

						handleFriendsList(friends);
					} catch (Exception ex) {
						Log.e(TAG, "Failed to parse JSON due to: " + ex);
						failedLoadingFriends();
					}
				} else {
					Log.e(TAG, "Server responded with status code: " + statusLine.getStatusCode());
					failedLoadingFriends();
				}
			} catch(Exception ex) {
				Log.e(TAG, "Failed to send HTTP POST request due to: " + ex);
				failedLoadingFriends();
			}
			return null;
		} 
	}
}
