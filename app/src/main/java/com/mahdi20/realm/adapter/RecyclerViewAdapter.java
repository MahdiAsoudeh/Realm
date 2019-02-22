package com.mahdi20.realm.adapter;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mahdi20.realm.R;
import com.mahdi20.realm.model.PhoneBook;

import java.util.Collections;
import java.util.List;

// mahdi20.com
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    List<PhoneBook> list = Collections.emptyList();
    Context context;

    public RecyclerViewAdapter(List<PhoneBook> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.txtName.setText(list.get(position).getName());
        holder.txtTell.setText(list.get(position).getTell());
//        holder.imageView.setImageResource(list.get(position).getImage());

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cv;
        public TextView txtName;
        public TextView txtTell;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cardView);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtTell = (TextView) itemView.findViewById(R.id.txtTell);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

}
