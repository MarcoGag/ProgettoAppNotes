package com.example.progettoappnotes;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

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

        editTextEmail = findViewById(R.id.editTextEmail);
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

            User user;
            if (Patterns.EMAIL_ADDRESS.matcher(loginInput).matches()) {
                user = new User(loginInput, passwordInput); // email
            } else {
                user = new User(true, loginInput, passwordInput); // username
            }

            MediaType JSON = MediaType.get("application/json");
            String url = "http://10.0.2.2/notes/login.php";  // Adatta al tuo backend

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
                    Log.d("LOGIN_RESPONSE", responseBody);

                    runOnUiThread(() -> {
                        try {
                            JSONObject obj = new JSONObject(responseBody);
                            String status = obj.optString("status", "error");
                            String message = obj.optString("message", "Errore sconosciuto");

                            if (response.isSuccessful() && status.equals("success")) {
                                try {
                                    JSONObject userJson = obj.getJSONObject("user");

                                    SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("userId", userJson.getString("id"));
                                    editor.putString("userEmail", userJson.getString("email"));
                                    editor.putBoolean("isLoggedIn", true);
                                    editor.apply();

                                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } catch (JSONException e) {
                                    Toast.makeText(LoginActivity.this, "Errore parsing utente", Toast.LENGTH_SHORT).show();
                                    Log.e("LOGIN_PARSING", e.toString());
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Log.e("LOGIN_ERROR", "Errore parsing JSON: " + e.getMessage());
                            Toast.makeText(LoginActivity.this, "Errore nella risposta del server", Toast.LENGTH_SHORT).show();
                        }
                    });

                    response.close();
                }

                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.e("LOGIN_NETWORK", "Errore di rete: " + e.getMessage());
                    runOnUiThread(() ->
                            Toast.makeText(LoginActivity.this, "Errore di rete: " + e.getMessage(), Toast.LENGTH_LONG).show()
                    );
                }
            });
        });

        textViewSignUp.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}
