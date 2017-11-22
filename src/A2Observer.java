/**
 * Observer interface for A2Components.
 */
public interface A2Observer {
    public void update(A2Subject s);

    public int compareTo(A2Observer o);
}
