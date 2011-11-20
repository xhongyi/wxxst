package org.example.events;

import static org.example.events.Constants.TABLE_NAME;
import static org.example.events.Constants.TIME;
import static org.example.events.Constants.TITLE;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class Events extends Activity {
	private EventsData events;
	private String input = "addtion";
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		events = new EventsData(this);
		try {
			addEvent("hello", "v.你好！");
			addEvent("temperal","a.时间的；世俗的");
			addEvent("basic","adj.基础的");
			addEvent("application","n.申请n.请求，申请；应用，应用程序n.请求，申请");
			addEvent("device","n.器械，装置；设计");
			addEvent("addtion","n.增加；（算数用语）加n.增加，附加n.加，加法；附加物");
			addEvent("fluoroscopy","n.透视");
			addEvent("spatial","a.有关空间的，在空间的a.空间的，占据空间的");
			addEvent("format","n.格式,形式");
			addEvent("still","a.不动的，平静的ad.仍然，还a.静止的n.寂静");
			Cursor cursor = getEvents(input);
			showEvents(cursor);
		}finally {
			events.close();
		}
	}
	
	private void addEvent(String string1, String string2){
		// Insert a new record into the Events data source
		SQLiteDatabase db = events.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(TIME, string1);
		values.put(TITLE, string2);
		db.insertOrThrow(TABLE_NAME, null, values);
	}
	
	private Cursor getEvents(String input) {
		// Perform a managed query
		SQLiteDatabase db = events.getReadableDatabase();
		String sql = "select title from events where time=?";
		Cursor cursor = db.rawQuery(sql, new String[]{ input.toString() });
		return cursor;
	}
	
	private void showEvents(Cursor cursor){
		
		TextView text = (TextView) findViewById(R.id.text);
		if (cursor.getCount() > 0)
		{
			cursor.moveToFirst();
			String result = cursor.getString(cursor.getColumnIndex("title"));
			text.setText(result);
		}

		else{
			text.setText("sdsd");
			
		}
	}
}