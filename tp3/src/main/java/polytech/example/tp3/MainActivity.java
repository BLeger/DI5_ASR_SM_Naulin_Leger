package polytech.example.tp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button btnStart;
    Button btnConnexion;
    Button btnDeconnexion;
    Button btnStop;
    EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("TP3 - Services");

        btnStart = findViewById(R.id.btnStart);
        btnConnexion = findViewById(R.id.btnConnexion);
        btnDeconnexion = findViewById(R.id.btnDeconnexion);
        btnStop = findViewById(R.id.btnStop);
        text = findViewById(R.id.text);

        btnStart.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText("START");
            }
        });

        btnConnexion.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText("CONNEXION");
            }
        });

        btnDeconnexion.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText("DECONNEXION");
            }
        });

        btnStop.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText("STOP");
            }
        });

        Intent intent = new Intent(this,BackgroundService.class);
        startService(intent);
    }
}
