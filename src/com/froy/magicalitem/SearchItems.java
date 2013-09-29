package com.froy.magicalitem;

import java.io.IOException;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SearchItems extends Activity {

	private SQLiteDatabase db;

	private String orderBy = "category ASC";

	Spinner itemCategory_s;
	Button bCategories;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// db create
		createDB();
		setContentView(R.layout.search_items);
		setVariables();
		addUiListeners();
		setSpinner();
		setSpinnerListener();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		db.close();
	}

	private void setVariables() {
		// TODO Auto-generated method stub
		itemCategory_s = (Spinner) findViewById(R.id.spItemCategory);
		bCategories = (Button) findViewById(R.id.bCategories);
		MyDbHelper helper = new MyDbHelper(this);
		db = helper.getWritableDatabase();
	}

	private void addUiListeners() {
		// TODO Auto-generated method stub
		bCategories.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent("com.froy.magicalitems.MANAGECATEGORY");
				startActivity(i);

			}
		});

	}

	private void setSpinner() {
		// TODO Auto-generated method stub

		Cursor c = db.query(MyConstants.CATEGORY_TABLE, null, null, null, null,
				null, orderBy, null);
		startManagingCursor(c);
		String[] from = new String[] {"category"};

		// create an array of the display item we want to bind our data to
		int[] to = new int[] { android.R.id.text1 };
		// create simple cursor adapter
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_spinner_item, c, from, to);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// get reference to our spinner
		itemCategory_s = (Spinner) findViewById(R.id.spItemCategory);
		itemCategory_s.setAdapter(adapter);
		db.close();
		

	}

	private void setSpinnerListener() {
		// TODO Auto-generated method stub
		itemCategory_s.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long id) {
				// TODO Auto-generated method stub
				TextView textView = (TextView) itemCategory_s.getSelectedView();
				String result = textView.getText().toString();
				String message = result + " was selected. ID : " + id;
				Toast.makeText(SearchItems.this, message, Toast.LENGTH_LONG)
						.show();

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

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
