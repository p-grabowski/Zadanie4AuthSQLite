package com.example.zadanie3auth;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class EditUserActivity extends AppCompatActivity {

    EditText username, name, password;
    Spinner range;
    int intRange;
    boolean selectRange= false;
    DataBaseHelper baza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        baza = new DataBaseHelper(this);


        int editId = getIntent().getIntExtra("edit",0);
        Log.d("Users", "Edytowany user "+ editId);

        editId++;

        username = findViewById(R.id.editEtUsername);
        name = findViewById(R.id.editEtName);
        password = findViewById(R.id.editEtPassword);
        range = findViewById(R.id.editSpRange);

        username.setText(baza.selectUserById(editId).getString(1));
        name.setText(baza.selectUserById(editId).getString(3));
        password.setText(baza.selectUserById(editId).getString(4));

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.range_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        range.setAdapter(adapter);

        intRange = baza.selectUserById(editId).getInt(2);

        range.setSelection(intRange);

        range.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                intRange = position;
                selectRange = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
