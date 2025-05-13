package com.example.ekszer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";

    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private CollectionReference mUsers;
    private User userObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if (user == null) {
            finish();
        }

        mFirestore = FirebaseFirestore.getInstance();
        mUsers = mFirestore.collection("users");


        mUsers.whereEqualTo("email", user.getEmail()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                if (task.getResult() != null && !task.getResult().isEmpty()) {
                    userObject = task.getResult().getDocuments().get(0).toObject(User.class);
                    fillData();
                } else {
                    Toast.makeText(this, "Hiba történt", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.shop_list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if( item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        if ( item.getItemId() == R.id.log_out_button) {
            mAuth.signOut();
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finishAffinity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void fillData(){
        TextView name = findViewById(R.id.profile_fullname);
        TextView email = findViewById(R.id.profile_email);
        TextView username = findViewById(R.id.profile_username);

        name.setText(getText(R.string.fullname) + ": " +  userObject.getName());
        email.setText(getText(R.string.email) + ": " + userObject.getEmail());
        username.setText(getText(R.string.username) + ": " +userObject.getUsername());
    }


}