package com.froy.magicalitem;

import com.froy.magicalitem.dao.CategoriesDao;
import com.froy.magicalitem.dao.DaoMaster;
import com.froy.magicalitem.dao.DaoSession;
import com.froy.magicalitem.dao.DaoMaster.DevOpenHelper;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
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

	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private CategoriesDao categorieseDao;

	private static final String TAG = "ManageCategoriesActivity.java";
	private String textColumn = CategoriesDao.Properties.CategoryName.columnName;
	private String orderBy = textColumn + " COLLATE LOCALIZED ASC";

	Spinner itemCategory_s;
	Button bCategories;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_items);
		setVariables();
		addUiListeners();
		setSpinner();
		setSpinnerListener();

	}

	private void setVariables() {
		// TODO Auto-generated method stub
		itemCategory_s = (Spinner) findViewById(R.id.spItemCategory);
		bCategories = (Button) findViewById(R.id.bCategories);
		DevOpenHelper helper = new DevOpenHelper(this,
				MyConstants.DATABASE_NAME, null);
		db = helper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		categorieseDao = daoSession.getCategoriesDao();

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
		Cursor c = db
				.query(categorieseDao.getTablename(),
						categorieseDao.getAllColumns(), null, null, null, null,
						orderBy);
		startManagingCursor(c);

		// create an array to specify which fields we want to display
		String[] from = new String[] { CategoriesDao.Properties.CategoryName.columnName };
		// create an array of the display item we want to bind our data to
		int[] to = new int[] { android.R.id.text1 };
		// create simple cursor adapter
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_spinner_item, c, from, to);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// get reference to our spinner
		itemCategory_s = (Spinner) findViewById(R.id.spItemCategory);
		itemCategory_s.setAdapter(adapter);

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

}
