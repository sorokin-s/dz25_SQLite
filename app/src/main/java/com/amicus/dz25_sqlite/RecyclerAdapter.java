package com.amicus.dz25_sqlite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    List<Item> items;
    View view;

    public interface OnItemClickListener{// слушатель
        void onItemClick(Item book,int position,View itemView);
    }
    private final OnItemClickListener onItemClickListener;
    public RecyclerAdapter(List<Item> items, OnItemClickListener listener) { // конструктор
        this.items = items;
        this.onItemClickListener = listener;
    }
    ViewGroup viewGroup;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        viewGroup=parent;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item currentItem = items.get(position);
        holder.textView1.setText(currentItem.getName());
        holder.textView2.setText(currentItem.getAuthor());
        holder.imageView.setImageResource(currentItem.getImageResId());
        holder.itemView.setOnClickListener(i->{   // обработка клика на элементе
            onItemClickListener.onItemClick(currentItem,position,holder.itemView); // передаём данные в метод слушателя
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public void update(){this.notifyDataSetChanged();}
    public  class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView textView1,textView2;
        ImageView imageView;
        CheckBox checkBox;
        public ViewHolder(@NotNull View itemView){ // конструктор
            super(itemView);
            textView1=itemView.findViewById(R.id.itemTextView);
            textView2= itemView.findViewById(R.id.itemTextView2);
            imageView = itemView.findViewById(R.id.image);
            //  itemView.setOnClickListener(this);

        }


    }
}
