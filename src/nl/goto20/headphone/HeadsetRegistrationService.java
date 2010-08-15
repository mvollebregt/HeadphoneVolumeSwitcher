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

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

/**
 * This service registers the {@link HeadsetBroadcastReceiver} as a listener
 * for the HEADSET_PLUG intent. Apparently, because the HEADSET_PLUG intent
 * is sent with the FLAG_RECEIVER_REGISTERED_ONLY flag set, we need a service
 * that keeps the broadcast receiver registered. See 
 * http://groups.google.com/group/android-developers/browse_thread/thread/6d0dda99b4f42c8f/d7de082acdb0da25 
 * for more information.
 * 
 * @author Michel Vollebregt
 */
public class HeadsetRegistrationService extends Service {
	
	private HeadsetBroadcastReceiver receiver;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		receiver = new HeadsetBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.intent.action.HEADSET_PLUG");
		registerReceiver(receiver, filter);
	}
	
	@Override
	public void onDestroy() {
		unregisterReceiver(receiver);
	}
}
