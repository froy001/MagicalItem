package com.froy.magicalitem;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.Html;
import android.util.Log;

public class ItemManager {
	private DndDB dbHelper;

	private static final String ITEM_TABLE = MyConstants.ITEM_TABLE;
	private static final String ITEM_ID = "_id";
	private static final String ITEM_NAME = "name";
	private static final String ITEM_CATEGORY = "category";
	private static final String ITEM_SPECIAL_ABILITY = "special_ability";
	private static final String ITEM_AURA = "aura";
	private static final String ITEM_CL = "caster_level";
	private static final String ITEM_PRICE = "price";
	private static final String ITEM_PREQ = "prereq";
	private static final String ITEM_COST = "cost";
	private static final String ITEM_FULL_TEXT = "full_text";

	private static final String SELECT_ITEM = "SELECT " + ITEM_ID + ", "
			+ ITEM_NAME + ", " + ITEM_CATEGORY + ", " + ITEM_SPECIAL_ABILITY
			+ ", " + ITEM_AURA + ", " + ITEM_CL + ", " + ITEM_PRICE + ", "
			+ ITEM_PREQ + ", " + ITEM_COST + ", " + ITEM_FULL_TEXT + " FROM "
			+ ITEM_TABLE + " ORDER BY " + ITEM_NAME + " ;";
	private static final String SELECT_ITEM_BY_ID_1 = "SELECT " + ITEM_ID + ", "
			+ ITEM_NAME + ", " + ITEM_CATEGORY + ", " + ITEM_SPECIAL_ABILITY
			+ ", " + ITEM_AURA + ", " + ITEM_CL + ", " + ITEM_PRICE + ", "
			+ ITEM_PREQ + ", " + ITEM_COST + ", " + ITEM_FULL_TEXT + " FROM "
			+ ITEM_TABLE +" WHERE _id = ";
	private static final String SELECT_ITEM_BY_ID_2 =" ORDER BY " + ITEM_NAME + " ;";

	private static final String TAG = "ItemManager.java";

	public ItemManager(Context context) {
		dbHelper = new DndDB(context);
		dbHelper.close();
	}

	public boolean close() {
		dbHelper.close();
		return true;

	}

	/**
	 * Insert an Item to the item list
	 * 
	 * @param itemName
	 *            the name of the Item
	 * @param category
	 *            description of item category
	 * @param special_ability
	 * 			does the item have special ability(Yes/No)
	 * @param aura
	 * 			what is the item's aura(None/(Strong, Moderate/Faint)+magic school)
	 * @param caster_level
	 * 			minimum caster level to create this item
	 * @param price
	 * 			the item's market price
	 * @param prereq
	 * 			Prerequisites to create item
	 * @param cost
	 * 			gp+xp cost
	 * @param full_text
	 * 			the complete description of the item 
	 * 
	 * @return success or fail name, category, special_ability, aura,
	 *         caster_level, price, prereq, cost
	 */
	public boolean insert(String itemName, String category,
			String special_ability, String aura, String caster_level,
			String price, String prereq, String cost, String full_text) {

		try {

			SQLiteDatabase sqlite = dbHelper.getWritableDatabase();

			ContentValues initialValues = new ContentValues();

			initialValues.put(ITEM_NAME, itemName);
			initialValues.put(ITEM_CATEGORY, category);
			initialValues.put(ITEM_SPECIAL_ABILITY, special_ability);
			initialValues.put(ITEM_AURA, aura);
			initialValues.put(ITEM_CL, caster_level);
			initialValues.put(ITEM_PRICE, price);
			initialValues.put(ITEM_PREQ, prereq);
			initialValues.put(ITEM_COST, cost);
			initialValues.put(ITEM_FULL_TEXT, full_text);

			sqlite.insert(ITEM_TABLE, null, initialValues);

		} catch (SQLException sqlerror) {

			Log.v("Insert into table error", sqlerror.getMessage());

			return false;
		}

		return true;

	}

	/**
	 * Get all available Items
	 * 
	 * @return List of Items in complete item list
	 */
	public ArrayList<Item> getItems() {
		ArrayList<Item> items = new ArrayList<Item>();

		SQLiteDatabase sqliteDB = dbHelper.getReadableDatabase();
		Log.d(TAG, "loading cursor");
		Cursor crsr = sqliteDB.rawQuery(SELECT_ITEM, null);
		Log.d(TAG, "Loaded cursor");

		crsr.moveToFirst();
		while (!crsr.isAfterLast()) {
			// Log.d(TAG, "start for loop");
			items.add(new Item(crsr.getLong(crsr.getColumnIndex(ITEM_ID)), crsr
					.getString(crsr.getColumnIndex(ITEM_NAME)), crsr
					.getString(crsr.getColumnIndex(ITEM_CATEGORY)), crsr
					.getString(crsr.getColumnIndex(ITEM_SPECIAL_ABILITY)), crsr
					.getString(crsr.getColumnIndex(ITEM_AURA)), crsr
					.getString(crsr.getColumnIndex(ITEM_CL)), crsr
					.getString(crsr.getColumnIndex(ITEM_PRICE)), crsr
					.getString(crsr.getColumnIndex(ITEM_PREQ)), crsr
					.getString(crsr.getColumnIndex(ITEM_COST)), crsr
					.getString(crsr.getColumnIndex(ITEM_FULL_TEXT))));

			crsr.moveToNext();
			// Log.d(TAG, "crsr moved next");

		}
		Log.d(TAG, "ended for loop");
		dbHelper.close();
		crsr.close();
		return items;
	}
	
	/**
	 * Get Item
	 * 
	 * @return Item
	 */
	public Item getItem(Long id){
		Long mId = id;
		SQLiteDatabase sqliteDB = dbHelper.getReadableDatabase();
		Cursor crsr = sqliteDB.rawQuery(SELECT_ITEM_BY_ID_1+mId+SELECT_ITEM_BY_ID_2, null);
		Log.i("crsr"," The cursor has "+ crsr.getCount()+ " rows");
		Item item = new Item();
		if (crsr!=null){
			item.setId(crsr.getLong(crsr.getColumnIndex(ITEM_ID)));
			item.setName(crsr.getString(crsr.getColumnIndex(ITEM_NAME)));
			item.setCategory(crsr.getString(crsr.getColumnIndex(ITEM_CATEGORY)));
			item.setSpecialAbility(crsr.getString(crsr.getColumnIndex(ITEM_SPECIAL_ABILITY)));
			item.setAura(crsr.getString(crsr.getColumnIndex(ITEM_AURA)));
			item.setCasterLevel(crsr.getString(crsr.getColumnIndex(ITEM_CL)));
			item.setPrice(crsr.getString(crsr.getColumnIndex(ITEM_PRICE)));
			item.setPreReq(crsr.getString(crsr.getColumnIndex(ITEM_PREQ)));
			item.setCost(crsr.getString(crsr.getColumnIndex(ITEM_COST)));
			item.setFullText(crsr.getString(crsr.getColumnIndex(ITEM_FULL_TEXT)));
		}
		return item;
		
		
		
	}
	

	/**
	 * Strip HTML
	 */
	public String stripHtml(String html) {
		return Html.fromHtml(html).toString();
	}
}
