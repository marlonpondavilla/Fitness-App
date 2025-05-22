package com.example.fitnessapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.fitnessapp.MainActivity;
import com.example.fitnessapp.R;
import com.example.fitnessapp.classes.DatabaseRef;
import com.example.fitnessapp.databinding.FragmentHomeBinding;
import com.example.fitnessapp.login;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeFragment extends Fragment {

    TextView userName;
    Button logoutButton, submitButton;
    EditText headerEditText, descriptionEditText;

    private FragmentHomeBinding binding;
    private FirebaseAuth auth;
    private GoogleSignInClient googleSignInClient;
    private DatabaseReference databaseReference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("programs");

        // Initialize GoogleSignInClient
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);

        logoutButton = binding.logoutBtn;
        submitButton = binding.submitButton;
        userName = binding.userName;

        //      check if the user is in the dashboard
        if (auth.getCurrentUser() != null) {
            userName.setText("Hi, " + auth.getCurrentUser().getDisplayName());

        } else {
            Toast.makeText(requireActivity(), "Please sign in to view this page", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(requireActivity(), MainActivity.class);
            startActivity(intent);
            requireActivity().finish();
        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headerEditText = binding.headerEditText;
                descriptionEditText = binding.descriptionEditText;

                addInputToDb(headerEditText, descriptionEditText);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                auth.signOut();

                                googleSignInClient.signOut().addOnCompleteListener(requireActivity(), task -> {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(requireActivity(), "Logout failed", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent logout = new Intent(requireActivity(), login.class);
                        startActivity(logout);
                        requireActivity().finish();
                        Toast.makeText(requireActivity(), "Logout successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        return root;
    }

    private void addInputToDb(EditText headerEditText, EditText descriptionEditText){
        DatabaseReference userRef = databaseReference.push();
        String header = String.valueOf(headerEditText.getText());
        String description = String.valueOf(descriptionEditText.getText());
        String id = userRef.getKey();

        if(header.isEmpty() || description.isEmpty()){
            Toast.makeText(requireActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseRef programs = new DatabaseRef(id, header, description);
        databaseReference.child(id).setValue(programs).addOnSuccessListener(aVoid -> {
            headerEditText.setText("");
            descriptionEditText.setText("");
            Toast.makeText(requireActivity(), "Program added successfully", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            Toast.makeText(requireActivity(), "Failed to add program to DB", Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}