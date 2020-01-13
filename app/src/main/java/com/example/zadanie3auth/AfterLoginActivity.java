package com.example.zadanie3auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class AfterLoginActivity extends AppCompatActivity {

    TextView textView;
    int id;
    Button addUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);

        textView = findViewById(R.id.afterTv);
        addUser = findViewById(R.id.afterBtAddUser);
        
        id = getIntent().getIntExtra("id",0);
        String a;
        if(Global.range==1)
            a = "Admin";
        else
            a = "User";

        textView.setText(a + ": " + Global.name);

        if(Global.range==1)addUser.setEnabled(true);

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddUserActivity.class));
            }
        });


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}
