package com.example.android_firebase_todo_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class VerTodosActivity extends AppCompatActivity {

    ToDoRecycleAdapter toDoRecycleAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_todo);

        recyclerView = findViewById(R.id.listaTodo);
        initRecycleTodoView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        toDoRecycleAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        toDoRecycleAdapter.stopListening();
    }

    private void initRecycleTodoView() {
        Query query = FirebaseFirestore.getInstance().collection("todos").orderBy("created", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<TODO> options = new FirestoreRecyclerOptions.Builder<TODO>().setQuery(query, TODO.class).build();

        toDoRecycleAdapter = new ToDoRecycleAdapter(options);

        recyclerView.setAdapter(toDoRecycleAdapter);
    }
}