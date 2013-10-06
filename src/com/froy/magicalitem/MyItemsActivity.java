package com.froy.magicalitem;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MyItemsActivity extends Activity {
	private static final String TAG = "MyItemsActivity.java";
	private MyItemAdapter tableRowAdapter;
	ArrayList<MyItem> myItems;
	MyItemManager itemMgr;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.my_items_layout);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		ListView mListView = (ListView) findViewById(R.id.lvMyItems);
		Button bAddItem = (Button) findViewById(R.id.myItemsLayout_bAddItems);
		TextView tvAddItem=(TextView) findViewById(R.id.myItemsLayout_tvAddItem);
		tvAddItem.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/dungeon.ttf"));
		itemMgr = new MyItemManager(getApplicationContext());

		// get the values from database\
		itemMgr.deleteEmptyItems();
		myItems = itemMgr.getItems();
		if (bAddItem.equals(null))
			Log.i("MyItemsActivity", "Button is null");
		bAddItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent i = new Intent("com.froy.magicalitems.LISTITEMS");
				startActivity(i);

			}
		});

		try {
			tableRowAdapter = new MyItemAdapter(this,
					R.layout.my_items_list_layout_linear, myItems);
			mListView.setAdapter(tableRowAdapter);
		} catch (Exception ex) {
			Log.e(ex.toString(), ex.toString());
		}
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case android.R.id.home:
			Intent homeIntent = new Intent(this, MainActivity.class);
			homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(homeIntent);
		}
			
			
		return (super.onOptionsItemSelected(item));
	}
}
