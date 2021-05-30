package com.choubapp.iwim_hub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Dashboard extends AppCompatActivity {
    TextView fullname, role;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        fullname = findViewById(R.id.fullname);
        role = findViewById(R.id.role);
        getUserInfo();
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


    public void logout(View v) {
        auth.signOut();
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);
    }
}
