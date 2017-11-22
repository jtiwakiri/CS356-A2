/**
 * Visitor for finding the most recently updated user.  In case of a tie, the first User visited is selected.
 */
public class A2LastUpdatedVisitor implements A2Visitor {
    private User lastUpdated;

    @Override
    public void visitUser(User u) {
        if (lastUpdated == null) {
            lastUpdated = u;
        } else if (u.getUpdateTime() > lastUpdated.getUpdateTime()) {
            lastUpdated = u;
        }
    }

    @Override
    public void visitGroup(Group g) {

    }

    public User getLastUpdated() {
        return lastUpdated;
    }
}
