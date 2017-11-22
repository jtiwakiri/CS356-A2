import java.util.Map;
import java.util.TreeMap;

/**
 * AdminController.  Stores data about users and groups.
 */
public class AdminController {
    private static AdminController instance;
    private Map<String, User> users;
    private Map<String, Group> groups;
    private Group root;


    private AdminController() {
        this.users = new TreeMap<String, User>();
        this.groups = new TreeMap<String, Group>();
        root = new Group("root");
        groups.put("root", root);
    }

    /**
     * @return An AdminController instance.
     */
    public static AdminController getInstance() {
        if (instance == null) {
            instance = new AdminController();
        }
        return instance;
    }

    /**
     * Add a User to the set of Users.
     * @param id ID for the new User.
     * @param groupId ID for the group that will contain the user.
     * @return A2ComponentError value indicating whether any errors occurred.
     */
    public A2ComponentError addUser(String id, String groupId) {
        if (id == null || id.length() == 0 || groupId == null || groupId.length() == 0) {
            return A2ComponentError.BLANK_ID;
        } else if (this.users.containsKey(id)) {
            return A2ComponentError.DUPLICATE_ID;
        } else if (! this.groups.containsKey(groupId)) {
            return A2ComponentError.NONEXISTENT_GROUP;
        }else {
            User newUser = new User(id);
            this.users.put(id, newUser);
            Group parent = groups.get(groupId);
            parent.addMember(newUser);
            return A2ComponentError.NO_ERROR;
        }
    }

    /**
     * Add a Group to the set of Groups.
     * @param newGroupId ID for the new Group.
     * @param parentGroupId ID for the Group that will contain the new Group.
     * @return
     */
    public A2ComponentError addGroup(String newGroupId, String parentGroupId) {
        if (newGroupId == null || newGroupId.length() == 0 || parentGroupId == null || parentGroupId.length() == 0) {
            return A2ComponentError.BLANK_ID;
        } else if (this.groups.containsKey(newGroupId)) {
            return A2ComponentError.DUPLICATE_ID;
        } else if (! this.groups.containsKey(parentGroupId)) {
            return A2ComponentError.NONEXISTENT_GROUP;
        } else {
            Group newGroup = new Group(newGroupId);
            this.groups.put(newGroupId, newGroup);
            Group parent = groups.get(parentGroupId);
            parent.addMember(newGroup);
            return A2ComponentError.NO_ERROR;
        }
    }

    /**
     * @return The number of Users.
     */
    public int userTotal() {
        A2ComponentCounterVisitor counter = new A2ComponentCounterVisitor();
        this.root.accept(counter);
        return counter.getUserTotal();
    }

    /**
     * @return The number of Groups.
     */
    public int groupTotal() {
        A2ComponentCounterVisitor counter = new A2ComponentCounterVisitor();
        this.root.accept(counter);
        return counter.getGroupTotal();
    }

    /**
     * @return The number of messages sent by all Users.
     */
    public int messageTotal() {
        A2ComponentCounterVisitor counter = new A2ComponentCounterVisitor();
        this.root.accept(counter);
        return counter.getMessageTotal();
    }

    /**
     * @return The number of messages with the word "good" in them.
     */
    public int goodMessageTotal() {
        A2ComponentCounterVisitor counter = new A2ComponentCounterVisitor();
        this.root.accept(counter);
        return counter.getPositiveTotal();
    }

    public boolean validateIDs() {
        A2IDValidationVisitor validate = new A2IDValidationVisitor();
        this.root.accept(validate);
        return validate.isValid();
    }

    public String getLastUpdatedUserId() {
        A2LastUpdatedVisitor last = new A2LastUpdatedVisitor();
        this.root.accept(last);
        return last.getLastUpdated().getId();
    }

    public User getUser(String id) {
        return users.get(id);
    }

    public Group getGroup(String id) {
        return groups.get(id);
    }
}
