package com.example.zadanie3auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;



public class AfterLoginActivity extends AppCompatActivity {

    TextView textView;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);

        textView = findViewById(R.id.afterTv);
        id = getIntent().getIntExtra("id",0);
        String a;
        if(Global.range==1)
            a = "Admin";
        else
            a = "User";

        textView.setText(a + ": " + Global.name);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}
