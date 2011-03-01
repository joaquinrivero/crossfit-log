package com.trekguy.crossfitlog;

import com.trekguy.crossfitlog.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Main extends Activity implements OnItemClickListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        setWodList();
    }
    
    private void setWodList()
	{
		DataHelper db = new DataHelper(this);
		
		String[] wodDays =  db.getWods();
		db.closeDatabase();

		ListView lv = (ListView)findViewById(R.id.DayList);
		
		if (wodDays == null)
		{
			lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[] {getResources().getString(R.string.addWod)}));
		}
		else
		{
			String[] wodList = new String[wodDays.length + 1];
			for (int i = 0; i < wodDays.length; i++)
			{
				wodList[i+1] = wodDays[i];
			}
			wodList[0] = getResources().getString(R.string.addWod);
			lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, wodList));
		}
		lv.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		String selectText = (String) ((TextView) view).getText();
		
		if (selectText.contains("+ Add New WOD"))
		{
			DataHelper db = new DataHelper(this);
			db.addWod((int) (Math.random()*100), "Test"); // TODO Add the wod add activity here
			db.closeDatabase();
			setWodList();
		}
		
		Log.i("Selected", selectText);
	}
}