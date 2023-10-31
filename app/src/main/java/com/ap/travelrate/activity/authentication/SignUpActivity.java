package com.ap.travelrate.activity.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ap.travelrate.R;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private TextView username, email, password, confirmPassword;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        username = findViewById(R.id.usernameSignUp);
        email = findViewById(R.id.emailSignUp);
        password = findViewById(R.id.passwordSignUp);
        confirmPassword = findViewById(R.id.confirmPasswordSignUp);

        // authentication via firebaseAuth
        auth = FirebaseAuth.getInstance();

        // set listeners to controls
        setListeners();
    }

    private void setListeners() {
        // todo if it's needed
    }

    public void signUp(View view) {
        auth.createUserWithEmailAndPassword(email.getText().toString().trim(), password.toString().trim())
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        // todo create user

                    }
                    else {
                        new AlertDialog.Builder(SignUpActivity.this)
                                .setTitle("Failed")
                                .setMessage("Failed to sign up")
                                .show();
                    }
                });
    }

    private void createUser() {

    }

}