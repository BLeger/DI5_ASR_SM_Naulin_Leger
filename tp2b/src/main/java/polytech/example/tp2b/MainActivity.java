package polytech.example.tp2b;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final EditText editText = findViewById(R.id.editText);
        ImageButton bt = findViewById(R.id.imageButton);
        final MainActivity context = this;

        final EditText textNom = findViewById(R.id.textNom);
        final EditText textPrenom = findViewById(R.id.textPrenom);
        final EditText textAge = findViewById(R.id.textAge);
        Button btnSubmit = findViewById(R.id.btnSubmit);


        bt.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Activity2.class);
                Bundle objetBundle = new Bundle();
                objetBundle.putString("passInfo", editText.getText().toString());
                intent.putExtras(objetBundle);
                startActivityForResult(intent, 12); // 12 = id de l'activity
            }
        });

        btnSubmit.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowPersonneActivity.class);
                Bundle objBundle = new Bundle();
                try {
                    Personne personne = new Personne(textNom.getText().toString(), textPrenom.getText().toString(), Integer.valueOf(String.valueOf(textAge.getText())));
                    objBundle.putSerializable("personne", personne);
                    intent.putExtras(objBundle);
                    startActivity(intent);

                } catch (NumberFormatException e) {
                    Toast.makeText(context, "Age doit etre un nombre", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // on vérifie que le résultat provient de la demande de la main activity
        if(requestCode == 12) {
            // on vérifie qu'on reçoit le bon code retour
            if(resultCode == 1 || resultCode == 2) {
                Bundle objetbundle = data.getExtras();

                if (objetbundle != null && objetbundle.containsKey("hello")) {
                    String info = objetbundle.getString("hello");
                    final EditText editText = findViewById(R.id.editText);
                    editText.setText(info);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
