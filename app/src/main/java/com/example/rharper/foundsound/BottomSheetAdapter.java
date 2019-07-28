package com.example.rharper.foundsound;

import android.icu.text.AlphabeticIndex;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class BottomSheetAdapter extends RecyclerView.Adapter<BottomSheetAdapter.bottomsheetViewHolder> {

    private ArrayList<Recording> list;
    public OnRecyclerItemClickListener clickListener;

    public static class bottomsheetViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView date;

        public bottomsheetViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.recordingName);
            date = itemView.findViewById(R.id.recordingDate);
        }

        public void bind(final Recording recording, final OnRecyclerItemClickListener clickListener){
            name.setText(recording.getName());
            date.setText(recording.getDate().toString());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClick(recording);
                }
            });
        }

    }

    public BottomSheetAdapter(ArrayList<Recording> list, OnRecyclerItemClickListener clickListener) {
        this.list = list;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public BottomSheetAdapter.bottomsheetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bottom_sheet_list_items, parent, false);

        bottomsheetViewHolder viewHolder = new bottomsheetViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BottomSheetAdapter.bottomsheetViewHolder viewHolder, int i) {
        viewHolder.bind(list.get(i), clickListener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
