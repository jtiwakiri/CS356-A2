import java.util.Set;
import java.util.TreeSet;

/**
 * Class for user groups.
 */
public class Group implements A2Component {
    private String id;
    private Set<A2Component> members;

    public Group(String id) {
        this.id = id;
        this.members = new TreeSet<A2Component>();
    }

    /**
     * Add a member to the Group.
     * @param newMember Member to be added to the Group.
     */
    public void addMember(A2Component newMember) {
        this.members.add(newMember);
    }

    @Override
    public void accept(A2Visitor visitor) {
        visitor.visitGroup(this);
        for (A2Component cursor : this.members) {
            cursor.accept(visitor);
        }
    }

    @Override
    public int compareTo(A2Component o) {
        return this.hashCode() - o.hashCode();
    }

    public String toString() {
        return this.id;
    }

    public String getId() {
        return this.id;
    }
    public Set<A2Component> getMembersSet() {
        return members;
    }
    public A2Component[] getMembersArray() {
        return members.toArray(new A2Component[0]);
    }

}
