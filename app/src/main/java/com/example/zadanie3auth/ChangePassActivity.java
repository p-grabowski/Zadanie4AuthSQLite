package com.example.zadanie3auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class ChangePassActivity extends AppCompatActivity {

    EditText newPass1, newPass2, oldPass;
    Button submit;

    DataBaseHelper baza;

    int d=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);

        oldPass = findViewById(R.id.changePassEtOldPass);
        newPass1 = findViewById(R.id.changePassEtNewPass1);
        newPass2 = findViewById(R.id.changePassEtNewPass2);
        submit = findViewById(R.id.changePassBtSubmit);

        baza = new DataBaseHelper(this);

        d = getIntent().getIntExtra("defaultPass",0);
        // jeżeli odbierze "d" to znaczy że zmiana hasla byla wymuszona przez program przy pierwszym logowaniu
        //int ifEdit = getIntent().getIntExtra("ifEdit",0);

        //if(ifEdit==1) {
        //    id = getIntent().getIntExtra("edit", 0);
        //    username = baza.selectUserById(id).getString(1);
        //}
        //else {
            //id = Global.id;
            //username = Global.username;
        //}
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(baza.login(Global.username, oldPass.getText().toString())){
                    if(newPass1.getText().toString().equals(newPass2.getText().toString())){
                        if(baza.upadateUserPass(Global.id,newPass1.getText().toString())) {
                            if(d==1){ baza.upadateUserPassDef(Global.id);}
                                Toast.makeText(ChangePassActivity.this, "zmieniono haslo", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), AfterLoginActivity.class));
                                }
                        else Log.d("Users", "błąd przy zmianie hasla");
                    }
                    else Toast.makeText(ChangePassActivity.this, "Nowe hasła są rózne od siebie", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(getApplicationContext(), AfterLoginActivity.class));
    }
}
