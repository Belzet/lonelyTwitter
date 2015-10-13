package ca.ualberta.cs.lonelytwitter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class LonelyTwitterActivity extends Activity {

	private static final String FILENAME = "file.sav";
	private LonelyTwitterActivity activity = this;

	private Button saveButton;

	public Button getSaveButton() {
		return saveButton;
	}

	private EditText bodyText;

	public EditText getBodyText() {
		return bodyText;
	}

	private static ListView oldTweetsList;

	public static ListView getOldTweetsList() {
		return oldTweetsList;
	}

	private static ArrayList<Tweet> tweets = new ArrayList<Tweet>();

	public static ArrayList<Tweet> getTweets() {
		return tweets;
	}

	private ArrayAdapter<Tweet> adapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState); //view - not changing part of the model
		setContentView(R.layout.main);   //view - not changing part of the model

		//all view - part of the user interface without changing state of the model
		bodyText = (EditText) findViewById(R.id.body);
		saveButton = (Button) findViewById(R.id.save);
		Button clearButton = (Button) findViewById(R.id.clear);
		oldTweetsList = (ListView) findViewById(R.id.oldTweetsList);


		saveButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				setResult(RESULT_OK);
				String text = bodyText.getText().toString(); //controller - changes the UI
				tweets.add(new NormalTweet(text)); //controller - changes the UI
				adapter.notifyDataSetChanged(); //view - telling android to update display, not model
				saveInFile(); //model - changing on the disk, not for the user
			}
		});

		clearButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				setResult(RESULT_OK);
				try {
					FileOutputStream fos = openFileOutput(FILENAME, 0); //model - works on file on disk
					fos.close(); //model - works on file on disk
				} catch (FileNotFoundException e) {
					throw new RuntimeException(e); //controller - changes view through exception
				} catch (IOException e) {
					throw new RuntimeException(e); //controller - changes view through exception
				}
				deleteFile(FILENAME); //model - deletes the file on the disk
				tweets.clear(); //controller - changes the UI
				adapter.notifyDataSetChanged(); //view - changes android's display
			}
		});

		oldTweetsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(activity, EditTweetActivity.class);
				intent.putExtra("position", position);
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart(); //view - works on android's display
		loadFromFile(); //controller - takes from disk to change UI
		adapter = new ArrayAdapter<Tweet>(this, R.layout.list_item, tweets); //view - operates on android display only
		oldTweetsList.setAdapter(adapter); //view - operates on display
	}

	private void loadFromFile() {
		try {
			FileInputStream fis = openFileInput(FILENAME); //model - opens file on disk
			BufferedReader in = new BufferedReader(new InputStreamReader(fis)); //controller - going to take from UI to model
			Gson gson = new Gson(); //controller - going to operate on model from UI
			// Taken from https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html 2015-09-22
			Type listType = new TypeToken<ArrayList<NormalTweet>>() {}.getType(); //controller - used by Json to operate on model
			tweets = gson.fromJson(in, listType); //controller - taking from disk to UI
		} catch (FileNotFoundException e) {
			tweets = new ArrayList<Tweet>(); //controller - changes UI
		} catch (IOException e) {
			throw new RuntimeException(e); //controller - changes view through exception
		}
	}
	
	private void saveInFile() {
		try {
			FileOutputStream fos = openFileOutput(FILENAME, 0); //model - opening file on disk
			OutputStreamWriter writer = new OutputStreamWriter(fos); //controller - moving UI to disk
			Gson gson = new Gson(); //controller - operating on model from UI
			gson.toJson(tweets, writer); //model - writing from Json to disk
			writer.flush(); //controller - pushing from UI to model
			fos.close(); //model - closing file on disk
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e); //controller - changes view through exception
		} catch (IOException e) {
			throw new RuntimeException(e); //controller - changes view through exception
		}
	}
}