package com.froy.magicalitem;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

public class SearchItems extends Activity {

	private SQLiteDatabase db;

	private String orderBy = "category ASC";
	private String mCategorySearchTerm;
	private String mNameSearchTerm;

	Spinner itemCategory_s;
	Button bCategories;
	ImageButton bSearch;
	EditText etItemName, etItemPrice, etCL;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_items);
		SharedPreferences setting=getSharedPreferences("setting", 0);
		SharedPreferences.Editor editor = setting.edit();
		editor.clear();
		editor.commit();
		// Restore UI state from savedInstanceState
		if (savedInstanceState != null) {
			String mItemName = savedInstanceState.getString("Name");
			if (mItemName != null) {
				etItemName = (EditText) findViewById(R.id.etSearchItems_ItemName);
				etItemName.setText(mItemName);
			}
		}

		setVariables();
		setSpinner();
		setSpinnerListener();

		bSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mNameSearchTerm = etItemName.getText().toString();
				Bundle b = new Bundle();
				b.putString("NAME", mNameSearchTerm);
				b.putString("CATEGORY", mCategorySearchTerm);
				Intent i = new Intent("com.froy.magicalitems.SEARCHLIST");
				i.putExtras(b);

				startActivity(i);

			}
		});

	}// ***end onCreate

	private void setVariables() {
		// TODO Auto-generated method stub
		itemCategory_s = (Spinner) findViewById(R.id.spItemCategory);
		bCategories = (Button) findViewById(R.id.bCategories);
		etItemName = (EditText) findViewById(R.id.etSearchItems_ItemName);

		bSearch = (ImageButton) findViewById(R.id.bSearchItem);
		MyDbHelper helper = new MyDbHelper(this);
		db = helper.getWritableDatabase();
	}

	private void setSpinner() {
		// TODO Auto-generated method stub

		Cursor c = db.query(MyConstants.CATEGORY_TABLE, null, null, null, null,
				null, orderBy, null);
		startManagingCursor(c);
		String[] from = new String[] { "category" };

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
				if(textView!=null){
				mCategorySearchTerm = textView.getText().toString();
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		int categoryPosition = itemCategory_s.getSelectedItemPosition();
		String itemName = etItemName.getText().toString();

		outState.putString("Name", itemName);
		outState.putInt("itemCategorySpinnerPosition", categoryPosition);
		super.onSaveInstanceState(outState);

	}

	@Override
	protected void onStop() {
		super.onStop();
		SharedPreferences setting = getSharedPreferences("setting", 0);
		SharedPreferences.Editor editor = setting.edit();
		editor.putString("name", mNameSearchTerm);
		editor.putString("category", mCategorySearchTerm);
		editor.commit();
		db.close();
	}

	@Override
	protected void onStart() {
		super.onStart();
		setVariables();
		setSpinner();
		setSpinnerListener();
		SharedPreferences setting = getSharedPreferences("setting", 0);
		etItemName.setText(setting.getString("name", ""));
		mCategorySearchTerm = setting.getString("category", "");
	}

}
