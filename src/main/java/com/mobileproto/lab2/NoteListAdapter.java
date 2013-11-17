package com.mobileproto.lab2;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by evan on 9/15/13.
 */

public class NoteListAdapter extends ArrayAdapter {

    private List<String> data;
    private MainActivity activity;
    private SQLiteDatabase db;

    public NoteListAdapter(Activity a, int viewResourceId, List<String> data){
        super(a, viewResourceId, data);
        this.data = data;
        this.activity = (MainActivity) a;
        this.db = activity.mDbHelper.getWritableDatabase();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        View v = convertView;
        if (v==null){
            LayoutInflater vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.note_list_item, null);
        }

        ImageButton del = (ImageButton) v.findViewById(R.id.deleteButton);
        final TextView name = (TextView) v.findViewById(R.id.titleTextView);
        name.setText(data.get(position));


        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String fileName = name.getText().toString();
//                activity.deleteFile(fileName);newRowId
//                data.remove(position);
//                NoteListAdapter.this.notifyDataSetChanged();

                // Define 'where' part of query.
//                String selection = FeedReaderDbHelper.FeedEntry.COLUMN_NAME_ENTRY_ID + "=?";
                // Specify arguments in placeholder order.
                String[] selectionArgs = { data.get(position) };
                // Issue SQL statement.
                db.delete("entry", "title=?", selectionArgs);
            }
        });

        return v;
    }
}
