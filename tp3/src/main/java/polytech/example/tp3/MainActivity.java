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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements IBackgroundServiceListener {

    Button btnStart;
    Button btnConnexion;
    Button btnDeconnexion;
    Button btnStop;
    EditText text;

    private ServiceConnection connection;
    private IBackgroundService service;

    private boolean serviceStarted = false;
    private boolean serviceBound = false;

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
                if (serviceStarted) {
                    Toast.makeText(MainActivity.this, "Le service est déjà démarré", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(MainActivity.this, BackgroundService.class);
                startService(intent);
                serviceStarted = true;
            }
        });

        btnConnexion.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!serviceStarted) {
                    Toast.makeText(MainActivity.this, "Le service n'est pas démarré", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (serviceBound) {
                    Toast.makeText(MainActivity.this, "Le service est déjà connecté", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(MainActivity.this, BackgroundService.class);

                //Création de l’objet Connexion
                connection = new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder binder) {
                        Log.i("BackgroundService", "Connected!");
                        service = ((BackgroundServiceBinder)binder).getService();
                        service.addListener(MainActivity.this);
                    }

                    public void onServiceDisconnected(ComponentName name) {
                        Log.i("BackgroundService", "Disconnected!");
                    }
                };

                //Connexion au servce
                bindService(intent,connection,BIND_AUTO_CREATE);
                serviceBound = true;
            }
        });

        btnDeconnexion.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!serviceBound) {
                    Toast.makeText(MainActivity.this, "Le service est déjà déconnecté", Toast.LENGTH_SHORT).show();
                    return;
                }

                service.removeListener(MainActivity.this);
                unbindService(connection);
                serviceBound = false;
            }
        });

        btnStop.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!serviceStarted) {
                    Toast.makeText(MainActivity.this, "Le service n'est pas démarré", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(MainActivity.this, BackgroundService.class);
                stopService(intent);
                serviceStarted = false;
                serviceBound = false;
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
