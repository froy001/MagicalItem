package com.froy.magicalitem.test;

import com.froy.magicalitem.ItemsListActivity;
import com.froy.magicalitem.MainActivity;
import com.froy.magicalitem.MyItemsActivity;
import com.froy.magicalitem.SearchItems;
import com.jayway.android.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ImageView;

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
	
	public void testHomeNavigationButtons(){
		
		//test for right activity
		
		
		//test main buttons
		solo.clickOnButton("Item List");
		solo.assertCurrentActivity("Check MainActivity 'Item List' Button", ItemsListActivity.class);
		solo.goBackToActivity("MainActivity");
		solo.clickOnButton("My Items");
		solo.assertCurrentActivity("Check MainActivity 'My Items' Button", MyItemsActivity.class);
		solo.goBackToActivity("MainActivity");
		solo.clickOnButton("Search Item");
		solo.assertCurrentActivity("Check MainActivity 'Search Item' Button", SearchItems.class);
	}
	
	public void testHomeTitleImage(){
		
		solo.assertCurrentActivity("Check MainActivity", MainActivity.class);
		for(View testView:solo.getViews()){
			if(testView instanceof ImageView){
				//Found the ImageView and can wowrk with it
				ImageView myImageToTest = (ImageView)testView;
				assertNotNull("Check MainActivity ImageView", myImageToTest.getDrawable());
			}
		}
		
		
	}
	

}
