package com.appsrise.twitter;

import com.marakana.android.yamba.clientlib.YambaClient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	EditText editText;
	Button updateButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//obtine textul din edit text
		editText = (EditText) findViewById(R.id.editText);
		updateButton = (Button) findViewById(R.id.updateButton);
		updateButton.setOnClickListener(this);

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
			YambaClient yambaClient = new YambaClient("student", "password");
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

}
