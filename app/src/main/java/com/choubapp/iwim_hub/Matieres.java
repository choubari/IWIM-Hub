package com.choubapp.iwim_hub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.choubapp.iwim_hub.Model.Matiere;
import com.choubapp.iwim_hub.Model.User;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import static android.view.View.GONE;

public class Matieres extends AppCompatActivity {
    String userRole;
    LinearLayout addMatiere;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private MatiereAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userRole = getIntent().getStringExtra("SELECTED_USER_ROLE");
        setContentView(R.layout.activity_matieres);
        addMatiere=findViewById(R.id.ajouterMatiere);
        //System.out.println("role" + userRole);
        if (userRole.equals("Etudiant")){
            addMatiere.setVisibility(GONE);
            setUpRecyclerView();
        }else {
            if (userRole.equals("Professeur"))
                setUpRecyclerViewProf();
        }
    }

    private void setUpRecyclerView() {
        Query query = db.collection("Matiere");
        FirestoreRecyclerOptions<Matiere> options = new FirestoreRecyclerOptions.Builder<Matiere>()
                .setQuery(query, Matiere.class)
                .build();
        adapter= new MatiereAdapter(options) {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Toast.makeText(Matieres.this, "Matiere Selectionnée", Toast.LENGTH_SHORT).show();
            }
        };
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void setUpRecyclerViewProf() {
        Query query = db.collection("Matiere").whereEqualTo("responsable",(user.getEmail())).orderBy("matiere", Query.Direction.ASCENDING);
        System.out.println(" sdfsdfsd" + query);
        FirestoreRecyclerOptions<Matiere> options = new FirestoreRecyclerOptions.Builder<Matiere>()
                .setQuery(query, Matiere.class)
                .build();
        adapter= new MatiereAdapter(options) {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                //Toast.makeText(Matieres.this, "Matiere Selectionnée", Toast.LENGTH_SHORT).show();
            }
        };
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    public void ajouterMatiere(View V){
        Intent intent = new Intent(this, AjouterMatiere.class);
        intent.putExtra("SELECTED_USER_ROLE", userRole);
        startActivity(intent);
    }


}
