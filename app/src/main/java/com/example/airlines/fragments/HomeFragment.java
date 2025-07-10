package com.example.airlines.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.airlines.R;
import com.example.airlines.adapters.AirlineAdapter;
import com.example.airlines.models.Airline;
import com.example.airlines.CitySelectionActivity;
import com.example.airlines.FlightStatusActivity;
import com.google.android.material.card.MaterialCardView;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Setup Quick Actions
        MaterialCardView cardBookFlight = view.findViewById(R.id.cardBookFlight);
        MaterialCardView cardFlightStatus = view.findViewById(R.id.cardFlightStatus);

        cardBookFlight.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CitySelectionActivity.class);
            startActivity(intent);
        });

        cardFlightStatus.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), FlightStatusActivity.class);
            startActivity(intent);
        });

        // Setup Airlines Grid
        RecyclerView recyclerView = view.findViewById(R.id.rvAirlines);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        List<Airline> airlines = new ArrayList<>();
        // Add major Indian airlines with actual logos and website URLs
        airlines.add(new Airline("Air India", R.drawable.airindia, "https://www.airindia.com"));
        airlines.add(new Airline("IndiGo", R.drawable.indigo, "https://www.goindigo.in"));
        airlines.add(new Airline("SpiceJet", R.drawable.spicejet, "https://www.spicejet.com"));
        airlines.add(new Airline("Vistara", R.drawable.vistara, "https://www.airvistara.com"));
        airlines.add(new Airline("Go First", R.drawable.gofirst, "https://www.flygofirst.com"));
        airlines.add(new Airline("Air Asia India", R.drawable.airasia, "https://www.airasia.co.in"));

        AirlineAdapter adapter = new AirlineAdapter(airlines);
        recyclerView.setAdapter(adapter);

        return view;
    }
} 