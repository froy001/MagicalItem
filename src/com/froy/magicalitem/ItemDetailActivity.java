package com.froy.magicalitem;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ItemDetailActivity extends Activity {
	Button bAddItem;
	TextView tvItemDetails;
	private Long itemId;
	private String itemDesc;
	private final String TAG = "com.from.magicalitems.ItemDetail";
	private MyItem mItem;
	private MyItemManager mItemManager = new MyItemManager(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_details);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			itemId = extras.getLong("ITEM_ID");
			itemDesc = extras.getString("ITEM_DETAILS");

		}
		tvItemDetails = (TextView) findViewById(R.id.tvItemDesctiption);
		tvItemDetails.setText(Html.fromHtml(itemDesc));
		mItem = new MyItem((Long) null, itemId, -1, null, null);
		bAddItem = (Button) findViewById(R.id.bAddItemToInventory);
		bAddItem.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/dungeon.ttf"));
		bAddItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyItemAdapter.enterCharges(ItemDetailActivity.this, mItem);
				

			}

		});

	}
}
