package com.froy.magicalitem;

import android.app.Application;

public class MyConstants extends Application {

	
	public static final String CURRENT_SCHEMA_PACKAGE = "com.magicItem.db";
	// TODO Update this to your Android projects base source directory
	public static final String SCHEMA_OUTPUT_DIR = "../MagicalItem/src-gen";
	public static final String MAIN_PACKAGE = "com.froy.magicalitem";
	public static final String DB_PATH = "/data/data/com.froy.magicalitem/databases/";  
	public static final String DB_NAME = "dnd35";
	public static final String CATEGORY_TABLE= "CATEGORIES";
	public static final String ITEM_TABLE= "item";
	public static final String MYITEM_TABLE= "my_items";
	public  static final String ITEM_ID = "_id";
	public static final String ITEM_NAME = "name";
	public static final String ITEM_CATEGORY = "category";
	public static final String ITEM_SPECIAL_ABILITY = "special_ability";
	public static final String ITEM_AURA = "aura";
	public static final String ITEM_CL = "caster_level";
	public static final String ITEM_PRICE = "price";
	public static final String ITEM_PREQ = "prereq";
	public static final String ITEM_COST = "cost";
	public static final String ITEM_FULL_TEXT = "full_text";
	

}
