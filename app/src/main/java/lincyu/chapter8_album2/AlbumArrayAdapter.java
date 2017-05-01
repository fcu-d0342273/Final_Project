package lincyu.chapter8_album2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class AlbumArrayAdapter extends ArrayAdapter<AlbumItem> {

	Context context;
	
	public AlbumArrayAdapter(Context context,
			ArrayList<AlbumItem> items) {
		super(context, 0, items);
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView,
			ViewGroup parent) {

		LayoutInflater inflater = LayoutInflater.from(context);
		
		LinearLayout itemlayout = null;
		if(convertView == null) {
			itemlayout = (LinearLayout)inflater.inflate
					(R.layout.listitem, null);
		} else {
			itemlayout = (LinearLayout) convertView;
		}
		AlbumItem item = (AlbumItem)getItem(position);
		
		TextView tv_name = (TextView)itemlayout.
				findViewById(R.id.itemtv);
		tv_name.setText(item.name);


		return itemlayout;
	}
}
