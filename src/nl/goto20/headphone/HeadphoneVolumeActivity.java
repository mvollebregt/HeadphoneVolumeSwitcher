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
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

/**
 * Activity to start / stop the head set volume service. 
 * The activity shows a start / stop button and starts or stops the {@link HeadsetRegistrationService}.
 * 
 * TODO: add a boot startup listener
 * TODO: add copyright notice on first startup of program
 * TODO: add another icon to the program
 * TODO: sign the program
 * TODO: use a PreferenceActivity (http://stackoverflow.com/questions/2027771/using-a-listview-to-create-a-settings-screen-in-android) for this screen
 * TODO: create a nice user interface for managing all volumes? 
 */
public class HeadphoneVolumeActivity extends Activity {
	
	public static final String PREFS_NAME = "prefs";
	public static final String IS_RUNNING = "isRunning";

	/**
	 * Shows the screen to start / stop the head set volume service.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
		boolean isRunning = preferences.getBoolean(IS_RUNNING, false);
		setContentView(R.layout.main);
		toggleButton(isRunning);
	}

	/**
	 * Toggles the service and saves the running state to the shared preferences.
	 */
	public void toggleService(View target) {
		CheckBox checkBox = (CheckBox) target;
		boolean isRunning = false;
		if (checkBox.isChecked()) {
			isRunning = null != startService(new Intent(this, HeadsetRegistrationService.class));
		} else {
			stopService(new Intent(this, HeadsetRegistrationService.class));
		}
		Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
		editor.putBoolean(IS_RUNNING, isRunning);
		editor.commit();
		toggleButton(isRunning);
	}
	
	/**
	 * Closes the activity when OK is pressed.
	 */
	public void closeActivity(View target) {
		finish();
	}

	/**
	 * Shows the correct state of the button.
	 */
	private void toggleButton(boolean isRunning) {
		CheckBox button = (CheckBox) findViewById(R.id.ServiceToggleButton);
		button.setText(isRunning ? R.string.stop: R.string.start);
		button.setChecked(isRunning);
	}
}
