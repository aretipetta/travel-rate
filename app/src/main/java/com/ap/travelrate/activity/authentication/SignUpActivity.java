package com.ap.travelrate.activity.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ap.travelrate.R;
import com.ap.travelrate.activity.authentication.domain.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private TextView username, email, password, confirmPassword;
    private Button signUpButton;
    private FirebaseAuth auth;
    private FirebaseDatabase database;

    private final String usernameRgx = "^_*[a-zA-Z0-9]+(_*[a-zA-Z0-9]*)*$",
            emailRgx = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$",
            passwordRgx = "^[0-9]{4}$";


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

        // ------ Set listeners to controls
        setListeners();
    }

    private void setListeners() {
        // ------ Add listener to sign Up button
        signUpButton.setOnClickListener(this::signUp);
    }

    public void signUp(View view) {
        // ------ Validate inputs
        if(!validateInputs(username.getText().toString().trim(), email.getText().toString().trim(),
                password.getText().toString().trim(), confirmPassword.getText().toString().trim())) {
            new AlertDialog.Builder(SignUpActivity.this)
                    .setTitle("Error")
                    .setMessage("Some fields are invalid or passwords don't match.")
                    .show();
            return;
        }

        // otherwise continue with registering the new user
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

    private boolean validateInputs(String username, String email, String password, String confirmPassword) {
        return username.matches(usernameRgx) &&
                email.matches(emailRgx) &&
                password.matches(passwordRgx) &&
                confirmPassword.matches(passwordRgx) &&
                password.equals(confirmPassword);
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
                // rollback: remove the authenticated user from firebase users
                FirebaseUser deleteUser = auth.getCurrentUser();
                deleteUser.delete()
                        .addOnCompleteListener(deleteTask -> {
                            if(deleteTask.isSuccessful()) {
                                Log.d("Delete User", "The user has been successfully been removed.");
                            }
                        });
            }
            new AlertDialog.Builder(SignUpActivity.this)
                    .setTitle(title)
                    .setMessage(message)
                    .show();
        });
    }

}