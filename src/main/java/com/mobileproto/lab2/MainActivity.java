package com.mobileproto.lab2;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {

    public FeedReaderDbHelper mDbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelper = new FeedReaderDbHelper(this);

//        SQLiteDatabase db = openOrCreateDatabase("FeedReader.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);

        final TextView title = (TextView) findViewById(R.id.titleField);
        final TextView note = (TextView) findViewById(R.id.noteField);

        List<String> files = new ArrayList<String>(Arrays.asList(fileList()));
        final NoteListAdapter aa = new NoteListAdapter(this, android.R.layout.simple_list_item_1, files);
        final ListView notes = (ListView) findViewById(R.id.noteList);

        notes.setAdapter(aa);

        Button save = (Button)findViewById(R.id.saveButton);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fileName = title.getText().toString();
                String noteText = note.getText().toString();
                if (fileName != null && noteText != null){
//                    try{
//                        FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
//                        fos.write(noteText.getBytes());
//                        fos.close();
//                        title.setText("");
//                        note.setText("");
//                        aa.insert(fileName,0);
//                        aa.notifyDataSetChanged();

//                    }catch /home/amclaughlin/Documents/MobileProto/Lab2(IOException e){
//                        Log.e("IOException", e.getMessage());

                    // Gets the data repository in write mode
                    SQLiteDatabase db = mDbHelper.getWritableDatabase();

//                    SQLiteDatabase db = openOrCreateDatabase("FeedReader.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);


// Create a new map of values, where column names are the keys
                    ContentValues values = new ContentValues();

                    values.put(FeedReaderDbHelper.FeedEntry.COLUMN_NAME_TITLE, fileName);
                    values.put(FeedReaderDbHelper.FeedEntry.COLUMN_NAME_CONTENT, noteText);

// Insert the new row, returning the primary key value of the new row
                    long newRowId;
                    assert db != null;
                    Log.d("hello", "make it");
                    newRowId = db.insertOrThrow(FeedReaderDbHelper.FeedEntry.TABLE_NAME, null, values);
                    Log.d("winner", String.valueOf(newRowId));

                  title.setText("");
                  note.setText("");
                  aa.insert(fileName,0);
                  aa.notifyDataSetChanged();
                    }
                }
        });

        save.setFocusable(false);

        notes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final TextView title = (TextView) view.findViewById(R.id.titleTextView);
                String fileName = title.getText().toString();
                Intent in = new Intent(getApplicationContext(), NoteDetailActivity.class);
                in.putExtra("file", fileName);
                startActivity(in);
            }
        });

        DatabaseHandler db = new DatabaseHandler(this);

        /**
         * CRUD Operations
         * */
        // Inserting Contacts
        Log.d("Insert: ", "Inserting ..");
//        db.addContact(new Contact(title, NoteDetailActivity.class));

        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<Contact> contacts = db.getAllContacts();

        for (Contact cn : contacts) {
            String log = "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
