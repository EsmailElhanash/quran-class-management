package com.example.halaqatalquran.HalaqatAdapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HalaqatAdapter extends RecyclerView.Adapter<HalaqatAdapter.HalaqatViewHolder> {

    @NonNull
    @Override
    public HalaqatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull HalaqatViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class HalaqatViewHolder extends RecyclerView.ViewHolder {

        public HalaqatViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
