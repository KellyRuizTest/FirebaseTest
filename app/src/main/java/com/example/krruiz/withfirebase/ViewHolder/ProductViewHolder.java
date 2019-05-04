package com.example.krruiz.withfirebase.ViewHolder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.krruiz.withfirebase.Interface.ItemClickListener;
import com.example.krruiz.withfirebase.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtProductName, txtProductDescription;
    public ImageView imageView;

    public ProductViewHolder(View itemView) {
        super(itemView);


        imageView = (ImageView) itemView.findViewById(R.id.imageProduct);
        txtProductName = (TextView) itemView.findViewById(R.id.txtProductName);
        txtProductDescription = (TextView) itemView.findViewById(R.id.txtProductDescription);

    }

    @Override
    public void onClick(View v) {

    }
}
