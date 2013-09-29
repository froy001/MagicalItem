package com.froy.magicalitem;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.Duration;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ItemsListActivity extends ListActivity {

	private static final String TAG = "ItemsListActivity.java";
	private ItemAdapter tableRowAdapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try {

			ItemManager itemMgr = new ItemManager(getApplicationContext());

			// get the values from database
			final ArrayList<Item> items = itemMgr.getItems();

			tableRowAdapter = new ItemAdapter(this,
					R.layout.item_list, items);
			Log.i(TAG, "Number of items in adapter: "+ tableRowAdapter.getCount());
			setListAdapter(tableRowAdapter);
			getListView().setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					Item mItem = items.get(arg2);
					Intent mIntent = new Intent("com.froy.magicalitems.ITEMDETAIL");
					mIntent.putExtra("ITEM_ID", mItem.getId());
					mIntent.putExtra("ITEM_DETAILS", mItem.getFullText());
					startActivity(mIntent);
					
					Log.i(TAG, "item id is " + mItem.getId() + "the item name is: " + mItem.getName());
					//Log.i(TAG, "Id is " + arg3);
					//Log.i(TAG, "Id is" + items);
					
				}
			});
			

		} catch (Exception ex) {
			Log.e(ex.toString(), ex.toString());

		}
		
	}

	

}