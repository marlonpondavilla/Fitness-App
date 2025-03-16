package com.example.fitnessapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.fitnessapp.MainActivity;
import com.example.fitnessapp.R;
import com.example.fitnessapp.databinding.FragmentHomeBinding;
import com.example.fitnessapp.login;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class HomeFragment extends Fragment {

    Button logoutButton;

    private FragmentHomeBinding binding;
    private FirebaseAuth auth;
    private GoogleSignInClient googleSignInClient;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance();

        // Initialize GoogleSignInClient
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);

        logoutButton = binding.logoutBtn;

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}