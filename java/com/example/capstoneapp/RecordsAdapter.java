package com.example.capstoneapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecordsAdapter extends RecyclerView.Adapter<RecordsAdapter.RecordViewHolder> {
    private List<Record> records;

    public RecordsAdapter(List<Record> records) {
        this.records = records;
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.record_item, parent, false);
        return new RecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        Record record = records.get(position);
        holder.dateTextView.setText(record.getDate());
        holder.teamNameTextView.setText(record.getTeamName());
        holder.opponentTextView.setText(record.getOpponent());
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public static class RecordViewHolder extends RecyclerView.ViewHolder {
        public TextView dateTextView;
        public TextView teamNameTextView;
        public TextView opponentTextView;

        public RecordViewHolder(View view) {
            super(view);
            dateTextView = view.findViewById(R.id.date_text_view);
            teamNameTextView = view.findViewById(R.id.team_name_text_view);
            opponentTextView = view.findViewById(R.id.opponent_text_view);
        }
    }
}
