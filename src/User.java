import javax.swing.*;
import java.util.*;

/**
 * Class for users.
 */
public class User implements A2Subject, A2Observer, A2Component {
    private String id;
    private ListModel<User> followedUsers;
    private List<String> posts;
    private ListModel<NewsEntry> news;
    private Set<A2Observer> usersFollowing;

    public User(String id) {
        this.id = id;
        this.followedUsers = new DefaultListModel<User>();
        this.posts = new LinkedList<String>();
        this.news = new DefaultListModel<NewsEntry>();
        this.usersFollowing = new  TreeSet<A2Observer>();
    }

    /**
     * Add a post to the list of posts for this User.
     * @param post Text for the new post.
     */
    public void addPost(String post) {
        ((List<String>) this.posts).add(post);
        this.notifyObservers();
    }

    /**
     * Add this User to the list of Users following the given User.
     * @param target User for this user to follow.
     */
    public void followUser(User target) {
        target.addObserver(this);
        ((DefaultListModel<User>) this.followedUsers).addElement(target);
    }

    @Override
    public void update(A2Subject s) {
        if (s instanceof User) {
            User followedUser = ((User) s);
            String newestPost = ((User) s).getLastPost();
            ((DefaultListModel<NewsEntry>) this.news).addElement(new NewsEntry(followedUser, newestPost));
        }
    }

    @Override
    public void accept(A2Visitor a) {
        a.visitUser(this);
    }

    @Override
    public void addObserver(A2Observer o) {
        usersFollowing.add(o);
    }

    @Override
    public int compareTo(A2Component o) {
        return this.hashCode() - o.hashCode();
    }

    public void notifyObservers() {
        for (A2Observer a : usersFollowing) {
            a.update(this);
        }
    }

    public String toString() {
        return this.id;
    }

    public String getId() {
        return id;
    }

    public String getLastPost() {
        return this.posts.get(this.posts.size() - 1);
    }

    public int getNumPosts() {
        return this.posts.size();
    }

    public ListModel getFollowedUsersListModel() {
        return this.followedUsers;
    }

    public List<String> getPostsList() {
        return this.posts;
    }

    public ListModel getNewsListModel() {
        return this.news;
    }

}
