package com.example.zadanie3auth;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static android.text.TextUtils.isEmpty;

public class EditUserActivity extends AppCompatActivity {

    EditText username, name, password;
    Spinner range;
    int intRange = 0;
    int editId=0;
    boolean selectRange= false;
    DataBaseHelper baza;
    Button submit, delete, editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        baza = new DataBaseHelper(this);


        editId = getIntent().getIntExtra("edit",0);
        Log.d("Users", "Edytowany user "+ editId);

        editId++;

        username = findViewById(R.id.editEtUsername);
        name = findViewById(R.id.editEtName);
        password = findViewById(R.id.editEtPassword);
        range = findViewById(R.id.editSpRange);
        submit = findViewById(R.id.editBtSubmit);
        delete = findViewById(R.id.editBtDelete);
        //editPassword = findViewById(R.id.editBtPassword);

        username.setText(baza.selectUserById(editId).getString(1));
        name.setText(baza.selectUserById(editId).getString(3));

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

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty(username.getText())||
                        isEmpty(name.getText())
                        || isEmpty(password.getText())){
                    Toast.makeText(EditUserActivity.this, "Wszytskie pola muszą byc wypelnione", Toast.LENGTH_SHORT).show();
                }else
                if(baza.upadateUser(editId, username.getText().toString(), password.getText().toString(), intRange, name.getText().toString())){
                    //if(baza.upadateUser(editId, username.getText().toString(), intRange, name.getText().toString())){
                        Toast.makeText(EditUserActivity.this, "Dane zostaly zmienione", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), AfterLoginActivity.class));
                    finish();
                }else Toast.makeText(EditUserActivity.this, "Blad przy edycji uzytkownika", Toast.LENGTH_SHORT).show();

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    new AlertDialog.Builder(EditUserActivity.this).setMessage(
                            "Czy usunąć użytkownika ?").setPositiveButton(
                            "Usuń", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (baza.deleteUser(editId)){
                                        Toast.makeText(EditUserActivity.this, "Użytkownik usunięty.", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), AfterLoginActivity.class));
                                        finish();
                                }
                                else Toast.makeText(EditUserActivity.this, "Blad przy usuwaniu uzytkownika", Toast.LENGTH_SHORT).show();
                                }
                            }).show();
            }
        });
        /*editPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditUserActivity.this, ChangePassActivity.class);
                intent.putExtra("edit", editId);
                intent.putExtra("ifEdit", 1);
                startActivity(intent);
                finish();
            }
        });*/
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(getApplicationContext(), AfterLoginActivity.class));
    }
}
