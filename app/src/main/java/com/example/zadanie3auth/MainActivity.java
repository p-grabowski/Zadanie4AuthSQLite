package com.example.zadanie3auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    EditText user, passwd;
    Button submit;
    Auth[] Users = new Auth[5];

    static DataBaseHelper baza;
    public Auth uzytkownik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = findViewById(R.id.mainEtUsername);
        passwd = findViewById(R.id.mainEtPasswd);
        submit = findViewById(R.id.mainBtSubmit);

        baza = new DataBaseHelper(this);

/*
        Users[0] = new Auth(1, "user1","test1",true);
        Users[1] = new Auth(2, "user2","test2",false);
        Users[2] = new Auth(3, "user3","test3",false);
        Users[3] = new Auth(4, "user4","test4",true);
        Users[4] = new Auth(5, "user5","test5",false);
 */

if(baza.checkUserIsExist("admin")==false) {
    baza.addUser("admin", 1,"Admin Kowalski");
    baza.addUser("user1", 0, "Uzytkownik Pierwszy");
    baza.addUser("user2", 0, "Uzytkownik Drugi");
    Log.d("Users", "dodano admina");
}else Log.d("Users", " nie dodano");


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameS = user.getText().toString();
                String passwdS= passwd.getText().toString();
                for(int i=0; i<Users.length; i++) {
                    Log.d("Users", "" + usernameS + " - " + passwdS);
                    if (baza.login(usernameS, passwdS)){
                        Toast.makeText(MainActivity.this, "zalogowano", Toast.LENGTH_SHORT).show();
                        Log.d("Users", "zalogowano "+usernameS+" id: "+baza.selectIdByUsername(usernameS));

                        Global.id = baza.selectIdByUsername(usernameS);
                        Global.name = baza.selectUserById(Global.id).getString(3);
                        Global.username = baza.selectUserById(Global.id).getString(1);
                        Global.range = baza.selectUserById(Global.id).getInt(2);

                        if(baza.checkDefaultPass(Global.id)){
                            Intent intent = new Intent(MainActivity.this, ChangePassActivity.class);
                            intent.putExtra("defaultPass", 1);
                            startActivity(intent);
                            finish();
                        }else {
                            Intent intent = new Intent(MainActivity.this, AfterLoginActivity.class);
                            //intent.putExtra("id", baza.selectIdByUsername(usernameS));
                            startActivity(intent);
                            finish();
                            break;
                        }
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Bledny username lub password", Toast.LENGTH_SHORT).show();
                        passwd.setText(null);
                    }
                }
            }
        });
    }
}