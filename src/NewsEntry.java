/**
 * Class for user messages.  Holds a User object and a String with the message.
 */
public class NewsEntry {
    private String id;
    private String text;

    public NewsEntry(User user, String post) {
        this.id = user.getId();
        this.text = post;
    }

    public String toString() {
        return this.id + ":  " + this.text;
    }

    public String getId() {
        return this.id;
    }

    public String getText() {
        return  this.text;
    }
}
