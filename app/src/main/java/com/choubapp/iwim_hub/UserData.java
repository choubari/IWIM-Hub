package com.choubapp.iwim_hub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class UserData extends AppCompatActivity {
    TextView fullname, email, courses, courses_names;
    ImageView picture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);
        fullname = findViewById(R.id.user_fullname);
        email = findViewById(R.id.user_email);
        courses = findViewById(R.id.courses);
        courses_names = findViewById(R.id.courses_names);
        picture = findViewById(R.id.user_picture);
        loadUserData();
    }

    private void loadUserData() {
    }
}
