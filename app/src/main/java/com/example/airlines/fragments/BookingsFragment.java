package com.example.airlines.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.airlines.R;
import com.example.airlines.SQLiteHelper;
import com.example.airlines.adapters.BookingAdapter;
import com.example.airlines.models.Booking;
import java.util.List;

public class BookingsFragment extends Fragment {
    private RecyclerView recyclerView;
    private BookingAdapter adapter;
    private TextView tvNoBookings;
    private SQLiteHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookings, container, false);
        
        dbHelper = new SQLiteHelper(requireContext());
        recyclerView = view.findViewById(R.id.bookingsRecyclerView);
        tvNoBookings = view.findViewById(R.id.tvNoBookings);
        
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        
        loadBookings();
        
        return view;
    }

    private void loadBookings() {
        List<Booking> bookings = dbHelper.getUserBookings();
        
        if (bookings.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            tvNoBookings.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            tvNoBookings.setVisibility(View.GONE);
            adapter = new BookingAdapter(bookings);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadBookings(); // Refresh bookings when returning to the fragment
    }
} 