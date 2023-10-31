package com.ap.travelrate.activity.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ap.travelrate.R;

public class SignInActivity extends AppCompatActivity {

    private TextView signUpTextView;
    private Button signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        signUpTextView = findViewById(R.id.signUpTextView);
        signInButton = findViewById(R.id.signInBtn);

        // set listeners to controls
        setListeners();
    }

    private void setListeners() {
//      ------ Add listener for signUpTextView to open the respective activity
        signUpTextView.setOnClickListener(view ->
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class)));

//      ------ Add listener to sign in button
        signInButton.setOnClickListener(this::signIn);
    }


    public void signIn(View view) {

    }

}