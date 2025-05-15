package com.example.progettoappnotes;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LoginActivity extends AppCompatActivity {

    EditText editTextEmail, editTextPassword, editTextUsername;
    Button buttonLogin;
    TextView textViewSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);  // Assicurati che il layout corrisponda al nome corretto

        // Inizializza le variabili
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewSignUp = findViewById(R.id.textViewSignUp);

        // Gestisce il click sul pulsante Login
        buttonLogin.setOnClickListener(view -> {
            User user = new User(editTextEmail.getText().toString(),editTextPassword.getText().toString(), editTextUsername.getText().toString());
            MediaType JSON = MediaType.get("application/json");
            String url = "http://10.0.2.2/notes/";
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
                public void onResponse(@NonNull okhttp3.Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        Log.d("success", "Login data sent successfully");
                    } else {
                        Log.d("error", "Server error: " + response.code()); //restituisce sempre errore perche usiamo http invece che https
                    }
                    response.close();
                }

                @Override
                public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                    runOnUiThread(() -> Log.d("Network error", e.getMessage()));
                }
            });


            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            // Verifica che i campi non siano vuoti
            if (TextUtils.isEmpty(email)) {
                editTextEmail.setError("Email is required");
                editTextEmail.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                editTextPassword.setError("Password is required");
                editTextPassword.requestFocus();
                return;
            }

            // Esegui il login (qui aggiungi la tua logica di autenticazione)
            /*
            if ((email, password)) {
                // Se le credenziali sono corrette, apri la pagina principale dell'app
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                // Se il login fallisce
                Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
            */
        });

        // Gestisce il click su "Non hai un account? Registrati"
        textViewSignUp.setOnClickListener(view -> {
            // Apre la pagina di registrazione (supponiamo che tu abbia una RegistrazioneActivity)
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}
