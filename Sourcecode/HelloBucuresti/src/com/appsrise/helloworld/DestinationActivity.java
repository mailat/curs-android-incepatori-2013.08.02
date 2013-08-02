package com.appsrise.helloworld;

import java.util.ArrayList;

import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class DestinationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_destination);
		
		Intent intent = getIntent();
		String nume = intent.getStringExtra("nume");
		String password = intent.getStringExtra("password");
		
		Log.d("HelloBucuresti", "onCreate in DestinationActivity, nume:" + nume + " , parola:" + password );
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.destination, menu);
		return true;
	}
	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		citesteContactele();
		
		return super.onOptionsItemSelected(item);
		

	}

	public void citesteContactele() {    
		ArrayList<String> listaContacte = new ArrayList<String>();
		String[] columns = new String[] { People.NAME, People.NUMBER };
		Uri mContacts = People.CONTENT_URI;
		Cursor mCur = managedQuery(mContacts, columns, null, null, People.NAME);
		if (mCur.moveToFirst()) {
		  do {
			  listaContacte.add(mCur.getString(mCur.getColumnIndex(People.NAME)));
			  Log.d("HelloBucuresti", "citit: " +  mCur.getString(mCur.getColumnIndex(People.NAME)));
		  } while (mCur.moveToNext());
		}
		Toast.makeText(this,  "Am citit "  + listaContacte.size(), Toast.LENGTH_LONG).show();
	}


}
