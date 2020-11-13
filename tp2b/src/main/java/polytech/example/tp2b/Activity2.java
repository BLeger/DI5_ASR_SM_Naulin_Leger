package polytech.example.tp2b;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class Activity2 extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        setTitle("fenetre 2");
        //récupération du text dans le champ de saisie
        final EditText textchampsaisie = (EditText) findViewById(R.id.nameText);
        Bundle objetbunble = this.getIntent().getExtras();
        // récupération de la valeur

        if (objetbunble != null && objetbunble.containsKey("passInfo")) {
            String InfoPasse = objetbunble.getString("passInfo");
            // on affiche l'information dans l'edittext
            textchampsaisie.setText(InfoPasse);
        }
    }
}