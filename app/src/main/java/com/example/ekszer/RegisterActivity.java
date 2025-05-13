package com.example.ekszer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {

    private static final String LOG_TAG = RegisterActivity.class.getName();

    private FirebaseAuth mAuth;

    private FirebaseFirestore mFirestore;
    private CollectionReference mUsers;

    EditText fullnameET;
    EditText usernameET;
    EditText emailET;
    EditText passwordET;
    EditText password_reET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fullnameET = findViewById(R.id.fullname);
        usernameET = findViewById(R.id.username);
        emailET = findViewById(R.id.email);
        passwordET = findViewById(R.id.password);
        password_reET = findViewById(R.id.password_re);

        mAuth = FirebaseAuth.getInstance();

        mFirestore = FirebaseFirestore.getInstance();
        mUsers = mFirestore.collection("users");

    }

    @SuppressLint("NewApi")
    public void register(View view) {

        if (fullnameET.getText().isEmpty() || usernameET.getText().isEmpty() || emailET.getText().isEmpty() || passwordET.getText().isEmpty() || password_reET.getText().isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Minden mező kitöltése kötelező!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (passwordET.getText().toString().length() < 6) {
            Toast.makeText(RegisterActivity.this, "A jelszónak legalább 6 karakter hosszúnak kell lennie!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!passwordET.getText().toString().equals(password_reET.getText().toString())) {
            Toast.makeText(RegisterActivity.this, "A két jelszó nem egyezik!", Toast.LENGTH_SHORT).show();
        }

        mUsers.whereEqualTo("email", emailET.getText().toString()).get().addOnSuccessListener(query -> {
            if (!query.isEmpty()) {
                Toast.makeText(this, "Ez az email cím már regisztrálva van!", Toast.LENGTH_SHORT).show();
                return;
            }
            mUsers.whereEqualTo("username", usernameET.getText().toString()).get().addOnSuccessListener(query2 -> {
                if (!query2.isEmpty()) {
                    Toast.makeText(this, "Ez a felhasználónév már foglalt!", Toast.LENGTH_SHORT).show();
                } else {
                    fireRegister();
                }
            }).addOnFailureListener(e -> {
                Toast.makeText(this, "Hiba történt.", Toast.LENGTH_SHORT).show();
            });
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Hiba történt.", Toast.LENGTH_SHORT).show();
        });


    }

    public void fireRegister() {
        mAuth.createUserWithEmailAndPassword(emailET.getText().toString(), passwordET.getText().toString()).addOnCompleteListener(
                this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mUsers.add(
                                    new User(fullnameET.getText().toString(),
                                            emailET.getText().toString(),
                                            usernameET.getText().toString()
                                    )
                            );
                            Toast.makeText(RegisterActivity.this, "Sikeres regisztráció!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Sikertelen regisztráció!", Toast.LENGTH_SHORT).show();
                            Log.e(LOG_TAG, "Registration failed!", task.getException());
                        }
                    }
                }
        );
    }

    public void login(View view) {
        finish();
    }
}