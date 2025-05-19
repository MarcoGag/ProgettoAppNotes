package com.example.progettoappnotes;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;
    Button buttonLogin;
    TextView textViewSignUp;
    TextView textViewLoggedInUser;
    Button buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail); // campo può contenere email o username
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewSignUp = findViewById(R.id.textViewSignUp);
        textViewLoggedInUser = findViewById(R.id.textViewLoggedInUser);
        buttonLogout = findViewById(R.id.buttonLogout);

        buttonLogin.setOnClickListener(view -> {
            String loginInput = editTextEmail.getText().toString().trim();
            String passwordInput = editTextPassword.getText().toString().trim();

            if (TextUtils.isEmpty(loginInput)) {
                editTextEmail.setError("Email o username richiesto");
                editTextEmail.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(passwordInput)) {
                editTextPassword.setError("Password richiesta");
                editTextPassword.requestFocus();
                return;
            }

            // Controlla se è un'email o uno username
            User user;
            if (Patterns.EMAIL_ADDRESS.matcher(loginInput).matches()) {
                user = new User(loginInput, passwordInput); // email
            } else {
                user = new User(true, loginInput, passwordInput); // username
            }

            MediaType JSON = MediaType.get("application/json");
            String url = "http://10.0.2.2/notes/login"; // Assicurati che sia corretto
            OkHttpClient client = new OkHttpClient();
            Gson gson = new Gson();
            String json = gson.toJson(user);

            RequestBody body = RequestBody.create(json, JSON);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    String responseBody = response.body().string();
                    runOnUiThread(() -> {
                        if (response.isSuccessful() && responseBody.contains("Login successful")) {
                            editTextEmail.setVisibility(View.GONE);
                            editTextPassword.setVisibility(View.GONE);
                            buttonLogin.setVisibility(View.GONE);
                            textViewSignUp.setVisibility(View.GONE);

                            textViewLoggedInUser.setText("Benvenuto, " + loginInput);
                            textViewLoggedInUser.setVisibility(View.VISIBLE);
                            buttonLogout.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(LoginActivity.this, "Credenziali errate", Toast.LENGTH_SHORT).show();
                        }
                    });
                    response.close();
                }

                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Errore di rete: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                }
            });
        });

        textViewSignUp.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}
