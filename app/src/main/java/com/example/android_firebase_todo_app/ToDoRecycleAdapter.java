package com.example.android_firebase_todo_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.Timestamp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ToDoRecycleAdapter extends FirestoreRecyclerAdapter<TODO, ToDoRecycleAdapter.ToDoViewHolder> {
    public ToDoRecycleAdapter(@NonNull FirestoreRecyclerOptions<TODO> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ToDoViewHolder holder, int position, @NonNull TODO model) {
        holder.titleTextView.setText(model.getTitle());
        holder.contentTextView.setText(model.getContent());
        holder.fechaTextView.setText(formatDate(model.getCreated().toDate()));
        holder.checkBox.setChecked(model.getIsCompleted());
    }

    @NonNull
    @Override
    public ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.todo_list_item, parent, false);

        return new ToDoViewHolder(view);
    }

    public static class ToDoViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView, contentTextView, fechaTextView;
        CheckBox checkBox;

        public ToDoViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.todoListTitle);
            contentTextView = itemView.findViewById(R.id.todoListContent);
            checkBox = itemView.findViewById(R.id.todoListCheckbox);
            fechaTextView = itemView.findViewById(R.id.todoListFecha);
        }
    }

    private String formatDate(Date date) {
        String pattern = "dd/MM/yyyy K:mm a";
        DateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());

        return dateFormat.format(date);
    }
}
