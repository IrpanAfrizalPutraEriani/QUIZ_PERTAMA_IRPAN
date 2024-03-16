package com.example.quizpertamapber;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
public class Teks_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teks);

        // Ambil pesan bon belanja dari Intent
        String bonMessage = getIntent().getStringExtra("bonMessage");

        // Tampilkan pesan bon belanja pada TextView atau komponen lainnya di layout bon belanja
        TextView textViewBon = findViewById(R.id.Deskripsi);
        textViewBon.setText(bonMessage);


        Button btnshare = findViewById(R.id.TombolKirim);

        btnshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, bonMessage);
                startActivity(Intent.createChooser(shareIntent, "Bagikan With"));
            }
        });

    }
}
