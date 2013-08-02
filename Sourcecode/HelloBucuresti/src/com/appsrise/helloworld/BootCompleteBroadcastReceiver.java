package com.appsrise.helloworld;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootCompleteBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equalsIgnoreCase("android.permission.RECEIVE_BOOT_COMPLETED" ))
				{
		Log.d("HelloBucuresti", "Am repornit emulator!");
		
		}
	}

}
