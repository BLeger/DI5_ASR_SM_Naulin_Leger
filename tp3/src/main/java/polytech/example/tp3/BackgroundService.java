package polytech.example.tp3;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class BackgroundService extends Service implements IBackgroundService{

    private Timer timer;
    private BackgroundServiceBinder binder;
    private ArrayList<IBackgroundServiceListener> listeners = null;

    public BackgroundService() {
        listeners = new ArrayList<>();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();
        binder = new BackgroundServiceBinder(this);
        Log.d(this.getClass().getName(), "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(this.getClass().getName(), "onStart");
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Date now = new Date();
                SimpleDateFormat format = new SimpleDateFormat("HH-mm-ss");

                fireDataChanged(now);
            }
        }, 0, 1000);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d(this.getClass().getName(), "onDestroy");
        this.timer.cancel();
        this.listeners.clear();
    }

    @Override
    public void addListener(IBackgroundServiceListener listener) {
        if(listeners != null){
            listeners.add(listener);
        }
    }

    @Override
    public void removeListener(IBackgroundServiceListener listener) {
        if(listeners != null){
            listeners.remove(listener);
        }
    }

    // Notification des listeners
    private void fireDataChanged(Object data){
        if(listeners != null){
            for(IBackgroundServiceListener listener: listeners){
                listener.dataChanged(data);
            }
        }
    }
}