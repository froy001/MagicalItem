package com.froy.magicalitem;

import com.froy.magicalitem.dao.Categories;
import com.froy.magicalitem.dao.CategoriesDao;
import com.froy.magicalitem.dao.DaoMaster;
import com.froy.magicalitem.dao.DaoMaster.DevOpenHelper;
import com.froy.magicalitem.dao.DaoSession;
import com.froy.magicalitem.dao.MagicalItemsDao;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class ManageCategoriesActivity extends Activity {

	private SQLiteDatabase db;

	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private CategoriesDao categorieseDao;
	private MagicalItemsDao magicItemsDao;
	private Cursor cursor;

	private String textColumn = CategoriesDao.Properties.CategoryName.columnName;
	private String orderBy = textColumn + " ASC";

	Button bAddCategory, bViewCategoryList;
	EditText etCategoryName;
	ListView categories;

	private static final String TAG = "ManageCategoriesActivity.java";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manage_categories);
		com.froy.magicalitem.dao.DaoMaster.DevOpenHelper helper = new DevOpenHelper(
				this, MyConstants.DATABASE_NAME, null);
		db = helper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		categorieseDao = daoSession.getCategoriesDao();
		cursor = db
				.query(categorieseDao.getTablename(),
						categorieseDao.getAllColumns(), null, null, null, null,
						orderBy);
		setVariables();
		setUiListeners();
	}

	private void setVariables() {
		// TODO Auto-generated method stub
		bAddCategory = (Button) findViewById(R.id.bAddCategory);
		bViewCategoryList = (Button) findViewById(R.id.bListCategories);
		etCategoryName = (EditText) findViewById(R.id.etCategoryName);
		categories = (ListView) findViewById(R.id.lvCategoryList);

	}

	private void setUiListeners() {
		// TODO Auto-generated method stub

		// bAddCategory Button
		bAddCategory.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addCategory();

			}
		});
		// bViewCategory Button//
		bViewCategoryList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				fillData();

			}
		});

		// et etCategoryName input listener
		etCategoryName.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				// TODO Auto-generated method stub
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					addCategory();
				}

				return false;
			}
		});

	}

	protected boolean addCategory() {
		// TODO Auto-generated method stub
		int etId = etCategoryName.getId();
		String categoryName = etCategoryName.getText().toString();
		etCategoryName.setText("");
		if (categoryName != null) {
			Categories category = new Categories(null, categoryName);
			categorieseDao.insert(category);
			cursor.requery();
			Log.d(TAG, "Category: " + categoryName + " inserted sucssesfully");
			return true;
		}
		Log.e(TAG,
				"Category not updated. EditText id: " + etCategoryName.getId());
		return false;

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		db.close();
	}

	protected void fillData() {
		// TODO Auto-generated method stub
		startManagingCursor(cursor);
		cursor = db
				.query(categorieseDao.getTablename(),
						categorieseDao.getAllColumns(), null, null, null, null,
						orderBy);
		//cursor.requery();

		String[] from = new String[] { CategoriesDao.Properties.CategoryName.columnName };
		int[] to = { R.id.tvCategryName };
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				R.layout.category_item_layout, cursor, from, to);
		categories.setAdapter(adapter);

	}
}
