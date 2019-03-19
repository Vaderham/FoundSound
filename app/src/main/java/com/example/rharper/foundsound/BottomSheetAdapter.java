package com.example.rharper.foundsound;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class BottomSheetAdapter extends RecyclerView.Adapter<BottomSheetAdapter.bottomsheetViewHolder> {

    private ArrayList<Recording> list;

    public static class bottomsheetViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView date;
        public TextView location;

        public bottomsheetViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.recordingName);
            date = itemView.findViewById(R.id.recordingDate);
            location = itemView.findViewById(R.id.recordingLocation);
        }
    }

    public BottomSheetAdapter(ArrayList<Recording> list) {
        this.list = list;
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
        viewHolder.name.setText(list.get(i).getName());
        viewHolder.date.setText(list.get(i).getDate().toString());
//        viewHolder.location.setText(list.get(i).getLocationData().toString());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
