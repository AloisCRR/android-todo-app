package com.example.android_firebase_todo_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateTodoActivity extends AppCompatActivity {

    Button btnCrearTodo, btnVerLista;
    EditText txtTitle, txtContent;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_todo);

        btnCrearTodo = (Button) findViewById(R.id.btnCrearTodo);
        btnVerLista = (Button) findViewById(R.id.btnVerLista);
        txtTitle = (EditText) findViewById(R.id.editTextTituloTodo);
        txtContent = (EditText) findViewById(R.id.editTextContenidoTodo);

        btnCrearTodo.setOnClickListener(this::createTodo);
        btnVerLista.setOnClickListener(this::listaTodos);
    }

    public void createTodo(View v) {

        if (TextUtils.isEmpty(txtTitle.getText().toString()) || TextUtils.isEmpty(txtContent.getText().toString())) {
            Snackbar.make(v, "Debe ingresar texto en título y contenido", Snackbar.LENGTH_LONG).show();
            return;
        }

        txtTitle.onEditorAction(EditorInfo.IME_ACTION_DONE);
        txtContent.onEditorAction(EditorInfo.IME_ACTION_DONE);

        TODO todoDocument = new TODO(txtTitle.getText().toString(), txtContent.getText().toString(), false, Timestamp.now(), Timestamp.now());

        firestore.collection("todos").add(todoDocument).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    Snackbar.make(v, "¡TO DO creado con éxito!", Snackbar.LENGTH_LONG).show();
                    txtTitle.getText().clear();
                    txtContent.getText().clear();
                    txtTitle.requestFocus();
                } else {
                    Snackbar.make(v, "¡Hubo un problema al crear el TO DO!", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    public void listaTodos(View v) {
        //        firestore.collection("todos").orderBy("created", Query.Direction.ASCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
        //            private static final String TAG = "Probando";
        //
        //            @Override
        //            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
        //                List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
        //                for (DocumentSnapshot documentSnapshot : snapshotList) {
        //                    Log.d(TAG, "onSuccess: " + documentSnapshot.getData());
        //                }
        //            }
        //        });

        Intent i = new Intent(this, VerTodosActivity.class);
        startActivity(i);
    }
}