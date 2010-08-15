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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Broadcast receiver that checks if the service should be started at boot time.
 * TODO: refactor this, so that it doesn't duplicate code from HeadphoneVolumeActivity.
 */
public class HeadsetRegistrationServiceBootStarter extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
			SharedPreferences preferences = context.getSharedPreferences(HeadphoneVolumeActivity.PREFS_NAME, Context.MODE_PRIVATE);
			boolean isEnabled = preferences.getBoolean(HeadphoneVolumeActivity.IS_RUNNING, false);
			if (isEnabled) {
				context.startService(new Intent(context, HeadsetRegistrationService.class));
			}
		}
	}	
}
