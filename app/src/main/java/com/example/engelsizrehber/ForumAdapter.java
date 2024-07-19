package com.example.engelsizrehber;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

public class ForumAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private Random random;

    private Context context;
    private ArrayList<Forum> arrayList;

    public ForumAdapter(Context context, ArrayList<Forum> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.forum_item;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.getTextView().setText(arrayList.get(position).baslik);
        holder.getTextView2().setText(arrayList.get(position).yazar + " tarafından yazıldı");
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
