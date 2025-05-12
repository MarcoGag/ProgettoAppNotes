package com.example.progettoappnotes;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;
    Button buttonLogin;
    TextView textViewSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);  // Assicurati che il layout corrisponda al nome corretto

        // Inizializza le variabili
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewSignUp = findViewById(R.id.textViewSignUp);

        // Gestisce il click sul pulsante Login
        buttonLogin.setOnClickListener(view -> {
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
            if (isValidLogin(email, password)) {
                // Se le credenziali sono corrette, apri la pagina principale dell'app
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                // Se il login fallisce
                Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
        });

        // Gestisce il click su "Non hai un account? Registrati"
        textViewSignUp.setOnClickListener(view -> {
            // Apre la pagina di registrazione (supponiamo che tu abbia una RegistrazioneActivity)
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    // Funzione di esempio per la validazione dell'email e della password
    private boolean isValidLogin(String email, String password) {
        // Esegui una verifica di base (puoi sostituirla con una connessione al tuo database o API)
        return email.equals("test@example.com") && password.equals("password123");
    }
}
