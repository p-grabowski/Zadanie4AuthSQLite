package com.example.zadanie3auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import static android.text.TextUtils.isEmpty;

public class AddUserActivity extends AppCompatActivity {

    EditText username, name;
    Spinner range;
    Button submit;
    int intRange;
    boolean selectRange = false;

    DataBaseHelper baza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        baza = new DataBaseHelper(this);

        username = findViewById(R.id.addUserEtUsername);
        name = findViewById(R.id.addUserEtName);
        range = findViewById(R.id.addUserSpRange);
        submit = findViewById(R.id.addUserBtSubmit);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.range_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        range.setAdapter(adapter);

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

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty(username.getText()) ||
                    isEmpty(name.getText()) ||
                    selectRange){
                    if(!baza.checkUserIsExist(username.getText().toString())){
                        if(baza.addUser(username.getText().toString(), intRange, name.getText().toString())) {
                            startActivity(new Intent(getApplicationContext(), AfterLoginActivity.class));
                            Toast.makeText(AddUserActivity.this, "Uzytkownik dodany", Toast.LENGTH_SHORT).show();
                        }
                        else Toast.makeText(AddUserActivity.this, "Blad przy dodawaniu uzytkownika", Toast.LENGTH_SHORT).show();
                    }else
                        Toast.makeText(AddUserActivity.this, "Uzytkownik o takiej nazwie juz istanieje", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
