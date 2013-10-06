package com.froy.magicalitem.test;

import com.froy.magicalitem.MainActivity;
import com.jayway.android.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;

public class MainActivityTest extends
		ActivityInstrumentationTestCase2<MainActivity> {
	
	private Solo solo;

	public MainActivityTest() {
		super(MainActivity.class);
		// TODO Auto-generated constructor stub
	}

	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	public void testHomeButtons(){
		
		solo.assertCurrentActivity("Check MainActivity", MainActivity.class);
	}

}
