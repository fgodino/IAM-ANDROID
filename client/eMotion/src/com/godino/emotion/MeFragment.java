package com.godino.emotion;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.NetworkImageView;
import com.godino.emotion.models.Person;
import com.godino.emotion.models.Status;
import com.godino.emotion.utils.DateTypeAdapter;
import com.godino.emotion.utils.GsonRequest;
import com.godino.emotion.utils.VolleyLoaders;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MeFragment extends SherlockFragment {


	final int REQ_CODE_PICK_IMAGE= 1;
	final int REQ_CODE_CROP_IMAGE= 2;

	View thumbnail;
	private CharSequence[] items;
	private Uri outputTemp;
	NetworkImageView picture;
	private TextView name;
	private TextView status_name;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_me, container, false);

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle state){
		super.onActivityCreated(state);
		
		final AlertDialog.Builder builder = new AlertDialog.Builder(getSherlockActivity());

		items = new CharSequence[]{getResources().getText(R.string.change_picture),
				getResources().getText(R.string.show_picture)};

		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				switch (item) {
				case 0:
					getPicture();
					break;

				default:
					break;
				}
			}
		});

		name = (TextView) getView().findViewById(R.id.me_name);
		status_name = (TextView) getView().findViewById(R.id.me_status);
		picture = (NetworkImageView) getView().findViewById(R.id.me_image);
		thumbnail = getView().findViewById(R.id.me_thumbnail);
		thumbnail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog alert = builder.create();
				alert.show();

			}
		});
		getMe();

	}
	
	public static final String SERVER_URL_GETME = "https://10.0.2.2:6001/me";
	
	public void getMe(){
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Date.class, new DateTypeAdapter());

		Gson gson = gsonBuilder.create();
		GsonRequest<Person> myReq = new GsonRequest<Person>(Method.GET,
				SERVER_URL_GETME,
				Person.class,
				createMyReqSuccessListener(),
				createMyReqErrorListener(),
				gson,
				true);

		myReq.setShouldCache(true);

		ApplicationController.getInstance().addToSecureRequestQueue(myReq);
	}

	private Response.Listener<Person> createMyReqSuccessListener() {
		return new Response.Listener<Person>() {
			@Override
			public void onResponse(Person response) {
				Status status = response.getStatus();
				name.setText(response.getName());
				picture.setImageUrl("http://upload.wikimedia.org/wikipedia/commons/f/fc/Nemer_Saade_Profile_Picture.jpg", VolleyLoaders.mImageLoader);
				
				if(status != null){
					status_name.setText(status.getTitle());
				}
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

	private void performCrop(Uri picUri) {
		try {

			Intent cropIntent = new Intent("com.android.camera.action.CROP");
			// indicate image type and Uri
			cropIntent.setDataAndType(picUri, "image/*");
			// set crop properties
			cropIntent.putExtra("crop", "true");
			// indicate aspect of desired crop
			cropIntent.putExtra("aspectX", 1);
			cropIntent.putExtra("aspectY", 1);
			// indicate output X and Y
			cropIntent.putExtra("outputX", 128);
			cropIntent.putExtra("outputY", 128);
			// retrieve data on return
			cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputTemp);
			cropIntent.putExtra("output", outputTemp);
			cropIntent.putExtra("return-data", false);
			// start the activity - we handle returning in onActivityResult
			startActivityForResult(cropIntent, REQ_CODE_CROP_IMAGE);
		}
		// respond to users whose devices do not support the crop action
		catch (ActivityNotFoundException anfe) {
			// display an error message
			String errorMessage = "Whoops - your device doesn't support the crop action!";
			Toast toast = Toast.makeText(this.getActivity(), errorMessage, Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	private void getPicture() {

		final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "eMotion" + File.separator);
		root.mkdirs();
		final String fname = UUID.randomUUID().toString();
		final File sdImageMainDirectory = new File(root, fname);
		outputTemp = Uri.fromFile(sdImageMainDirectory);

		final List<Intent> cameraIntents = new ArrayList<Intent>();
		final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		final PackageManager packageManager = getSherlockActivity().getPackageManager();
		final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
		for(ResolveInfo res : listCam) {
			final String packageName = res.activityInfo.packageName;
			final Intent intent = new Intent(captureIntent);
			intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
			intent.setPackage(packageName);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, outputTemp);
			cameraIntents.add(intent);
		}

		// Filesystem.
		final Intent galleryIntent = new Intent();
		galleryIntent.setType("image/*");
		galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

		// Chooser of filesystem options.
		final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");

		// Add the camera options.
		chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));

		startActivityForResult(chooserIntent, REQ_CODE_PICK_IMAGE);

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(resultCode == Activity.RESULT_OK)
		{
			if(requestCode == REQ_CODE_PICK_IMAGE)
			{
				final boolean isCamera;
				if(data == null)
				{
					isCamera = true;
				}
				else
				{
					final String action = data.getAction();
					if(action == null)
					{
						isCamera = false;
					}
					else
					{
						isCamera = action.equals(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
					}
				}

				Uri selectedImageUri;
				if(isCamera)
				{
					selectedImageUri = outputTemp;
				}
				else
				{
					selectedImageUri = data == null ? null : data.getData();
				}
				performCrop(selectedImageUri);
			}
			else if (requestCode == REQ_CODE_CROP_IMAGE) { 
				
				
			}
		}
	}


}
