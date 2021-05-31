package com.choubapp.iwim_hub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.choubapp.iwim_hub.Model.Matiere;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AjouterMatiere extends AppCompatActivity {
    private TextInputEditText matiere, module, horaire;
    String userRole;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Matiere newMatiere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userRole = getIntent().getStringExtra("SELECTED_USER_ROLE");
        setContentView(R.layout.activity_ajouter_matiere);
        matiere = findViewById(R.id.nom_matiere);
        horaire = findViewById(R.id.horaire_matiere);
        module = findViewById(R.id.nom_module);
    }

    public void addMatiereFirebase (View v){
        newMatiere = new Matiere();
        newMatiere.setMatiere(matiere.getText().toString());
        newMatiere.setResponsable(user.getEmail());
        newMatiere.setModule(module.getText().toString());
        newMatiere.setHoraire(horaire.getText().toString());
        db.collection("Matiere").add(newMatiere).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(AjouterMatiere.this, "Registrated successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AjouterMatiere.this, Matieres.class);
                intent.putExtra("SELECTED_USER_ROLE", userRole);
                startActivity(intent);
                finish();
            }
        });
    }
}
