/*
Copyright 2010 Michel Vollebregt
  
This file is part of Headphone Volume Switcher.

Headphone Volume Switcher is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Headphone Volume Switcher is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Headphone Volume Switcher. If not, see <http://www.gnu.org/licenses/>.
*/

package nl.goto20.headphone;

import nl.goto20.headphone.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;

/**
 * TODO: Write documentation
 * TODO: use name so we can use different head sets with different settings? (if so: change introductory text)
 */
public class HeadsetBroadcastReceiver extends BroadcastReceiver {
	
	private static final String PLUGGED = "plugged.%d";
	private static final String UNPLUGGED = "unplugged.%d";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		int state = intent.getIntExtra("state", 2);
		// String name = intent.getStringExtra("name");
		
		SharedPreferences preferences = context.getSharedPreferences(HeadphoneVolumeActivity.PREFS_NAME, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		
		String previousState = (state == 0 ? PLUGGED : UNPLUGGED);
		String newState = (state == 0 ? UNPLUGGED: PLUGGED);
		
		AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		for (int streamNumber = 0; streamNumber < AudioManager.NUM_STREAMS; streamNumber++) {
			int currentVolume = audioManager.getStreamVolume(streamNumber);
			editor.putInt(String.format(previousState, streamNumber), currentVolume);
			int newVolume = preferences.getInt(String.format(newState, streamNumber), currentVolume); 
			audioManager.setStreamVolume(streamNumber, newVolume, 0);
			// message.append(streamNumber + ": " + audioManager.getStreamVolume(streamNumber) + "; ");
		}
		editor.commit();
	}
	
	
	
	static void showMessage(Context context, String message) {
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(ns);
		int icon = R.drawable.icon;
		CharSequence tickerText = message;
		long when = System.currentTimeMillis();
		Notification notification = new Notification(icon, tickerText, when);
		CharSequence contentTitle = message;
		CharSequence contentText = "";
		Intent notificationIntent = new Intent(context, HeadsetBroadcastReceiver.class);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
		mNotificationManager.notify(1, notification);
	}

}
