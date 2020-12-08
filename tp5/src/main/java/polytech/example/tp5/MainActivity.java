package polytech.example.tp5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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
                /*try {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    url = new URL("http://www.google.com/");
                    urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    readStream(in);
                    urlConnection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                    urlConnection.disconnect();
                }*/

                callWebApi = new CallWebApi(text);
                callWebApi.execute();
            }
        });
    }

    private void readStream(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line + "\n");
        }
        text.setText(stringBuilder.toString());
    }
}
