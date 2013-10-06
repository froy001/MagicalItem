package com.froy.magicalitem;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.froy.magicalitem.R.color;
import com.qustom.dialog.FroyQustomDialogBuilder;
import com.qustom.dialog.QustomDialogBuilder;

/**
 * <h1>MyItemAdapter</h1>
 * @author David Fradis
 * 
 * List to View adapter
 *
 */

public class MyItemAdapter extends BaseAdapter implements OnClickListener {

	private static List<MyItem> rows;
	private static Context mContext;
	private ViewHolder holder;
	private final String TAG = MyItemAdapter.class.getSimpleName();
	private final String CHARGES_MESSAGE = "charges left: ";

	/**
	 * 
	 * @param context - The relevant context
	 * @param itemResId - the layout for the list
	 * @param items - the items to be displayeds
	 */
	public MyItemAdapter(final Context context, final int itemResId,
			final List<MyItem> items) {
		super();

		MyItemAdapter.rows = items;
		MyItemAdapter.mContext = context;

	}

	public int getCount() {
		return MyItemAdapter.rows.size();

	}

	public Object getItem(int position) {
		return MyItemAdapter.rows.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	/**
	 * Set the content for a row here
	 */
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater mInflater = LayoutInflater.from(parent.getContext());

		if (convertView == null) {

			convertView = mInflater.inflate(
					R.layout.my_items_list_layout_linear, null);

			holder = new ViewHolder();
			holder.position = position;
			holder.name = (TextView) convertView
					.findViewById(R.id.tvMyItemName);
			holder.name.setTypeface(Typeface.createFromAsset(
					mContext.getAssets(), "fonts/dungeon.ttf"));
			holder.charges = (TextView) convertView
					.findViewById(R.id.tvCharges);
			holder.charges.setTypeface(Typeface.createFromAsset(
					mContext.getAssets(), "fonts/dungeon.ttf"));
			holder.bFire = (Button) convertView.findViewById(R.id.bFire);
			holder.bFire.setOnClickListener(MyItemAdapter.this);
			holder.bFire.setTag(position);
			holder.bDelete = (Button) convertView.findViewById(R.id.bDelete);
			holder.bFire.setOnClickListener(MyItemAdapter.this);
			holder.bDelete.setTag(position);

			convertView.setTag(holder);

		} else {

			holder = (ViewHolder) convertView.getTag();

		}
		holder.bFire = (Button) convertView.findViewById(R.id.bFire);
		holder.bFire.setOnClickListener(MyItemAdapter.this);
		holder.bFire.setTag(position);
		MyItem row = MyItemAdapter.rows.get(position);
		holder.name.setText(row.getName());
		holder.name.setTypeface(Typeface.createFromAsset(mContext.getAssets(),
				"fonts/dungeon.ttf"));
		holder.charges.setTypeface(Typeface.createFromAsset(
				mContext.getAssets(), "fonts/dungeon.ttf"));
		holder.charges.setText(CHARGES_MESSAGE + row.getItemCharges());
		holder.bDelete = (Button) convertView.findViewById(R.id.bDelete);
		holder.bDelete.setOnClickListener(MyItemAdapter.this);
		holder.bDelete.setTag(position);

		return convertView;

	}

	public void refresh(final List<MyItem> newList) {
		
		MyItemAdapter.rows = newList;

		if (rows.isEmpty())
			Log.d("refresh", "rows is empty");
		super.notifyDataSetChanged();
	}

	/**
	 * Delete a row from the list of rows
	 * 
	 * @param row
	 *            row to be deleted
	 */
	public void deleteRow(MyItem row) {

		if (MyItemAdapter.rows.contains(row)) {
			MyItemAdapter.rows.remove(row);
		}

	}

	static class ViewHolder {
		TextView id, itemId, name, category, charges, cost;
		Button bDelete, bFire;

		int position;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		final int position = (Integer) v.getTag();
		final MyItem mItem = rows.get(position);
		final MyItemManager mngr = new MyItemManager(MyItemAdapter.mContext);
		Vibrator vibe = (Vibrator) mContext
				.getSystemService(Context.VIBRATOR_SERVICE);
		MediaPlayer mp = MediaPlayer.create(this.mContext, R.raw.explosion_2);
		Log.d("MyItemAdapter.onClick", "Item id: " + mItem.getId()
				+ "\n Item Name :" + mItem.getName() + "mItem charges: "
				+ mItem.getItemCharges());
		Log.i("onclick:", "position is: " + v.getTag());
		switch (v.getId()) {
		case R.id.bFire:
			if (mItem.getItemCharges() > 0) {
				Log.d("MyItemAdapter.onClick", "bFire Clicked");
				mp.start();
				vibe.vibrate(100);
				mngr.fireCharge(mItem);
				rows.get(position).setItemCharges(mItem.getItemCharges() - 1);
				mngr.close();
				refresh(rows);
			} else if (mItem.getItemCharges() <= 0) {
				Log.d("MyItemAdapter", "Item Charges <=0");
				QustomDialogBuilder alert = new QustomDialogBuilder(mContext);
				alert.setTitle("No More Charges!!");
				alert.setMessage("Do you want to reload the item?");
				alert.setTitleColor("#ff0101");
				alert.setDividerColor("#ff0101");
				alert.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// Add charges
								enterCharges(mContext, mItem);
							}
						});

				alert.setNegativeButton("No",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// Canceled.

							}
						});
				final AlertDialog dialog = alert.create();
				dialog.show();

			}

			break;

		case R.id.bDelete:
			Log.d("MyItemAdapter.onClick", "bDelete Clicked");
			QustomDialogBuilder alert = new QustomDialogBuilder(
					MyItemAdapter.mContext);
			alert.setTitle("Confirm Item Deletion");
			alert.setMessage("Are you sure you want to delete item ?");
			alert.setTitleColor("#ff0101");
			alert.setDividerColor("#ff0101");
			alert.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							// Add charges
							mngr.deleteItem(mItem.getId());
							rows.remove(position);
							refresh(rows);
						}
					});

			alert.setNegativeButton("No",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							// Canceled.

						}
					});
			final AlertDialog dialog = alert.create();
			dialog.show();

			break;

		}

	}

	// add charges to item.

	public static boolean enterCharges(final Context context, final MyItem item) {
		
		// TODO Fix the charges so that items with no chrges are writen without popping altertDialog
		
		QustomDialogBuilder alert = new QustomDialogBuilder(context);
		final MyItemManager mItemManager = new MyItemManager(context);
		final MyItem mItem = item;
		boolean result = false;
		alert.setTitle("Test this title");
		alert.setMessage(R.string.add_item_dialog_message);
		alert.setTitleColor("#ff0101");
		alert.setDividerColor("#ff0101");

		// Set an EditText view to get user input
		final EditText input = new EditText(context);
		input.setInputType(InputType.TYPE_CLASS_NUMBER);
		alert.addViewToLinearLayout(input, R.id.contentPanel);
		
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				
				// make sure that the input is not empty
				if (input.getText().length() > 0) {
					String value = input.getText().toString();
					
					// check that input is not zero
					if (value.equals("0")) {
						showCancelAlert(context, "Don't use Zero",
								"You cant have zero charges!!! who are you kidding?");

					} else {
						int charges = Integer.parseInt(value);
						mItem.setItemCharges(charges);
						mItemManager.addToInventory(mItem.getItemId(),
								mItem.getItemCharges());
						Log.i("MyItemAdapter", "Added this to DB");
						Intent i = new Intent(context, MyItemsActivity.class);
						context.startActivity(i);
					}
				}
			}
		});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.
					}
				});

		//open the numbers input UI so that the input type is clearer
		final AlertDialog dialog = alert.create();
		input.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				
				if (hasFocus) {
					dialog.getWindow()
							.setSoftInputMode(
									WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
				}
			}
		});

		dialog.show();
		input.requestFocus();
		mItemManager.close();
		result = true;
		return result;

	}

	private static void showCancelAlert(Context mContext, String title,
			String message) {

		AlertDialog.Builder alert = new AlertDialog.Builder(mContext);

		alert.setTitle(title);
		alert.setMessage(message);
		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.
					}
				});
		alert.create();
		alert.show();

	}

}
