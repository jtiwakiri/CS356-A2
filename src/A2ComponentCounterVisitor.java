/**
 * Visitor for counting stats about Users and Groups.
 */
public class A2ComponentCounterVisitor implements A2Visitor {
    private int userTotal;
    private int groupTotal;
    private int messageTotal;
    private int positiveTotal;

    public void visitUser(User user) {
        userTotal++;
        messageTotal += user.getNumPosts();
        for (String post : user.getPostsList()) {
            if (post.toLowerCase().contains("good")) {
                this.positiveTotal++;
            }
        }
    }

    public void visitGroup(Group group) {
        groupTotal++;
    }

    public int getUserTotal() {
        return this.userTotal;
    }

    public int getGroupTotal() {
        return this.groupTotal;
    }

    public int getMessageTotal() {
        return this.messageTotal;
    }

    public int getPositiveTotal() {
        return this.positiveTotal;
    }
}
