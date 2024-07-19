package com.example.engelsizrehber;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    EditText singinEmail, singinPassword;
    FirebaseAuth auth;
    FirebaseFirestore db;
    SharedPreferences sharedPreferences;

    TextToSpeech tts;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences(getApplicationContext().getPackageName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        singinEmail = findViewById(R.id.mailEditText);
        singinPassword = findViewById(R.id.passwordEditText);
        auth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        db = FirebaseFirestore.getInstance();
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR){
                    tts.setLanguage(new Locale("tr", "TR"));

                }
            }
        });

        if (intent.getBooleanExtra("snack",false) == true) {
            showBar(intent.getStringExtra("message"));
        }

        int giris = sharedPreferences.getInt("user-giris", 0);
        if (giris == 808257624) {
            Intent intent1 = new Intent(getApplicationContext(), NavbarActivity.class);
            startActivity(intent1);

        }
        else{

        }

    }

    private void showBar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
    }

    private void fetchUserFromFirestore() {
        editor.remove("loginusername");
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        final String userEmail = singinEmail.getText().toString().trim();
        Query query = db.collection("users").whereEqualTo("email", userEmail);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("TAG", document.getId() + "!!!!!!!!!!!!!!!!!! => " + document.getString("name"));
                        editor.putString("loginusername", document.getString("name"));
                        editor.commit();
                    }
                } else {
                    Log.d("TAG", "Belgeler alınırken hata oluştu: ", task.getException());
                }
            }
        });
    }

    public void girisYap(){
        final String userEmail = singinEmail.getText().toString().trim();
        final String userPassword = singinPassword.getText().toString().trim();
        if (TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(userPassword)) {
            Toast.makeText(getApplicationContext(), "Bütün Alanları Doldurun", Toast.LENGTH_SHORT).show();
        }
        else{
            auth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        fetchUserFromFirestore();
                        String message = "Giriş Başarılı Engelsiz Rehber'e Hoş Geldiniz";
                        Intent intent = new Intent(getApplicationContext(), NavbarActivity.class);
                        intent.putExtra("message", message);
                        intent.putExtra("snack", true);
                        startActivity(intent);

                        editor.putString("user-email", userEmail);
                        editor.putString("user-password", userPassword);
                        editor.putInt("user-giris",808257624);

                        editor.commit();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Mail veya Parola Hatalı", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    public void giris(View view){
        girisYap();
    }


    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {

    }


    public void goToKayit(View view) {
        Intent intent = new Intent(getApplicationContext(), KayitActivity.class);
        startActivity(intent);
    }

    public void talkEmail(View view){tts.speak("E-Posta Giriniz", TextToSpeech.QUEUE_FLUSH, null, null);}
    public void talkPassword(View view){tts.speak("Parola Giriniz", TextToSpeech.QUEUE_FLUSH, null, null);}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }
}