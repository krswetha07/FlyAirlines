package com.example.airlines.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.airlines.R;
import com.example.airlines.models.Booking;
import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {
    private List<Booking> bookings;

    public BookingAdapter(List<Booking> bookings) {
        this.bookings = bookings;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_booking, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking booking = bookings.get(position);
        holder.tvRoute.setText(booking.getFrom() + " → " + booking.getTo());
        holder.tvDateTime.setText(booking.getDate() + " " + booking.getTime());
        holder.tvFlightNumber.setText("Flight: " + booking.getFlightNumber());
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    static class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView tvRoute;
        TextView tvDateTime;
        TextView tvFlightNumber;

        BookingViewHolder(View view) {
            super(view);
            tvRoute = view.findViewById(R.id.tvRoute);
            tvDateTime = view.findViewById(R.id.tvDateTime);
            tvFlightNumber = view.findViewById(R.id.tvFlightNumber);
        }
    }
} 