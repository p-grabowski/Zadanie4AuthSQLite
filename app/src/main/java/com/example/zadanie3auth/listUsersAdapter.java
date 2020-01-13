package com.example.zadanie3auth;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class listUsersAdapter extends CursorAdapter {

    public listUsersAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.user_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView name = view.findViewById(R.id.itemName);
        TextView username = view.findViewById(R.id.itemUsername);

        //Wyodrębnij dane z kursora
        String nameS = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        String usernameS = cursor.getString(cursor.getColumnIndexOrThrow("username"));

        name.setText(nameS);
        username.setText(usernameS);

    }
}
