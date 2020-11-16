package polytech.example.tp3;

public interface IBackgroundService {
    public void addListener(IBackgroundServiceListener listener);
    public void removeListener(IBackgroundServiceListener listener);
}
