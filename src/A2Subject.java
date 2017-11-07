/**
 * Subject interface for A2Components.
 */
public interface A2Subject {
    public void addObserver(A2Observer o);

    public void notifyObservers();
}
