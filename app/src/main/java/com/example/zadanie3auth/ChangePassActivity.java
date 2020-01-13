package com.example.zadanie3auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class ChangePassActivity extends AppCompatActivity {

    EditText newPass1, newPass2, oldPass;
    Button submit;

    DataBaseHelper baza;

    String d="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);

        oldPass = findViewById(R.id.changePassEtOldPass);
        newPass1 = findViewById(R.id.changePassEtNewPass1);
        newPass2 = findViewById(R.id.changePassEtNewPass2);
        submit = findViewById(R.id.changePassBtSubmit);

        baza = new DataBaseHelper(this);

        d = getIntent().getStringExtra("defaultPass");
        // jeżeli odbierze "d" to znaczy że zmiana hasla byla wymuszona przez program przy pierwszym logowaniu


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(baza.login(Global.username, oldPass.getText().toString())){
                    if(newPass1.getText().toString().equals(newPass2.getText().toString())){
                        if(baza.upadateUserPass(Global.id,newPass1.getText().toString())){
                            if(d.equals("d")){
                                if(baza.upadateUserPassDef(Global.id)) {
                                    Toast.makeText(ChangePassActivity.this, "zmieniono haslo", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), AfterLoginActivity.class));
                                }
                                else
                                    Log.d("Users", "błąd przy zmianie hasla");
                            }
                        }
                    }
                }
            }
        });
    }
}
