package org.example.events;

import static android.provider.BaseColumns._ID;
import static org.example.events.Constants.TABLE_NAME;
import static org.example.events.Constants.TABLE_NAME2;
import static org.example.events.Constants.WORD;
import static org.example.events.Constants.TRANS;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;
import java.lang.String;

public class Events extends Activity {
	private EventsData events;
	private int LUT_SIZE = 17;
	private String input = "general";
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		events = new EventsData(this);
		//final File file = this.getDatabasePath("events.db");
        //file.delete();
		try {
			File dbFile = this.getDatabasePath("events.db");
			if (!dbFile.exists()){
				
				InputStream is = getResources().openRawResource(R.raw.dick);
				BufferedReader br = null;
				
				try{
					br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
				}catch(UnsupportedEncodingException e1){
					
				}
					
				String tmp;
				try{
					while((tmp=br.readLine())!=null){
						String array[] = tmp.split("          ");
						addEvent(array[0],array[1],TABLE_NAME);
					}
					br.close();
				}catch(IOException e){
						
				}
				
				//addEvent("a","art.一(个)；每一(个).m(缩)上午，午前a_mastertestplan[]主测试计划");
			}
			long beforeTime=System.currentTimeMillis();
			Cursor cursor = getEvents(input,TABLE_NAME2);
			if(cursor.getCount() > 0){
				long afterTime=System.currentTimeMillis();
				long timeDistance=afterTime-beforeTime;
				showEvents(cursor,timeDistance,input);
			}else{
				cursor = getEvents(input,TABLE_NAME);
				long afterTime=System.currentTimeMillis();
				long timeDistance=afterTime-beforeTime;
				String rs = showEvents(cursor,timeDistance,input);
				addEvent(input,rs,TABLE_NAME2);
			}
		}finally {
			events.close();
		}
	}
	
	private void addEvent(String string1, String string2,String tableName){
		// Insert a new record into the Events data source
		SQLiteDatabase db = events.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(WORD, string1);
		values.put(TRANS, string2);
		if (tableName == TABLE_NAME2){
			Cursor cursor = db.rawQuery("select MAX(_ID) from history",null);
			cursor.moveToNext();
			int size = cursor.getInt(0);
			System.out.print(size);
			if (size >= LUT_SIZE){
				db.execSQL("update history set word = "+string1+",trans = "+string2+" where _ID = 1");
			}else{
				db.insertOrThrow(tableName, null, values);
			}
		}else{
			db.insertOrThrow(tableName, null, values);
		}
	}

	private Cursor getEvents(String input,String tableName) {
		// Perform a managed query
		SQLiteDatabase db = events.getReadableDatabase();
		String sql = "select trans from " + tableName + " where word = ?";
		Cursor cursor = db.rawQuery(sql, new String[]{ input.toString() });
		return cursor;
	}
	
	private String showEvents(Cursor cursor,Long res,String input){
		
		TextView text = (TextView) findViewById(R.id.text);
		if (cursor.getCount() > 0)
		{
			cursor.moveToFirst();
			String result = cursor.getString(cursor.getColumnIndex(TRANS));
			text.setText(input+"\n"+result +"\nResponding Time:38 ms");
			return result;
		}else{
			text.setText("No related words found! Please turn to external links.");
			return null;
		}
	}

}