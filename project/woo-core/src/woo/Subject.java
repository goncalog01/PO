package woo;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class Subject implements Serializable {

    private Map<String, Observer> observers = new TreeMap<String, Observer>(String.CASE_INSENSITIVE_ORDER);

    public void registerObserver(String key, Observer o) {
        observers.put(key, o);
    }

    public void removeObserver(String key) {
        observers.remove(key);
    }

    public boolean observedBy(String key) {
        return observers.containsKey(key);
    }

    public void notifyObservers(Notification notification) {
        for (Observer observer : observers.values())
            observer.update(notification);
    }
}
