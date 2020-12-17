package com.example.android_firebase_todo_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class VerTodosActivity extends AppCompatActivity implements ToDoRecyclerAdapter.ToDoListener, FirebaseAuth.AuthStateListener {

    private static final String TAG = "Probando";
    ToDoRecyclerAdapter toDoRecyclerAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_todo);

        recyclerView = findViewById(R.id.listaTodo);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        initRecycleTodoView(FirebaseAuth.getInstance().getCurrentUser());
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(this);
        toDoRecyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().removeAuthStateListener(this);
        toDoRecyclerAdapter.stopListening();
    }

    private void initRecycleTodoView(FirebaseUser user) {

        Log.d(TAG, "initRecycleTodoView: CUANTAS VECES");
        
        Query query = FirebaseFirestore.getInstance().collection("todos")
                .whereEqualTo("user", user.getUid())
                .orderBy("completed", Query.Direction.ASCENDING)
                .orderBy("created", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<TODO> options = new FirestoreRecyclerOptions.Builder<TODO>().setQuery(query, TODO.class).build();

        toDoRecyclerAdapter = new ToDoRecyclerAdapter(options, this);

        recyclerView.setAdapter(toDoRecyclerAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }


        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            ToDoRecyclerAdapter.ToDoViewHolder toDoViewHolder = (ToDoRecyclerAdapter.ToDoViewHolder) viewHolder;

            if (direction == ItemTouchHelper.LEFT) {
                toDoViewHolder.deleteToDo();
            }
            if (direction == ItemTouchHelper.RIGHT) {
                toDoViewHolder.editTodo();
            }

        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(VerTodosActivity.this, R.color.amberEdit))
                    .addSwipeRightActionIcon(R.drawable.ic_baseline_edit_24)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(VerTodosActivity.this, R.color.redDelete))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    @Override
    public void handleCompleted(boolean completed, DocumentSnapshot snapshot) {
        snapshot.getReference().update("completed", completed).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Snackbar.make(recyclerView, "¡To Do actualizado!", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(recyclerView, "¡Problema al actualizar el To Do!", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void handleEdit(DocumentSnapshot snapshot) {

        TODO todo = snapshot.toObject(TODO.class);

        EditText titleEdit;
        EditText contentEdit;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.edit_todo_dialog, null);
        builder.setView(dialogView);

        titleEdit = dialogView.findViewById(R.id.editTodoTitleDialog);
        titleEdit.setText(todo.getTitle());
        titleEdit.setSelection(todo.getTitle().length());

        contentEdit = dialogView.findViewById(R.id.editTodoContentDialog);
        contentEdit.setText(todo.getContent());
        contentEdit.setSelection(todo.getContent().length());

        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                todo.setTitle(titleEdit.getText().toString());
                todo.setContent(contentEdit.getText().toString());
                todo.setUpdated(Timestamp.now());
                snapshot.getReference().set(todo).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Snackbar.make(recyclerView, "¡TO DO editado con éxito!", Snackbar.LENGTH_LONG).show();
                        } else {
                            Snackbar.make(recyclerView, "¡Hubo un problema al editar el TO DO!", Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }).setNegativeButton("Cancelar", null);

        builder.show();

//        new AlertDialog.Builder(this).setView(R.id.editTodoLayoutDialog)
//                .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Snackbar.make(recyclerView, "¡Guardando...!", Snackbar.LENGTH_LONG).show();
//                    }
//                }).setNegativeButton("Cancelar", null).show();

    }

    @Override
    public void handleDelete(DocumentSnapshot snapshot) {

        DocumentReference documentReference = snapshot.getReference();
        TODO todo = snapshot.toObject(TODO.class);
        snapshot.getReference().delete().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Snackbar.make(recyclerView, "¡TO DO eliminado con éxito!", Snackbar.LENGTH_LONG).setAction("Deshacer", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        documentReference.set(todo);
                    }
                }).show();
            } else {
                Snackbar.make(recyclerView, "¡Hubo un problema al eliminar el TO DO!", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startSignUpLoginActivity();
            return;
        }
    }

    public void startSignUpLoginActivity() {
        Intent intent = new Intent(this, SignUpLoginActivity.class);
        startActivity(intent);
        finish();
    }
}