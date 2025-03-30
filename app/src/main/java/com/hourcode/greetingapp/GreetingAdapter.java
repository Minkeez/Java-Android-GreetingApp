package com.hourcode.greetingapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GreetingAdapter extends RecyclerView.Adapter<GreetingAdapter.GreetingViewHolder> {

    private List<String> greetingList;

    public GreetingAdapter(List<String> greetingList) {
        this.greetingList = greetingList;
    }

    @NonNull
    @Override
    public GreetingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new GreetingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GreetingViewHolder holder, int position) {
        String greeting = greetingList.get(position);
        holder.greetingText.setText(greeting);
    }

    @Override
    public int getItemCount() {
        return greetingList.size();
    }

    public static class GreetingViewHolder extends RecyclerView.ViewHolder {
        TextView greetingText;

        public GreetingViewHolder(@NonNull View itemView) {
            super(itemView);
            greetingText = itemView.findViewById(android.R.id.text1);
        }
    }

    public void addGreeting(String greeting) {
        greetingList.add(0, greeting); // Add to top
        notifyItemInserted(0);
    }
}
