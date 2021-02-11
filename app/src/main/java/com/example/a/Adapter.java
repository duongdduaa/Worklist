package com.example.a;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    private ArrayList<Work> worklist;
    private OnClickEvent onClickEvent;
    private Context context;

    public Adapter() {

    }

    public void setData(ArrayList<Work> data) {

        worklist = new ArrayList<>();
        worklist.clear();
        worklist.addAll(data);
        notifyDataSetChanged();
    }

    public interface OnClickEvent {
        void OnItemClick(int position);

        void OnItemDelete(int position);
    }

    public void setOnClickEvent(OnClickEvent onClickEvent) {
        this.onClickEvent = onClickEvent;
    }



    @NonNull
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.MyViewHolder holder, int position) {
        Work work = worklist.get(position);
        holder.itemView.setOnClickListener(v ->{
            onClickEvent.OnItemClick(position);
        });
        holder.title.setText(work.getTitle());
        holder.editText.setText(work.getDate());
        holder.content.setText(work.getContent());


    }


    @Override
    public int getItemCount() {
        return worklist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private EditText editText, title, content;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            title = itemView.findViewById(R.id.title);
            editText = itemView.findViewById(R.id.date);
            content = itemView.findViewById(R.id.content);
        }
    }
}
