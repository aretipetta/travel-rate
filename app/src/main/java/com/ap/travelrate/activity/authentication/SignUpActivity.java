package com.ap.travelrate.activity.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ap.travelrate.R;
import com.ap.travelrate.activity.authentication.domain.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private TextView username, email, password, confirmPassword;
    private Button signUpButton;
    private FirebaseAuth auth;
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        username = findViewById(R.id.usernameSignUp);
        email = findViewById(R.id.emailSignUp);
        password = findViewById(R.id.passwordSignUp);
        confirmPassword = findViewById(R.id.confirmPasswordSignUp);
        signUpButton = findViewById(R.id.signUpBtn);

        // authentication via firebaseAuth
        auth = FirebaseAuth.getInstance();

        // set listeners to controls
        setListeners();
    }

    private void setListeners() {
        // todo if it's needed
        signUpButton.setOnClickListener(this::signUp);
    }

    public void signUp(View view) {
        auth.createUserWithEmailAndPassword(email.getText().toString().trim(), password.toString().trim())
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        // create user
                        createUser();
                    }
                    else {
                        new AlertDialog.Builder(SignUpActivity.this)
                                .setTitle("Failed")
                                .setMessage("Failed to sign up.")
                                .show();
                    }
                });
    }

    private void createUser() {
        User user = new User(username.getText().toString().trim(),
                email.getText().toString().trim(), password.getText().toString().trim());
        database = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = database.getReference("users/" +
                username.getText().toString().trim()); // auth.getCurrentUser().getEmail()
        dbRef.setValue(user).addOnCompleteListener(task -> {
            String title = "Completed", message = "You have successfully signed up!";

            if(!task.isSuccessful()) {
                title = "Failed";
                message = "Failed to sign up.";
                //todo: rollback and delete the current user
            }
            new AlertDialog.Builder(SignUpActivity.this)
                    .setTitle(title)
                    .setMessage(message)
                    .show();
        });
    }

}