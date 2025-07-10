package com.example.airlines.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.airlines.LoginActivity;
import com.example.airlines.R;
import com.example.airlines.SQLiteHelper;
import com.google.android.material.card.MaterialCardView;

public class ProfileFragment extends Fragment {
    private SQLiteHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        
        dbHelper = new SQLiteHelper(requireContext());
        
        TextView tvUsername = view.findViewById(R.id.tvUsername);
        MaterialCardView cardMembership = view.findViewById(R.id.cardMembership);
        TextView tvMembershipLevel = view.findViewById(R.id.tvMembershipLevel);
        TextView tvPoints = view.findViewById(R.id.tvPoints);
        Button btnLogout = view.findViewById(R.id.btnLogout);
        
        // Set user data
        String username = dbHelper.getCurrentUser();
        tvUsername.setText(username);
        tvMembershipLevel.setText("Gold Member"); // TODO: Implement actual membership levels
        tvPoints.setText("1,234 points"); // TODO: Implement actual points system
        
        btnLogout.setOnClickListener(v -> {
            dbHelper.logout();
            startActivity(new Intent(requireActivity(), LoginActivity.class));
            requireActivity().finish();
        });
        
        return view;
    }
} 