import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class for creating User windows.
 */
public class UserGUI {
    private User user;
    private AdminController admin;
    private JTextField followUserField;
    private JTextField postMessageField;
    private JButton followUserButton;
    private JButton postMessageButton;
    private JList<String> followedUsersList;
    private JList<NewsEntry> newsList;
    private JFrame frame;

    public UserGUI(AdminController admin, User user) {
        this.user = user;
        this.admin = admin;
        this.frame = new JFrame("User " + user.getId());
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * Displays the window for the User.
     */
    public void display() {
        // Main content pane
        Container content = this.frame.getContentPane();
        content.setLayout(new GridLayout(1, 3));
        // Create components
        createPostMessageComponents();
        createFollowUserComponents();
        createNewsList();
        createFollowedUsersList();
        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4,1));
        buttonPanel.add(followUserField);
        buttonPanel.add(followUserButton);
        buttonPanel.add(postMessageField);
        buttonPanel.add(postMessageButton);
        // Add components to main pane
        content.add(this.followedUsersList);
        content.add(buttonPanel);
        content.add(this.newsList);
        // Set visible
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Create the JTextField and JButton for following a User.
     */
    private void createFollowUserComponents() {
        this.followUserField = new JTextField(15);
        this.followUserButton = new JButton("Follow");
        followUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User target = admin.getUser(followUserField.getText().trim());
                if (target == null) {
                    String message = "User does not exist.";
                    JOptionPane.showMessageDialog(frame, message, "Follow User Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    user.followUser(target);
                    String message = "User followed.";
                    JOptionPane.showMessageDialog(frame, message, "Follow User",
                            JOptionPane.PLAIN_MESSAGE);
                }
                followUserField.setText("");
            }
        });
    }

    /**
     * Create the JTextfield and JButton for posting a message.
     */
    private void createPostMessageComponents() {
        this.postMessageField = new JTextField(15);
        this.postMessageButton = new JButton("Post");
        postMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newPost = postMessageField.getText().trim();
                user.addPost(newPost);
                postMessageField.setText("");
            }
        });
    }

    /**
     * Create the list of followed users.
     */
    private void createFollowedUsersList() {
        ListModel<String> model = user.getFollowedUsersListModel();
        this.followedUsersList = new JList<String>(model);
    }

    /**
     * Create the list of messages from followed users.
     */
    private void createNewsList() {
        this.newsList = new JList<NewsEntry>(user.getNewsListModel());
    }
}
