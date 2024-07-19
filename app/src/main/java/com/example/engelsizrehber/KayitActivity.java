package com.example.engelsizrehber;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.UUID;

public class KayitActivity extends AppCompatActivity {

    EditText singupName, singupEmail, singupPassword, singupPasswordR;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit);

        singupName = findViewById(R.id.nameEditText);
        singupEmail = findViewById(R.id.mailEditText);
        singupPassword = findViewById(R.id.passwordEditText);
        singupPasswordR = findViewById(R.id.passwordTekrarEditText);

        sharedPreferences = getSharedPreferences(getApplicationContext().getPackageName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        firestore=FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();


    }


    public void adduser(){
        final String fullname = singupName.getText().toString().trim();
        final String email = singupEmail.getText().toString().trim();
        final String password = singupPassword.getText().toString().trim();
        final String passwordr = singupPasswordR.getText().toString().trim();

        if (fullname.isEmpty() || email.isEmpty() || password.isEmpty() || passwordr.isEmpty()){
            Toast.makeText(this, "Bütün Alanları Doldurun", Toast.LENGTH_SHORT).show();
        }
        else if (!password.equals(passwordr)){
            Toast.makeText(this, "Parolalar Eşleşmiyor", Toast.LENGTH_SHORT).show();
        }
        else if (password.length() < 6 || passwordr.length() < 6){
            Toast.makeText(this, "Parolanız 6 karakterden Fazla Olmalı", Toast.LENGTH_SHORT).show();
        }
        else if (fullname.length() < 3 || passwordr.length() > 13){
            Toast.makeText(this, "Lütfen geçerli bir isim girin", Toast.LENGTH_SHORT).show();
        }
        else if (!(email.contains("@gmail.com") || email.contains("@outlook.com") || email.contains("yahoo.com"))){
            Toast.makeText(this, "Lütfen Geçerli Mail Girin ", Toast.LENGTH_SHORT).show();
        }
        else{
            auth.createUserWithEmailAndPassword(email,passwordr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        FirebaseUser user = auth.getCurrentUser();
                        My_Models model = new My_Models(email,fullname);
                        firestore.collection("users").document(user.getUid()).set(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    String message = "Kayıt Başarılı Lütfen Giriş Yapın";
                                    editor.clear();
                                    editor.apply();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.putExtra("message", message);
                                    intent.putExtra("snack", true);
                                    startActivity(intent);
                                }
                            }
                        });

                    }
                }
            });
        }
    }
    private String writeUidToFirestore(String uid) {
        // Kullanıcının UID'sini Firestore'a yaz
        final String[] resultUid = {null};
        firestore.collection("users").document(uid)
                .set(new HashMap<String, Object>(), SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        resultUid[0] = uid;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        resultUid[0] = "ID YAZILAMADI";
                    }
                });
        return resultUid[0];
    }
    public void kayit(View view) {
        adduser();
    }

    private void showBar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {

    }

    public void goToGiris(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}