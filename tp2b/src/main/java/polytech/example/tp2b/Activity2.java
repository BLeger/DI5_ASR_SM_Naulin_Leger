package polytech.example.tp2b;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class Activity2 extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        setTitle("Fenetre 2");
        //récupération du text dans le champ de saisie
        final EditText textchampsaisie = (EditText) findViewById(R.id.nameText);
        Bundle objetbundle = this.getIntent().getExtras();
        // récupération de la valeur

        if (objetbundle != null && objetbundle.containsKey("passInfo")) {
            String InfoPasse = objetbundle.getString("passInfo");
            // on affiche l'information dans l'edittext
            textchampsaisie.setText(InfoPasse);
        }

        // Listener bouton 1
        Button returnButton1 = findViewById(R.id.returnButton1);
        returnButton1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToMainActivity(1);
            }
        });

        // Listerner bouton 2
        Button returnButton2 = findViewById(R.id.returnButton2);
        returnButton2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToMainActivity(2);
            }
        });
    }

    private void returnToMainActivity(int returnCode) {
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        bundle.putString("hello", "Hello World");

        setResult(returnCode, intent);
        finish();
    }
}