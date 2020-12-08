package polytech.example.tp5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GeoActivity extends AppCompatActivity {

    Button buttonReturn;
    EditText textGeoIP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo);
        setTitle("Informations de localisation");

        buttonReturn = findViewById(R.id.buttonReturn);
        textGeoIP = findViewById(R.id.textGeoIP);
        final GeoActivity context = this;

        buttonReturn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
