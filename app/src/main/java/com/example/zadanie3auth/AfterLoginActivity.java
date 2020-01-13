package com.example.zadanie3auth;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;



public class AfterLoginActivity extends AppCompatActivity {

    TextView textView;
    //int id;
    Button addUser, changePass;
    ListView listUsers;
    DataBaseHelper baza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);
        baza = new DataBaseHelper(this);

        textView = findViewById(R.id.afterTv);
        addUser = findViewById(R.id.afterBtAddUser);
        listUsers = findViewById(R.id.afterListUsers);
        changePass = findViewById(R.id.afterBtPassword);

        //id = getIntent().getIntExtra("id",0);
        String a;
        if(Global.range==1)
            a = "Admin";
        else
            a = "User";

        textView.setText(a + ": " + Global.name);

        if(Global.range==1){            //jesli uzytkownik jest adminem
            addUser.setEnabled(true);
            RefreshList();
        }

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddUserActivity.class));
                finish();
            }
        });

        listUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AfterLoginActivity.this, EditUserActivity.class);
                intent.putExtra("edit", position);
                startActivity(intent);
                finish();
            }
        });

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ChangePassActivity.class));
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public void RefreshList(){
        Cursor usersCursor = baza.selectAll();
        listUsersAdapter PunktyAdapter = new listUsersAdapter(AfterLoginActivity.this, usersCursor);
        listUsers.setAdapter(PunktyAdapter);
        registerForContextMenu(listUsers);
    }
}
