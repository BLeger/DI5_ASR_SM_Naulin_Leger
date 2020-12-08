package polytech.example.tp5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    URL url;
    HttpURLConnection urlConnection = null;

    Button button;
    EditText text;
    CallWebApi callWebApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("TP5 - Requête HTTP");

        button = findViewById(R.id.button);
        text = findViewById(R.id.editText);

        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    String IP = text.getText().toString();
                    URL url = new URL("http://ip-api.com/xml/"+IP);
                    callWebApi = new CallWebApi(MainActivity.this);
                    callWebApi.execute(url.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getResult(GeoIP result) {
        if (result == null) {
            Toast.makeText(MainActivity.this, "L'IP saisi est incorrecte", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(MainActivity.this, GeoActivity.class);
        Bundle objBundle = new Bundle();
        objBundle.putSerializable("geoIP", result);
        intent.putExtras(objBundle);
        startActivity(intent);
    }
}
