package com.ydev.rnbapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtName, edtEmail, edtPassword;
    private Button btnSignup;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private String email, password, name;
    private String TAG = getClass().getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        init();

    }

    private void init() {

        edtName = (EditText) findViewById(R.id.edt_name);
        edtEmail = (EditText) findViewById(R.id.edt_email);
        edtPassword = (EditText) findViewById(R.id.edt_password);

        btnSignup = (Button) findViewById(R.id.btn_signup);

        btnSignup.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        email = edtEmail.getText().toString().trim();
        password = edtPassword.getText().toString().trim();
        name = edtName.getText().toString().trim();

        if (id == R.id.btn_signup){

            if(validEntry(email, password, name)){
                Log.d(TAG, "Btn Clicked");
                addToFirebaseUser(email, password, name);
            }

        }

    }

    private void addToFirebaseUser(String email, String password, final String name) {

        Log.d(TAG, "addToFirebaseUser");

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name).build();

                            user.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    //DO NOTHING
                                }
                            });

                            user.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(SignupActivity.this,
                                                        "An email has been sent to verify your account.", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });

                            updateUI(user);


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignupActivity.this, "Something went wrong. Try Again later",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                    }
                });
    }

    private void updateUI(FirebaseUser user) {

        if (user != null){
            //Move to HomeActivity.
            Intent intent = new Intent(this, HomeActivity.class);
            finish();
            startActivity(intent);
        }

    }

    private boolean validEntry(String email, String password, String name) {
        boolean result = true;

        if (TextUtils.isEmpty(email)){
            edtEmail.setError("Invalid Email");
            result = false;
        }
        if (TextUtils.isEmpty(password)){
            edtPassword.setError("Password cannot be empty");
            result = false;
        }

        if (TextUtils.isEmpty(name)){
            edtName.setError("Invalid Name");
            result = false;
        }

        return result;
    }
}
