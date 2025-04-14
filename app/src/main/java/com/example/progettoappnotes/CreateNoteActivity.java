package com.example.progettoappnotes;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        // Trova il bottone per tornare indietro
        ImageView imageBack = findViewById(R.id.imageBack);

        if (imageBack != null) {
            imageBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Utilizza la gestione moderna del back con getOnBackPressedDispatcher()
                    getOnBackPressedDispatcher().onBackPressed();
                }
            });
        } else {
            // Se non trova l'ImageView, mostra un messaggio di errore
            Toast.makeText(this, "Immagine Back non trovata!", Toast.LENGTH_SHORT).show();
        }
    }
}
