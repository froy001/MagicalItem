package com.froy.magicalitem;

import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	Button bItemList, bMyItems, bSpellList, bSearchItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bItemList = (Button) findViewById(R.id.bItemlist);
		createDB();
		bItemList.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/dungeon.ttf"));

		// Button export intitalize
		bMyItems = (Button) findViewById(R.id.bMyItems);
		bMyItems.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/dungeon.ttf"));
		
		bSearchItem = (Button) findViewById(R.id.bSearchItems);
		bSearchItem.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/dungeon.ttf"));
		

	}
	
	public void mainClickMethod(View v){
		switch (v.getId()){
		case R.id.bItemlist:
			Intent i = new Intent("com.froy.magicalitems.LISTITEMS");
			startActivity(i);
			break;
		case R.id.bMyItems:
			Intent i1 = new Intent("com.froy.magicalitems.MYITEMLIST");
			startActivity(i1);
			
			break;
		case R.id.bSearchItems:
			Intent i2 = new Intent("com.froy.magicalitems.SEARCH");
			startActivity(i2);
			
			break;
		
		
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void createDB() {
		// TODO Auto-generated method stub
		MyDbHelper db = new MyDbHelper(getBaseContext());
		// creats a db in data/data/packageName/databases from the DB file in
		// /assets
		try {
			db.createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
