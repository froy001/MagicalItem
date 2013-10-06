package com.froy.magicalitem;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
/**
 * 
 * @author David Fradis
 * <p>
 * This activity is responsible for the splash screen
 *</p>
 */
public class Splash extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.splash);
		
		//wait 3 sec before continung
		
		Thread timer = new Thread() {
			public void run() {
				try {
					sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					Intent openMainActivity = new Intent(
							"com.froy.magicalitems.MENU");
					startActivity(openMainActivity);
				}
			}
		};

		timer.start();
	}

}
