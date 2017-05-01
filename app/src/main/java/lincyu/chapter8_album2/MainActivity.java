package lincyu.chapter8_album2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {

	ListView lv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ArrayList<CountyItem> albumlist = new ArrayList<CountyItem>();

		albumlist.add(new CountyItem("台北"));
		albumlist.add(new CountyItem("桃園"));
		albumlist.add(new CountyItem("新竹"));
		albumlist.add(new CountyItem("宜蘭"));
		albumlist.add(new CountyItem("苗栗"));
		albumlist.add(new CountyItem("台中"));
		albumlist.add(new CountyItem("彰化"));
		albumlist.add(new CountyItem("南投"));
		albumlist.add(new CountyItem("花蓮"));
		albumlist.add(new CountyItem("雲林"));
		albumlist.add(new CountyItem("嘉義"));
		albumlist.add(new CountyItem("台南"));
		albumlist.add(new CountyItem("高雄"));
		albumlist.add(new CountyItem("台東"));
		albumlist.add(new CountyItem("屏東"));

		
		AlbumArrayAdapter adapter =
			new AlbumArrayAdapter(this, albumlist);
		
		lv = (ListView)findViewById(R.id.lv);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(itemclick);
	}
	
	OnItemClickListener itemclick = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> av, View v,
				int position, long id) {

			String county = new String();
			String [] titles;
			titles = new String[6];
			titles[0] = "空氣汙染指標(AQI)";
			titles[1] = "空氣汙染物(Pollutant)";
			titles[2] = "風速(m/sec)";
			titles[3] = "風向(degrees)";
			titles[4] = "細懸浮微粒((μg/m3)";
			titles[5] = "資料建置日期(YMD)";

			//之後會有更多那些資料的程式碼，匯入資料後才有的
			switch(position) {
			case 0 :
				county = "台北";
				break;
				case 1 :
					county = "桃園";
					break;
				case 2 :
					county = "新竹";
					break;
				case 3 :
					county = "宜蘭";
					break;
				case 4 :
					county = "苗栗";
					break;
				case 5 :
					county = "台中";
					break;
				case 6 :
					county = "彰化";
					break;
				case 7 :
					county = "南投";
					break;
				case 8 :
					county = "花蓮";
					break;
				case 9 :
					county = "雲林";
					break;
				case 10 :
					county = "嘉義";
					break;
				case 11 :
					county = "台南";
					break;
				case 12 :
					county = "高雄";
					break;
				case 13 :
					county = "台東";
					break;
				case 14 :
					county = "屏東";
					break;

			}
			Intent intent = new Intent();
			intent.setClass(MainActivity.this,
					GridActivity.class);
			intent.putExtra("KEY_COUNTY", county);
			intent.putExtra("KEY_TITLE", titles);
			startActivity(intent);
		}
	};
}
