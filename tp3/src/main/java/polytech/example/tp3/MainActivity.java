package polytech.example.tp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements IBackgroundServiceListener {

    Button btnStart;
    Button btnConnexion;
    Button btnDeconnexion;
    Button btnStop;
    EditText text;

    private ServiceConnection connection;

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
                Intent intent = new Intent(MainActivity.this, BackgroundService.class);
                startService(intent);
            }
        });

        btnConnexion.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BackgroundService.class);

                //Création de l’objet Connexion
                connection = new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        Log.i("BackgroundService", "Connected!");
                        IBackgroundService monservice = ((BackgroundServiceBinder)service).getService();
                        monservice.addListener(MainActivity.this);
                    }

                    public void onServiceDisconnected(ComponentName name) {
                        Log.i("BackgroundService", "Disconnected!");
                    }
                };

                //Connexion au servce
                bindService(intent,connection,BIND_AUTO_CREATE);
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
                Intent intent = new Intent(MainActivity.this, BackgroundService.class);
                stopService(intent);
            }
        });
    }

    @Override
    public void dataChanged(final Object data) {
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                text.setText(data.toString());
            }
        });
    }
}
