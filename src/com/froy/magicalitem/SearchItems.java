package com.froy.magicalitem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class SearchItems extends Activity {

	Spinner itemCategory_s;
	Button bCategories;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_items);
		setVariables();
		addUiListeners();

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

	private void setVariables() {
		// TODO Auto-generated method stub
		itemCategory_s = (Spinner) findViewById(R.id.spItemCategory);
		bCategories = (Button) findViewById(R.id.bCategories);
		setSpinner();

	}

	private void setSpinner() {
		// TODO Auto-generated method stub
		// Create array adapter using strig array and default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				SearchItems.this, R.array.planets_array,
				android.R.layout.simple_spinner_item);
		// Specify the layout to use wjen the list of choice appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		itemCategory_s.setAdapter(adapter);
		itemCategory_s.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View v, int pos,
					long id) {
				// TODO Auto-generated method stub
				String position = Integer.toString(pos);
				String selectedItem = itemCategory_s.getSelectedItem()
						.toString();
				Toast.makeText(
						getApplicationContext(),
						"Item Selected Position: " + position + "\n"
								+ "Item Selected: " + selectedItem,
						Toast.LENGTH_LONG).show();

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

}
