package com.choubapp.iwim_hub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.choubapp.iwim_hub.Model.Message;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Messagerie extends AppCompatActivity {
    private String sender;
    private ListView mChatListView;
    private EditText mInputText;
    private ImageView mSendButton;
    String email, userSelectedEmail;
    String receiverSelected;

    private DatabaseReference mDatabaseReference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference user = db.collection("User");

    TextView date;
    private ChatListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userSelectedEmail = getIntent().getStringExtra("SELECTED_USER_EMAIL");
        setContentView(R.layout.activity_messagerie);
        email= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mInputText = findViewById(R.id.messageInput);
        mSendButton = findViewById(R.id.sendButton);
        mChatListView = findViewById(R.id.chat_list_view);
        date = findViewById(R.id.date);
        //setupDisplayName();
        // envoyer message si la touche "enter" du clavier est cliquée
        mInputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Messagerie.this.sendMessage();
                return false;
            }
        });

        // envoyer un message si le boutton d'envoi est cliqué
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Messagerie.this.sendMessage();
            }
        });
        LoadSpinnerData();
    }



    public void LoadSpinnerData(){
        // spinner des destinataires
        final Spinner spinner = findViewById(R.id.spinnerMessages);
        final List<String> receiverList = new ArrayList<>();
        final List<String> receiverEmails = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, receiverList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        user.whereNotEqualTo("email",email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String receiverNom = document.getString("nom");
                        String receiverPrenom = document.getString("prenom");
                        String receiverEmail = document.getString("email");
                        if (receiverEmail != null) {
                            receiverList.add(receiverNom + " "+ receiverPrenom);
                            receiverEmails.add(receiverEmail);
                            if (receiverEmail.equals(userSelectedEmail)){
                                int spinnerPosition = adapter.getPosition(receiverNom + " "+ receiverPrenom);
                                spinner.setSelection(spinnerPosition);
                            }
                        }

                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int select = parent.getSelectedItemPosition();
                receiverSelected = receiverEmails.get(select);
                date.setText("");
                onStop();
                onStart();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
/*
    private void setupDisplayName(){
        user.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.getString("email").equals(email))
                            sender = document.getString("nom")+ " "+document.getString("prenom");
                    }
                }
            }
        });
    }
*/

    private void sendMessage() {
        //envoyer le message à Firebase
        String input = mInputText.getText().toString();
        if(!input.equals("")){
            Message chat = new Message(input, email, receiverSelected, new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()), new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
            mDatabaseReference.child("messages").push().setValue(chat);
            mInputText.setText("");
        }
    }

    public void ChangeDate() {
        mChatListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (totalItemCount != 0) {

                    if (mAdapter.getDateselected(firstVisibleItem + visibleItemCount - 1).equals(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()))) {
                        date.setText("Aujourd'hui");
                    } else {
                        //changer le text de la Date
                        date.setText(mAdapter.getDateselected(firstVisibleItem + visibleItemCount - 1));
                    }
                }

            }
        });
    }

    // Setup adapter au début.
    public void onStart(){
        super.onStart();
        mAdapter = new ChatListAdapter(this , mDatabaseReference,receiverSelected, email);
        mChatListView.setAdapter(mAdapter);
        ChangeDate();
    }
    @Override
    public void onStop() {
        super.onStop();
        //supprimer Firebase event listener de l'adapter.
        mAdapter.cleaunup();
    }

}
