package com.example.engelsizrehber;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewHolder extends RecyclerView.ViewHolder{
    private TextView textView, textView2;
    private CardView cardView;
    private ImageView imageView;
    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.baslik);
        textView2 = itemView.findViewById(R.id.yazar);
        cardView = itemView.findViewById(R.id.cardView);
        imageView = itemView.findViewById(R.id.imgResim);
    }


    public TextView getTextView(){
        return textView;
    }
    public TextView getTextView2(){
        return textView2;
    }

    public CardView getCardView(){
        return cardView;
    }

    public ImageView getImageView(){
        return imageView;
    }
}
