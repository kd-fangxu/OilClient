package com.oil.adapter;

/**
 * Author: alex askerov
 * Date: 9/9/13
 * Time: 10:52 PM
 */

import java.util.List;

import org.askerov.dynamicgrid.BaseDynamicGridAdapter;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oilclient.R;

/**
 * Author: alex askerov Date: 9/7/13 Time: 10:56 PM
 */
public class CheeseDynamicAdapter extends BaseDynamicGridAdapter {
	Context context;

	public CheeseDynamicAdapter(Context context, List<?> items, int columnCount) {
		super(context, items, columnCount);
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CheeseViewHolder holder;
		if (convertView == null) {
			// convertView = LayoutInflater.from(getContext()).inflate(
			// R.layout.item_userfoucecontrol, null);
			convertView = View.inflate(context, R.layout.item_userfoucecontrol,
					null);
			holder = new CheeseViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (CheeseViewHolder) convertView.getTag();
		}
		holder.build(getItem(position).toString());
		return convertView;
	}

	private class CheeseViewHolder {
		private TextView titleText;
		private ImageView image;

		private CheeseViewHolder(View view) {
			titleText = (TextView) view.findViewById(R.id.tv_userfouce_name);
			image = (ImageView) view.findViewById(R.id.iv_userfouce_cancel);
		}

		void build(String title) {
			titleText.setText(title);
			image.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(getContext(), "onivclick", 1).show();
				}
			});
			//
			// image.setImageResource(R.drawable.ic_launcher);
		}
	}
}