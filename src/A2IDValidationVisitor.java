import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Visitor for checking whether all components have valid ID strings.  Valid IDs should be unique and contain no spaces.
 * IDs are checked for uniqueness across the whole set of components.  A user and group should not both hold the same
 * ID.
 */
public class A2IDValidationVisitor implements A2Visitor {
    Map<String, A2Component> componentMap;
    Set<A2Component> invalid;

    public A2IDValidationVisitor() {
        this.componentMap = new TreeMap<String, A2Component>();
        this.invalid = new TreeSet<A2Component>();
    }

    @Override
    public void visitUser(User u) {
        checkValid(u);
    }

    @Override
    public void visitGroup(Group g) {
        checkValid(g);
    }

    private void checkValid(A2Component c) {
        if (c.getId().split(" ").length > 1) {
            this.invalid.add(c);
        } else {
            // If component with the current component's ID does not exist in the map containing all visited components,
            // add it to the map.  If it does exist, add both components to the set of invalid components.
            A2Component mapEntry = this.componentMap.get(c.getId());
            if (mapEntry == null) {
                componentMap.put(c.getId(), c);
            } else {
                this.invalid.add(mapEntry);
                this.invalid.add(c);
            }
        }
    }

    public boolean isValid() {
        return invalid.size() == 0;
    }
}
