package lincyu.chapter8_album2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class GridActivity extends ActionBarActivity {

	private TextView county_choose;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grid);

		Intent intent = getIntent();
		String counties = intent.getStringExtra("KEY_COUNTY");
		String [] title = intent.getStringArrayExtra("KEY_TITLE");




		String [] titleList = new String[title.length];
		for (int i = 0; i < title.length; i++) {
			titleList[i] = String.valueOf(title[i]);
		}
		county_choose = (TextView) findViewById(R.id.county_text);
		county_choose.setText(counties);
		county_choose.setHeight(100);
		county_choose.setTextColor(Color.RED);
		GridView gv = (GridView)findViewById(R.id.gv);
		gv.setNumColumns(2);
		gv.setAdapter(new MessegeAdapter(this, titleList));
	}
	
	public class MessegeAdapter extends ArrayAdapter<String> {


		private Context mCtx;

		
		public MessegeAdapter(Context c, String [] titleList) {
			super(c, 0, titleList);
			mCtx = c;
		}

		@Override
		public View getView(int position, View convertView,
				ViewGroup parent) {

			TextView tv = new TextView(mCtx);
			String title = getItem(position);
			tv.setText(title);
			tv.setHeight(500);




			return tv;
		}	
	}
}