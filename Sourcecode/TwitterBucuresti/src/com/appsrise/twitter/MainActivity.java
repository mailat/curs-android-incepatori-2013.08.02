package com.appsrise.twitter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.YambaClient;

public class MainActivity extends Activity implements OnClickListener, OnPreferenceChangeListener{
	EditText editText;
	Button updateButton;
	TextView textCount;
	SharedPreferences prefs;
	String username;
	String password;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//prepare the preferences
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		username = prefs.getString("username", "student");
		password = prefs.getString("password", "password");
		
		//obtine textul din edit text
		editText = (EditText) findViewById(R.id.editText);
		updateButton = (Button) findViewById(R.id.updateButton);
		textCount = (TextView) findViewById(R.id.textCount);
		
		updateButton.setOnClickListener(this);

		// watch for the number of inserted characters
		TextWatcher watcher = new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
				int count = 140 - s.length();
				textCount.setText(Integer.toString(count));
				Log.d("TwitterBucuresti", "Chars left:" + count );
				
				// if we have 50 chars left we change the colour to RED
				if (count<50)
					textCount.setTextColor(Color.RED);
				else
					textCount.setTextColor(Color.BLACK);
				
			}
		};
		editText.addTextChangedListener(watcher);
		
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		//call the preference activity
		startActivity(new Intent(this, PrefsActivity.class));
		return (true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		//chemam AsynkTask
		new PostToTwitter().execute();
	}
	
	class PostToTwitter extends AsyncTask<String, Integer, String>
	{

		@Override
		protected String doInBackground(String... params) {

			
			//posteaza textul pe twitter server
			YambaClient yambaClient = new YambaClient(username, password);
			try {
				yambaClient.postStatus(editText.getText().toString());
				Log.d("TwitterBucuresti", "We just posted: " + editText.getText().toString());
				return ("OK");
			} catch (Throwable e) {
				Log.d("TwitterBucuresti", "Error + " + e.getMessage());
				e.printStackTrace();
				return ("Eroare la postare");
			}
		}

		@Override
		protected void onPostExecute(String result) {
			Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
		}	
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		//read from preferences
		username = prefs.getString("username", "student");
		password = prefs.getString("password", "password");
		return false;
	}

}
