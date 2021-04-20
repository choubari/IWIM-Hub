package com.choubapp.iwim_hub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.choubapp.iwim_hub.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {
    private TextInputEditText nomInput, prenomInput, emailInput, passwordInput;
    private String nom, prenom, password, email;
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();
        nomInput = findViewById(R.id.login_nom);
        prenomInput = findViewById(R.id.login_prenom);
        passwordInput = findViewById(R.id.login_password);
        emailInput = findViewById(R.id.login_email);


    }

    public void registerNewUser(View v){
        email = emailInput.getText().toString();
        password = passwordInput.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            //FirebaseUser user = mAuth.getCurrentUser();
                            User user = new User();
                            user.setEmail(emailInput.getText().toString());
                            user.setNom(nomInput.getText().toString());
                            user.setPrenom(prenomInput.getText().toString());
                            user.setType("Etudiant");
                            db.collection("User").document(email).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                   if (task.isSuccessful()) {
                                       Toast.makeText(Registration.this, "Registrated successfully", Toast.LENGTH_SHORT).show();
                                   }else{
                                       Toast.makeText(Registration.this, "Registrated Not Successfully", Toast.LENGTH_SHORT).show();
                                   }
                                }
                            });

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Registration.this, "Authentication failed. Retry.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void login(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
