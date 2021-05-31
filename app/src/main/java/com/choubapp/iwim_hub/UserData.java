package com.choubapp.iwim_hub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class UserData extends AppCompatActivity {
    TextView fullname, email, courses, courses_names;
    ImageView picture;
    String userSelectedEmail, userSelectedRole;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userSelectedEmail = getIntent().getStringExtra("SELECTED_USER_EMAIL");
        userSelectedRole = getIntent().getStringExtra("SELECTED_USER_ROLE");

        setContentView(R.layout.activity_user_data);
        fullname = findViewById(R.id.user_fullname);
        email = findViewById(R.id.user_email);
        courses = findViewById(R.id.courses);
        courses_names = findViewById(R.id.courses_names);
        picture = findViewById(R.id.user_picture);
        loadUserData();
    }

    private void loadUserData() {
        db.collection("User").document(userSelectedEmail).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot==null){
                    Toast.makeText(UserData.this, "Error Loading Info", Toast.LENGTH_SHORT).show();
                }else{
                    fullname.setText(documentSnapshot.get("nom")+" "+documentSnapshot.get("prenom"));
                    email.setText(documentSnapshot.get("email").toString());
                    loadImage();
                    if (userSelectedRole.equals("Etudiant")){
                        courses.setVisibility(View.GONE);
                        courses_names.setVisibility(View.GONE);
                    }
                }
            }
        });

    }
    public void sendMessage (View v){
        Intent intent = new Intent(this,Messagerie.class);
        intent.putExtra("SELECTED_USER_EMAIL", userSelectedEmail);
        startActivity(intent);
    }
    private void loadImage() {
        storageReference.child(userSelectedEmail).child("ProfilePicture").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).transform(new CircleTransform()).into(picture);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                if (exception instanceof StorageException && ((StorageException) exception).getErrorCode() == StorageException.ERROR_OBJECT_NOT_FOUND) {
                    Log.d("TAG", "File not exist");
                }
            }
        });
    }
}
