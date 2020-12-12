package com.example.android_firebase_todo_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
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

        btnCrearTodo.setOnClickListener(v -> createTodo(v));
        // verLista.setOnClickListener(v -> );
    }

    public void createTodo(View v) {

        if (TextUtils.isEmpty(txtTitle.getText().toString())||TextUtils.isEmpty(txtContent.getText().toString())) {
            Snackbar.make(v, "Debe ingresar texto en título y contenido", Snackbar.LENGTH_LONG).show();
            return;
        }

        Map<String, Object> todoDocument = new HashMap<>();

        todoDocument.put("title", txtTitle.getText().toString());
        todoDocument.put("content", txtContent.getText().toString());
        todoDocument.put("completed", false);
        todoDocument.put("created", Timestamp.now());
        todoDocument.put("updated", Timestamp.now());

        firestore.collection("todos").add(todoDocument).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Snackbar.make(v, "¡TO DO creado con éxito!", Snackbar.LENGTH_LONG).show();
                txtTitle.getText().clear();
                txtContent.getText().clear();
                txtTitle.requestFocus();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Snackbar.make(v, "¡Hubo un problema al crear el TO DO!", Snackbar.LENGTH_LONG).show();
            }
        });
    }
}