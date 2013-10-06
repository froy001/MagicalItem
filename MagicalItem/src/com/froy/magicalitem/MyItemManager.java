package com.froy.magicalitem;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.Html;
import android.util.Log;

public class MyItemManager {

	private DndDB dbHelper;
	private Context mContext;

	private static final String MYITEM_TABLE = MyConstants.MYITEM_TABLE;
	private static final String ITEM_TABLE = MyConstants.ITEM_TABLE;
	private static final String ID = "_id";
	private static final String MYITEM_ID = "item_id";
	private static final String CHARGES = "charges";
	private static final String ITEM_NAME = "name";
	private static final String ITEM_CATEGORY = "category";
	
	// Select all user inventory items
	//TODO select only the ones that are >0
	private static final String SELECT_MYITEMS = "SELECT " + MYITEM_TABLE + "."
			+ ID + ", " + MYITEM_TABLE + "." + MYITEM_ID + ", " + MYITEM_TABLE
			+ "." + CHARGES + ", " + ITEM_TABLE + "." + ITEM_NAME + ", "
			+ ITEM_TABLE + "." + ITEM_CATEGORY + " FROM " + ITEM_TABLE + ", "
			+ MYITEM_TABLE + " WHERE " + ITEM_TABLE + "." + ID + "="
			+ MYITEM_TABLE + "." + MYITEM_ID +/** " AND " +  MYITEM_TABLE + "."
			+ CHARGES + ">0" + **/ " ;";
	
	private static final String TAG = MyItemManager.class.getSimpleName();

	//Object constructor
	public MyItemManager(Context context) {
		this.mContext = context;
		dbHelper = new DndDB(mContext);
		dbHelper.close();
	}

	public boolean close() {
		dbHelper.close();
		return true;

	}

	/**
	 * Add an Item to your Item inventory
	 * 
	 * @param itemId
	 *            the item ID from the complete list
	 * @param cahrges
	 *            number of charges in item
	 * 
	 * @return success or fail
	 */

	public boolean addToInventory(Long itemId, int charges) {
		
		SQLiteDatabase sqlite = dbHelper.getWritableDatabase();

		ContentValues initialValues = new ContentValues();

		initialValues.put(MYITEM_ID, itemId);
		initialValues.put(CHARGES, charges);
		
		try {
			sqlite.insert(MYITEM_TABLE, null, initialValues);
			
			} catch (SQLException sqlerror) {
			Log.v("Insert into table error", sqlerror.getMessage());

			return false;
		}
		finally {
			sqlite.close();
		}
		return true;

	}
	

	/**
	 * Fire one charge of the item
	 * 
	 * @param myItemId
	 *            the inventory item id (my_items._id)
	 * @param cahrges
	 *            number of charges in item
	 * 
	 * @return success or fail
	 */

	public boolean fireCharge(MyItem mItem) {
		Long mId = mItem.getId();
		int mCharges = mItem.getItemCharges();
		SQLiteDatabase sqlite = null;
		Log.d("Inside fireCharge", "Item initial # of charges: "+mCharges);
		mCharges -= 1;
		Log.d("Inside fireCharge", "Item current # of charges: "+mCharges);
		String whereClause = ID + "=" + mId;
		
		sqlite = dbHelper.getWritableDatabase();

		ContentValues updatedValues = new ContentValues();

		//updatedValues.put(ID, mId);
		updatedValues.put(CHARGES, mCharges);

		try {
			sqlite.beginTransaction();

			sqlite.update(MYITEM_TABLE, updatedValues, whereClause, null);
			sqlite.setTransactionSuccessful();
			Log.d("db update", "DB updated");
		} catch (SQLException e) {
			Log.w("Update table error", e.getMessage());
			return false;
		} finally {
			if(sqlite !=null)
			sqlite.endTransaction();
			
			Log.d("db update", "DB updated sucesfully");

		}
		if(sqlite !=null)
		sqlite.close();
		
		return true;
	}

	/**
	 * Get all available items in inventory
	 * 
	 * @return List of items in inventory
	 */
	public ArrayList<MyItem> getItems() {
		ArrayList<MyItem> mItems = new ArrayList<MyItem>();
		SQLiteDatabase sqliteDB = dbHelper.getReadableDatabase();
		Cursor crsr = sqliteDB.rawQuery(SELECT_MYITEMS, null);
		Log.d(TAG, "Loaded cursor");
		Log.i(TAG, SELECT_MYITEMS);

		crsr.moveToFirst();

		while (!crsr.isAfterLast()) {
			mItems.add(new MyItem(crsr.getLong(crsr.getColumnIndex(ID)), crsr
					.getLong(crsr.getColumnIndex(MYITEM_ID)), crsr.getInt(crsr
					.getColumnIndex(CHARGES)), crsr.getString(crsr
					.getColumnIndex(ITEM_NAME)), crsr.getString(crsr
					.getColumnIndex(ITEM_CATEGORY))));
			crsr.moveToNext();
		}

		dbHelper.close();
		crsr.close();
		return mItems;
	}
	
	public boolean deleteItem(Long id){

		//TODO needs a delete function to delete rows from db
		Long mId = id;
		SQLiteDatabase sqlite = null;
		String whereClause = ID + "=" + mId;
		sqlite = dbHelper.getWritableDatabase();
		
		try {
			sqlite.beginTransaction();
			sqlite.delete(MYITEM_TABLE, whereClause, null);
			sqlite.setTransactionSuccessful();
			Log.d("db update", "DB updated");
		} catch (SQLException e) {
			Log.w("Update table error", e.getMessage());
			return false;
		} finally {
			if(sqlite !=null)
			sqlite.endTransaction();
			
			Log.d("db update", "DB updated sucesfully");
		}
		
		if(sqlite !=null) sqlite.close();
		
		return true;
	}
	
	public boolean deleteEmptyItems(){
		
		String whereClause = MYITEM_TABLE+"."+CHARGES + "<=" + 0;
		SQLiteDatabase sqlite = null;
		sqlite = dbHelper.getWritableDatabase();
		
		try{
			sqlite.beginTransaction();
			int rowsDeleted = sqlite.delete(MYITEM_TABLE, whereClause, null);
			sqlite.setTransactionSuccessful();
			Log.d("deleteEmpty", "after deleteion, "+rowsDeleted +" Rows deleted");
			
		}finally{
			if(sqlite !=null)
				sqlite.endTransaction();
				
				Log.d("deleteEmptyItems", "DB updated sucesfully");
		}

		return true;
	}

	/**
	 * Strips html for editing purposes(the full_text field is html)
	 * @param html - String
	 * @return - A string clean from html tags
	 */
	public String stripHtml(String html) {
		
		return Html.fromHtml(html).toString();
	}
}
