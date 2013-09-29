package com.froy.magicalitem;

import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Message;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ItemAdapter extends BaseAdapter {

	private final List<Item> rows;
	private final Context mContext;
	
	private final String TAG = "com.froy.magicalitem.ItemAdapter";

	public ItemAdapter(final Context context, final int itemResId,
			final List<Item> items) {

		this.rows = items;
		this.mContext = context;
	}

	public int getCount() {
		return this.rows.size();

	}

	public Object getItem(int position) {
		return this.rows.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	/**
	 * Set the content for a row here
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		Item row = this.rows.get(position);
		LayoutInflater mInflater = (LayoutInflater) parent.getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		ViewHolder holder;
		
		if (convertView == null) {

			convertView = mInflater.inflate(R.layout.custom_item_list_layout, null);
			
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.tvItemName);
			holder.name.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/dungeon.ttf"));
			holder.casterLevel=(TextView) convertView.findViewById(R.id.tvCL);
			holder.casterLevel.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/dungeon.ttf"));
			
			convertView.setTag(holder);

		}else{
			
			holder = (ViewHolder) convertView.getTag();
			
		}
		holder.name.setText(row.getName());
		holder.name.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/dungeon.ttf"));
		holder.casterLevel.setText("CL: " + row.getCasterLevel());
		holder.casterLevel.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/dungeon.ttf"));
		
		return convertView;

	}

	/**
	 * Delete a row from the list of rows
	 * 
	 * @param row
	 *            row to be deleted
	 */
	public void deleteRow(Item row) {

		if (this.rows.contains(row)) {
			this.rows.remove(row);
		}
	}
	static class ViewHolder{
		TextView name, casterLevel;
	}

}
