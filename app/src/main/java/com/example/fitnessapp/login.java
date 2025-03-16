package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fitnessapp.classes.Users;
import com.example.fitnessapp.utils.Utils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class login extends AppCompatActivity {

    EditText email, password;
    Button loginBtn, signUpBtn;
    ImageView googleSigninBtn;

    private FirebaseAuth auth;
    private GoogleSignInClient googleSignInClient;
    private FirebaseDatabase firebaseDatabase;
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        googleSigninBtn = findViewById(R.id.googleProviderBtn);

        loginBtn = findViewById(R.id.loginBtn);
        signUpBtn = findViewById(R.id.signupBtn);
        email = findViewById(R.id.usernameInput);
        password = findViewById(R.id.passwordInput);

        if(auth.getCurrentUser() != null){
            Intent goToDashboard = new Intent(login.this, MainActivity.class);
            startActivity(goToDashboard);
            finish();
        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = email.getText().toString().trim();
                String userPass = password.getText().toString().trim();

                if(user.isEmpty()){
                    email.setError("Email cannot be empty");
                } else if(!user.contains("@")){
                    email.setError("Invalid email type");
                } else if(userPass.isEmpty()){
                    password.setError("Password cannot be empty");
                } else{
                    auth.signInWithEmailAndPassword(user, userPass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent goToDashboard = new Intent(login.this, MainActivity.class);
                            startActivity(goToDashboard);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if(e instanceof FirebaseAuthInvalidUserException){
                                Toast.makeText(login.this, "user does not exist", Toast.LENGTH_SHORT).show();
                            } else if(e instanceof FirebaseAuthInvalidCredentialsException){
                                Toast.makeText(login.this, "Email or Password is incorrect.", Toast.LENGTH_SHORT).show();
                            } else{
                                Toast.makeText(login.this, "Login Err: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

//        firebase db starts here
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

//       google sign in flow
        googleSigninBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });


        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToSignUpPage = new Intent(login.this, signup.class);
                startActivity(goToSignUpPage);
            }
        });
    }

    private void signIn(){
        Intent signInUser = googleSignInClient.getSignInIntent();
        startActivityForResult(signInUser, RC_SIGN_IN);
    }

    // google signin handler
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
//                google signin is successful
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);

            } catch(ApiException e){
                int statusCode = e.getStatusCode();
                Log.w("MainActivity", "Google sign-in failed with status code: " + statusCode, e);
                switch (statusCode) {
                    case GoogleSignInStatusCodes.SIGN_IN_CANCELLED:
                        Toast.makeText(this, "Sign-in was canceled by the user", Toast.LENGTH_SHORT).show();
                        break;
                    case GoogleSignInStatusCodes.SIGN_IN_REQUIRED:
                        Toast.makeText(this, "Sign-in is required", Toast.LENGTH_SHORT).show();
                        break;
                    case GoogleSignInStatusCodes.NETWORK_ERROR:
                        Toast.makeText(this, "Network error occurred. Please check your internet connection.", Toast.LENGTH_SHORT).show();
                        break;
                    case GoogleSignInStatusCodes.INTERNAL_ERROR:
                        Toast.makeText(this, "Internal error occurred. Please try again later.", Toast.LENGTH_SHORT).show();
                        break;
                    case GoogleSignInStatusCodes.DEVELOPER_ERROR:
                        Toast.makeText(this, "Developer error. Please check your configuration in the Google Developer Console.", Toast.LENGTH_SHORT).show();
                        break;
                    case GoogleSignInStatusCodes.SIGN_IN_FAILED:
                        Toast.makeText(this, "Sign-in failed. Please try again.", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(this, "An unexpected error occurred during Google sign-in", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acc){
        AuthCredential credential= GoogleAuthProvider.getCredential(acc.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if(!task.isSuccessful()){
                        Log.w("MainActivity", "signInWithCredential:failure", task.getException());
                        Toast.makeText(this, "signInWithCredential:failure", Toast.LENGTH_SHORT).show();
                    } else{
                        FirebaseUser user = auth.getCurrentUser();
                        if(user != null){
                            String userId = user.getUid();
                            String userName = user.getDisplayName();
                            String userEmail = user.getEmail();

//                           add user data to database
                            addUserToDatabase(userId, userName, userEmail);
                        }
                    }
                });
    }

    public void addUserToDatabase(String uid, String userName, String email){
        DatabaseReference userRef = firebaseDatabase.getReference("users").child(uid);
        Utils dateFormat = new Utils();
        String formattedDate = dateFormat.setDate(System.currentTimeMillis());

//      check if user already exists
        userRef.get().addOnCompleteListener(task -> {
            if(!task.isSuccessful() || !task.getResult().exists()){
//              user does not exist, add to the db
                userRef.setValue(new Users(uid, userName, email, formattedDate))
                        .addOnSuccessListener(aVoid ->{
                            Log.d("MainActivity", "User added to Realtime Database");

                            Intent toDashboard = new Intent(login.this, MainActivity.class);
                            Toast.makeText(this, "Sign in successfull", Toast.LENGTH_SHORT).show();
                            startActivity(toDashboard);
                            finish();
                        })
                        .addOnFailureListener(e -> {
                            Log.d("MainActivity", "Error adding user to Realtime Database");
                            Toast.makeText(this, "Error adding user to Realtime Database", Toast.LENGTH_SHORT).show();
                        });
            } else{
                Log.d("MainActivity", "User Already exist");
                Intent toDashboard = new Intent(login.this, MainActivity.class);
                startActivity(toDashboard);
                finish();
            }
        });

    }
}