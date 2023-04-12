package observer;

public interface observable {
    void notifyObservers();
    void addObserver(observer o);
}
