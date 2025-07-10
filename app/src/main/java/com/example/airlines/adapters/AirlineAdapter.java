package com.example.airlines.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.airlines.R;
import com.example.airlines.WebViewActivity;
import com.example.airlines.models.Airline;
import com.google.android.material.card.MaterialCardView;
import java.util.List;

public class AirlineAdapter extends RecyclerView.Adapter<AirlineAdapter.AirlineViewHolder> {
    private final List<Airline> airlines;

    public AirlineAdapter(List<Airline> airlines) {
        this.airlines = airlines;
    }

    @NonNull
    @Override
    public AirlineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_airline, parent, false);
        return new AirlineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AirlineViewHolder holder, int position) {
        Airline airline = airlines.get(position);
        holder.airlineLogo.setImageResource(airline.getLogoResId());
        holder.airlineName.setText(airline.getName());
        
        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), WebViewActivity.class);
            intent.putExtra(WebViewActivity.EXTRA_URL, airline.getWebsiteUrl());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return airlines.size();
    }

    static class AirlineViewHolder extends RecyclerView.ViewHolder {
        final ImageView airlineLogo;
        final TextView airlineName;
        final MaterialCardView cardView;

        AirlineViewHolder(View itemView) {
            super(itemView);
            airlineLogo = itemView.findViewById(R.id.ivAirlineLogo);
            airlineName = itemView.findViewById(R.id.tvAirlineName);
            cardView = itemView.findViewById(R.id.airlineCard);
        }
    }
} 