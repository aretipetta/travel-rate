package com.ap.travelrate.activity.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ap.travelrate.R;

public class SignInActivity extends AppCompatActivity {

    private TextView signUpTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        signUpTextView = findViewById(R.id.signUpTextView);

        // set listeners to controls
        setListeners();
    }

    private void setListeners() {
        // add listener for signUpTextView to open the respective activity
        signUpTextView.setOnClickListener(view ->
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class)));
    }


    public void signIn(View view) {

    }

}