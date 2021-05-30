package com.choubapp.iwim_hub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Dashboard extends AppCompatActivity {
    TextView fullname, role;
    ImageView image;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        fullname = findViewById(R.id.fullname);
        role = findViewById(R.id.role);
        image = findViewById(R.id.user_image);
        getUserInfo();
        getUserImage();
    }

    private void getUserImage() {
        storageReference.child(user.getEmail()).child("ProfilePicture").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).transform(new CircleTransform()).into(image);
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

    private void getUserInfo() {
        db.collection("User").document(user.getEmail()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot==null){
                    Toast.makeText(Dashboard.this, "Error Loading Info", Toast.LENGTH_SHORT).show();
                }else{
                    fullname.setText(documentSnapshot.get("nom")+" "+documentSnapshot.get("prenom"));
                    role.setText(documentSnapshot.get("type").toString());
                }
            }
        });
    }
    public void uploadPicture(View V){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK  && data!= null && data.getData() != null) {
            mImageUri = data.getData();
            uploadFile();
            System.out.println("uri "+mImageUri);
        }
    }
    private void uploadFile() {
        if (mImageUri != null) {
            StorageReference fileReference = storageReference.child(user.getEmail()).child("ProfilePicture"); //User email/ProfilePic.jpg
            Toast.makeText(this, "Uploading ...", Toast.LENGTH_SHORT).show();
            fileReference.putFile(mImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    Toast.makeText(Dashboard.this, "Upload Successful", Toast.LENGTH_SHORT).show();
                    getUserImage();
                }
            });
        } else {
            Toast.makeText(this, "Can't upload image", Toast.LENGTH_SHORT).show();
        }
    }

    public void Messagerie(View v){
        Intent intent = new Intent(this, Messagerie.class);
        startActivity(intent);
    }
    public void Timetables(View v){
        Intent intent = new Intent(this, Timetables.class);
        startActivity(intent);
    }
    public void Matieres(View v){
        Intent intent = new Intent(this, Matieres.class);
        startActivity(intent);
    }
    public void Abscences(View v){
        Intent intent = new Intent(this, Abscences.class);
        startActivity(intent);
    }

    public void ProfessorsStudentsList(View V){
        if (role.getText().toString().equals("Professeur")){
            Intent intent = new Intent(this, ListEtudiants.class);
            startActivity(intent);
        }else{
            if (role.getText().toString().equals("Etudiant")){
                Intent intent = new Intent(this, ListProfesseurs.class);
                startActivity(intent);
            }else{
                Intent intent = new Intent(this, RedirectionToLists.class);
                startActivity(intent);
            }
        }
    }
    public void logout(View v) {
        auth.signOut();
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);
    }
}
