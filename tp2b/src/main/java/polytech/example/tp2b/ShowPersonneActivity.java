package polytech.example.tp2b;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ShowPersonneActivity extends AppCompatActivity {

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

            String nameLabel = getString(R.string.name_label);
            String firstnameLabel = getString(R.string.firstname_label);
            String ageLabel = getString(R.string.age_label);

            nameTextView.setText(nameLabel + personne.getNom());
            firstnameTextView.setText(firstnameLabel + personne.getPrenom());
            ageTextView.setText(ageLabel + personne.getAge());
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