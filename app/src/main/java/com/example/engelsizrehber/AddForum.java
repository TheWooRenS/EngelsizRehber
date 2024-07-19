package com.example.engelsizrehber;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddForum extends AppCompatActivity {

    Uri imageUri;
    ImageView imgFoto;
    EditText baslikEditText, aciklamaEditText;
    FirebaseDatabase db;
    DatabaseReference reference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_forum);
        imgFoto = findViewById(R.id.imgFoto);
        baslikEditText = findViewById(R.id.baslikEditText);
        aciklamaEditText = findViewById(R.id.baslikEditText);
        db = FirebaseDatabase.getInstance();

    }

    ActivityResultLauncher<Intent> galeriIntent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK) {
                        imageUri = result.getData().getData();
                        imgFoto.setImageURI(imageUri);
                    }
                }
            });
    public void addImage(View view) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        galeriIntent.launch(intent);
    }

    public void ekle(View view) {
        final String baslik = baslikEditText.getText().toString().trim();
        final String aciklama = aciklamaEditText.getText().toString().trim();
        if (baslik.isEmpty() != true || aciklama.isEmpty() != true){
            FirebaseStorage storage = FirebaseStorage.getInstance();
            String fotoAdi = UUID.randomUUID().toString();
            StorageReference storageReference = storage.getReference();
            StorageReference fotoRef = storageReference.child("images/"+fotoAdi);
            Map<String, Object> data = new HashMap<>();
            data.put("baslik", baslik);
            data.put("aciklama", aciklama);
            data.put("resim", fotoAdi);
            reference = db.getReference(UUID.randomUUID().toString());

            fotoRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    reference.setValue(data);
                                    Intent intent = new Intent(getApplicationContext(),ForumFragment.class);
                                    startActivity(intent);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Hata", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        else {
            Snackbar.make(view, "Lütfen Tüm Alanları Doldurun", Snackbar.LENGTH_SHORT).show();
        }
    }
}