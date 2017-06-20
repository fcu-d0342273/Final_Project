package lincyu.chapter8_album2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.os.Handler;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

	ListView lv;
    List<CountyItem> lsIm = new ArrayList<>();
    private ImArrayAdapter adapter = null;

    private static final int LIST_IM = 1;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LIST_IM: {
                    List<CountyItem> ims = (List<CountyItem>)msg.obj;
                    refreshImList(ims);
                    break;
                }
            }
        }
    };

    private void refreshImList(List<CountyItem> im) {
        adapter.clear();
        adapter.addAll(im);

    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);





	/*	albumlist.add(new CountyItem("台北"));
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
		albumlist.add(new CountyItem("屏東"));*/

		
		//TitleArrayAdapter adapter =
			//new TitleArrayAdapter(this, albumlist);
		
		/*lv = (ListView)findViewById(R.id.lv);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(itemclick);*/


        adapter = new ImArrayAdapter(this, new ArrayList<CountyItem>());
        ListView lvIms = (ListView)findViewById(R.id.lv);
        lvIms.setAdapter(adapter);
        getImFromFirebase();
        lvIms.setOnItemClickListener(itemclick);



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
			intent.setClass(MainActivity.this, GridActivity.class);
			intent.putExtra("KEY_COUNTY", county);
			intent.putExtra("KEY_TITLE", titles);

			startActivity(intent);
		}
	};




    class FirebaseThread extends Thread {

        private DataSnapshot dataSnapshot;

        public FirebaseThread(DataSnapshot dataSnapshot) {
            this.dataSnapshot = dataSnapshot;
        }


      @Override
        public void run() {
          ArrayList<CountyItem> albumlist = new ArrayList<CountyItem>();
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                DataSnapshot dsWs = ds.child("WindSpeed");
                DataSnapshot dsWd = ds.child("WindDirec");
                DataSnapshot dsPt = ds.child("PublishTime");
                DataSnapshot dsPoll = ds.child("MajorPollutant");
                DataSnapshot dsPsi = ds.child("PSI");
                DataSnapshot dsPm = ds.child("PM25");
                DataSnapshot dsCo = ds.child("County");

                String windSpeed = (String) dsWs.getValue();
                String windDir = (String) dsWd.getValue();
                String pTime = (String) dsPt.getValue();
                String pollutant = (String) dsPoll.getValue();
                String psi = (String) dsPsi.getValue();
                String pm25 = (String) dsPm.getValue();
                String county = (String) dsCo.getValue();

                CountyItem im = new CountyItem("");
                im.setCounty(county);
                im.setWindDir(windDir);
                im.setWindSpeed(windSpeed);
                im.setpTime(pTime);
                im.setPollutant(pollutant);
                im.setPsi(psi);
                im.setPm25(pm25);
                albumlist.add(im);
                Log.v("Test", county + " : "  + pTime + " ; " + windSpeed + " ; " + windDir + " ; " + pollutant + " ; " + psi + " ; " + pm25);

                Message msg = new Message();
                msg.what = LIST_IM;
                msg.obj = albumlist;
                handler.sendMessage(msg);
            }

        }

    }



    private  void getImFromFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

              /*  for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    DataSnapshot dsWs = ds.child("WindSpeed");
                    DataSnapshot dsWd = ds.child("WindDirec");
                    DataSnapshot dsPt = ds.child("PublishTime");
                    DataSnapshot dsPoll = ds.child("MajorPollutant");
                    DataSnapshot dsPsi = ds.child("PSI");
                    DataSnapshot dsPm = ds.child("PM25");
                    DataSnapshot dsCo = ds.child("County");

                    String windSpeed = (String) dsWs.getValue();
                    String windDir = (String) dsWd.getValue();
                    String pTime = (String) dsPt.getValue();
                    String pollutant = (String) dsPoll.getValue();
                    String psi = (String) dsPsi.getValue();
                    String pm25 = (String) dsPm.getValue();
                    String county = (String) dsCo.getValue();

                    CountyItem aCounty = new CountyItem("");
                    aCounty.setCounty(county);
                    aCounty.setPollutant(pollutant);
                    aCounty.setpTime(pTime);
                    aCounty.setPm25(pm25);
                    aCounty.setPsi(psi);
                    aCounty.setWindDir(windDir);
                    aCounty.setWindSpeed(windSpeed);
                    lsIm.add(aCounty);
                    System.out.println("TEST12345678 : " + lsIm.isEmpty());
                    Log.v("Test", county + " : " + pTime + " ; " + windSpeed + " ; " + windDir + " ; " + pollutant + " ; " + psi + " ; " + pm25);
                    Message msg = new Message();
                    msg.what = LIST_IM;
                    msg.obj = lsCounty;
                    //handler.sendMessage(msg);
                }*/
              new FirebaseThread(dataSnapshot).start();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v("Imformation", databaseError.getMessage());
            }
        });
       // System.out.println("TEST999999 : " + lsIm.isEmpty());
        //return lsIm;

    }

    private CountyItem getCounty(String county) {
        CountyItem trueCounty = null;
        for(int i=0;i<14;i++) {
            System.out.println("SUCCESS : " + lsIm.isEmpty());
            if(lsIm.get(i).getCounty().equals(county)) {
                trueCounty = lsIm.get(i);
            }
        }
        return  trueCounty;
    }


   class ImArrayAdapter extends ArrayAdapter<CountyItem> {
       Context context;

       public ImArrayAdapter(Context context, List<CountyItem> items) {
           super(context, 0, items);
           this.context = context;
       }

       @Override
       public View getView(int position, View convertView,
                           ViewGroup parent) {

           LayoutInflater inflater = LayoutInflater.from(context);

           LinearLayout itemlayout = null;
           if (convertView == null) {
               itemlayout = (LinearLayout) inflater.inflate
                       (R.layout.dataitem, null);
           } else {
               itemlayout = (LinearLayout) convertView;
           }
           CountyItem item = (CountyItem) getItem(position);

           TextView tv_name = (TextView)itemlayout.findViewById(R.id.county);
           tv_name.setText(item.getCounty());
           tv_name.setTextColor(Color.BLUE);

           TextView tv_psi = (TextView) itemlayout.findViewById(R.id.psi);
           tv_psi.setText("空氣汙染指標(Psi) :" + item.getPsi());
           TextView tv_pollutant = (TextView) itemlayout.findViewById(R.id.pollutant);
           tv_pollutant.setText("空氣汙染物(Pollutant) : " + item.getPollutant());
           TextView tv_windspeed = (TextView) itemlayout.findViewById(R.id.windspeed);
           tv_windspeed.setText("風速(m/sec) : " + item.getWindSpeed());
           TextView tv_winddir = (TextView) itemlayout.findViewById(R.id.winddir);
            tv_winddir.setText("風向(degrees) : " + item.getWindDir());
           TextView tv_pm25 = (TextView) itemlayout.findViewById(R.id.pm25);
            tv_pm25.setText("細懸浮微粒((μg/m3) : " + item.getPm25());
           TextView tv_ptime = (TextView) itemlayout.findViewById(R.id.ptime);
           tv_ptime.setText("資料建置日期(YMD) : " + item.getpTime());


           return itemlayout;
       }
   }
}
