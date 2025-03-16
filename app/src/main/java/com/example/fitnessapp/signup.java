package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fitnessapp.classes.Users;
import com.example.fitnessapp.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {

    TextView haveAccTxt;
    EditText fName, midName, lastName, email, password;
    Button createAccBtn;

    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        haveAccTxt = findViewById(R.id.haveAccount);
        fName = findViewById(R.id.signFirstName);
        midName = findViewById(R.id.signMidName);
        lastName = findViewById(R.id.signLastName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        createAccBtn = findViewById(R.id.createAccBtn);

        createAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = email.getText().toString().trim();
                String userPass = password.getText().toString().trim();
                String userFName = fName.getText().toString().trim();
                String userMidName = midName.getText().toString().trim();
                String userLastName = lastName.getText().toString().trim();

                if (user.isEmpty()){
                    email.setError("Email cannot be empty");
                } else if(userPass.isEmpty()){
                    password.setError("Password cannot be empty");
                } else if(!user.contains("@")){
                    email.setError("Please enter a valid email address");
                } else if(userFName.isEmpty()){
                    fName.setError("This field is required");
                } else if(userLastName.isEmpty()){
                    lastName.setError("This field is required");
                } else{
                    auth.createUserWithEmailAndPassword(user, userPass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser firebaseUser = auth.getCurrentUser();
                                        if (firebaseUser != null) {
                                            // User created successfully
                                            String uid = firebaseUser.getUid();
                                            String userName = userFName + " " + userLastName;
                                            String formattedDate = new Utils().setDate(System.currentTimeMillis());
                                            DatabaseReference userRef = firebaseDatabase.getReference("users").child(uid);

                                            // Set the user's display name
                                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                    .setDisplayName(userName)
                                                    .build();

                                            // Update the user's profile
                                            firebaseUser.updateProfile(profileUpdates).addOnCompleteListener(profileTask -> {
                                                if (profileTask.isSuccessful()) {
                                                    Log.d("SignUpPage", "Display name updated successfully");

                                                    // Add the new user to Realtime Database
                                                    userRef.setValue(new Users(uid, userName, user, formattedDate))
                                                            .addOnSuccessListener(aVoid -> {
                                                                Log.d("SignUpPage", "User added to Realtime Database");
                                                                Toast.makeText(signup.this, "Account created successfully", Toast.LENGTH_LONG).show();
                                                                finish();
                                                            })
                                                            .addOnFailureListener(e -> {
                                                                Log.d("SignUpPage", "Error adding user to Realtime Database: " + e.getMessage());
                                                                Toast.makeText(signup.this, "Error adding user to Realtime Database", Toast.LENGTH_SHORT).show();
                                                            });
                                                } else {
                                                    Log.d("SignUpPage", "Error updating display name: " + profileTask.getException().getMessage());
                                                    Toast.makeText(signup.this, "Error updating display name", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        } else {
                                            Log.d("SignUpPage", "Error: Firebase user is null");
                                            Toast.makeText(signup.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        // Authentication failed - get the specific error
                                        Exception exception = task.getException();
                                        if (exception != null) {
                                            Log.e("SignUpPage", "Authentication failed: " + exception.getMessage());
                                            Toast.makeText(signup.this, "Authentication failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
                }
            }
        });

        haveAccTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent goBackToLoginPage = new Intent(signup.this, login.class);
                startActivity(goBackToLoginPage);
            }
        });
    }
}