package com.froy.magicalitem;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.froy.magicalitem.dao.Categories;
import com.froy.magicalitem.dao.CategoriesDao;
import com.froy.magicalitem.dao.CategoriesDao.Properties;
import com.froy.magicalitem.dao.DaoMaster;
import com.froy.magicalitem.dao.DaoMaster.DevOpenHelper;
import com.froy.magicalitem.dao.DaoSession;

import de.greenrobot.dao.query.QueryBuilder;

public class ManageCategoriesActivity extends Activity {

	private SQLiteDatabase db;

	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private CategoriesDao categorieseDao;
	private Cursor cursor;

	private static final String TAG = "ManageCategoriesActivity.java";
	private String textColumn = CategoriesDao.Properties.CategoryName.columnName;
	private String orderBy = textColumn + " COLLATE LOCALIZED ASC";

	Button bAddCategory, bViewCategoryList, bDeleteCategory;
	EditText etCategoryName;
	ListView categories;

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

		setVariables();
		setUiListeners();
		fillData();
	}

	private void setVariables() {
		// TODO Auto-generated method stub
		bAddCategory = (Button) findViewById(R.id.bAddCategory);
		bViewCategoryList = (Button) findViewById(R.id.bListCategories);
		bDeleteCategory = (Button) findViewById(R.id.bDeleteCategory);
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

		// bDeleteCategories set button to delete item from category list by
		// name

		bDeleteCategory.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				deleteCategory();
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
		
		categories.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				
				final long myId = id;
				
				// alert dialog to confirm deleting a category
				DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
				    @Override
				    public void onClick(DialogInterface dialog, int which) {
				        switch (which){
				        case DialogInterface.BUTTON_POSITIVE:
				            //Yes button clicked - delete teh category
				        	categorieseDao.deleteByKey(myId);
							Log.d(TAG, "Deleted category id: " + myId );
							cursor.requery();
				            break;

				        case DialogInterface.BUTTON_NEGATIVE:
				            //No button clicked
				            break;
				        }
				    }
				};

				AlertDialog.Builder builder = new AlertDialog.Builder(ManageCategoriesActivity.this);
				builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
				    .setNegativeButton("No", dialogClickListener).show();
				
				
				
//				categorieseDao.deleteByKey(id);
//				Log.d(TAG, "Deleted category id: " + id );
//				cursor.requery();
				
			}
		});
		
	

	}
	
	

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if(db!=null){
			db.close();
			daoSession.clear();
		}
		super.onDestroy();
		
	}

	// ///Methods for managing categories

	protected boolean addCategory() {
		// TODO Auto-generated method stub
		cursor = db
				.query(categorieseDao.getTablename(),
						categorieseDao.getAllColumns(), null, null, null, null,
						orderBy);

		String categoryName = etCategoryName.getText().toString();
		etCategoryName.setText("");
		if (categoryName != null) {
			Categories category = new Categories(null, categoryName);
			categorieseDao.insert(category);
			cursor.requery();
			Log.d(TAG, "Category: " + categoryName + " inserted sucssesfully");
			fillData();
			return true;
		}
		Log.e(TAG,
				"Category not updated. EditText id: " + etCategoryName.getId());
		return false;

	}

	protected void fillData() {
		// TODO Auto-generated method stub
		startManagingCursor(cursor);
		Log.d(TAG, "column name: " + textColumn);
		cursor = db
				.query(categorieseDao.getTablename(),
						categorieseDao.getAllColumns(), null, null, null, null,
						orderBy);
		cursor.requery();

		String[] from = new String[] { CategoriesDao.Properties.CategoryName.columnName };
		int[] to = { R.id.tvCategryName };
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				R.layout.category_item_layout, cursor, from, to);
		categories.setAdapter(adapter);

	}

	protected boolean deleteCategory() {
		// TODO Auto-generated method stub
		String et = etCategoryName.getText().toString();
		if (et != null) {
			QueryBuilder qb = categorieseDao.queryBuilder();
			qb.where(Properties.CategoryName.eq(et)).buildDelete()
					.executeDeleteWithoutDetachingEntities();
			daoSession.clear();
			fillData();
			return true;
		}
		return false;
	}

}
