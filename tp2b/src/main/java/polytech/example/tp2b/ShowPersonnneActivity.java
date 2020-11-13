package polytech.example.tp2b;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ShowPersonnneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_personnne);

        setTitle(R.string.show_person_title);

        TextView nameTextView = (TextView) findViewById(R.id.nameTextView);
        TextView firstnameTextView = (TextView) findViewById(R.id.firstnameTextView);
        TextView ageTextView = (TextView) findViewById(R.id.ageTextView);

        Bundle objetbundle = this.getIntent().getExtras();

        if (objetbundle != null && objetbundle.containsKey("personne")) {
            Personne personne = (Personne) objetbundle.getSerializable("personne");

            nameTextView.setText(R.string.name_label + personne.getNom());
            nameTextView.setText(R.string.firstname_label + personne.getPrenom());
            nameTextView.setText(R.string.age_label + personne.getAge());
        }

        // Listener bouton return
        Button returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}