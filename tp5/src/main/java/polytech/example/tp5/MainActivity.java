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
    GeoIP result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("TP5 - Requête HTTP");

        button = findViewById(R.id.button);
        text = findViewById(R.id.editText);
        final MainActivity context = this;
        result = new GeoIP();

        /*button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
               try {
                    URL newURL = new URL("http://www.google.com/");
                    callWebApi = new CallWebApi(text);
                    callWebApi.execute(newURL.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });*/
        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(context, GeoActivity.class);
                //Bundle objBundle = new Bundle();
                try {
                    String IP = text.getText().toString();
                    URL url = new URL("http://ip-api.com/xml/"+IP);
                    callWebApi = new CallWebApi(MainActivity.this);
                    callWebApi.execute(url.toString());

                    /*GeoIP geoIP = new GeoIP();
                    objBundle.putSerializable("geoIP", geoIP);
                    intent.putExtras(objBundle);
                    startActivity(intent);*/

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getResult(GeoIP result) {
        this.result = result;
        Log.d("BENOIT", result.toString());
    }
}
