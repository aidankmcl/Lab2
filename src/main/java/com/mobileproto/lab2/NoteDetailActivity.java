package com.mobileproto.lab2;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

/**
 * Created by evan on 9/15/13.
 */
public class NoteDetailActivity extends Activity {

    public SQLiteDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        Intent intent = getIntent();

        String fileName = intent.getStringExtra("file");

        TextView title = (TextView) findViewById(R.id.noteTitle);
        TextView noteText = (TextView) findViewById(R.id.noteText);

        title.setText(fileName);
        StringBuilder fileText = new StringBuilder();
        //            FileInputStream fis = openFileInput(fileName);
//            InputStreamReader inputStreamReader = new InputStreamReader(fis);
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//            String line;
//            while ((line = bufferedReader.readLine()) != null){
//                fileText.append(line);
//                fileText.append('\n');
//            }

        db = openOrCreateDatabase("Jab.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        Log.d("MEOW", fileName);
        Cursor c = db.rawQuery("SELECT * FROM entry WHERE title=\"" + fileName + "\"", null);
        c.moveToFirst();
        String item = c.getString(3);
        c.getColumnIndexOrThrow(FeedReaderDbHelper.FeedEntry._ID);

        fileText.append(item);

        noteText.setText(fileText.toString());

    }
}
