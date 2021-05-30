package com.choubapp.iwim_hub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RedirectionToLists extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redirection_to_lists);
    }
    public void ListProfesseurs(View V){
        Intent intent = new Intent(this, ListProfesseurs.class);
        startActivity(intent);
    }

    public void ListEtudiants(View V){
        Intent intent = new Intent(this, ListEtudiants.class);
        startActivity(intent);
    }
}
