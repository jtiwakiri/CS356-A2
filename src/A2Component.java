/**
 * Interfaced used by the User and Group classes.
 */
public interface A2Component extends Comparable<A2Component> {
    public void accept(A2Visitor a);
}
