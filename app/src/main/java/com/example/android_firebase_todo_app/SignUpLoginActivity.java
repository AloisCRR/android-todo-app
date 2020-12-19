package com.example.android_firebase_todo_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class SignUpLoginActivity extends AppCompatActivity {

    private static final String TAG = "Pruebas";
    int REQ_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_login);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(this, CreateTodoActivity.class);
            finish();
        }

        handleSignUpLogin();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE && resultCode == RESULT_OK) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            if (user.getMetadata().getCreationTimestamp() == user.getMetadata().getLastSignInTimestamp()) {
                Toast.makeText(this, "¡Bienvenido " + user.getDisplayName() +"!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "¡Bienvenido nuevamente "+ user.getDisplayName() +"!", Toast.LENGTH_LONG).show();
            }

            Intent intent = new Intent(this, CreateTodoActivity.class);
            startActivity(intent);
            finish();

        } else {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (response == null) {
                Toast.makeText(this, "Inicio de sesión o registro cancelado", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    public void handleSignUpLogin() {
        List<AuthUI.IdpConfig> provider = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        Intent intent = AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(provider).setLogo(R.drawable.ic_to_do_app).setAlwaysShowSignInMethodScreen(true).build();

        startActivityForResult(intent, REQ_CODE);
    }
}