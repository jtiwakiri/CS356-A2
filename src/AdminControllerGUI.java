import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class for displaying AdminController windows.
 */
public class AdminControllerGUI {
    private AdminController admin;
    private JTree userTree;
    private DefaultMutableTreeNode userTreeRoot;
    private JTextField createUserField;
    private JTextField createGroupField;
    private JButton createUserButton;
    private JButton createGroupButton;
    private JButton userViewButton;
    private JButton userTotalButton;
    private JButton groupTotalButton;
    private JButton messageTotalButton;
    private JButton goodMessageTotalButton;
    private JFrame frame;

    public AdminControllerGUI(AdminController admin) {
        this.admin = admin;
        this.frame = new JFrame("Admin Controller");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Display the window for the AdminController.
     */
    public void display() {
        // Main content pane
        Container content = this.frame.getContentPane();
        content.setLayout(new GridLayout(1, 2));
        // Create components
        createAddUserComponents();
        createAddGroupComponents();
        createUserTree();
        createOpenUserViewButton();
        createFunctionButtons();
        // Add tree to left half of pane
        content.add(this.userTree);
        // Panel for the other half of the main pane
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 1));
        panel.add(this.createUserField);
        panel.add(this.createUserButton);
        panel.add(this.createGroupField);
        panel.add(this.createGroupButton);
        panel.add(this.userViewButton);
        panel.add(this.userTotalButton);
        panel.add(this.groupTotalButton);
        panel.add(this.messageTotalButton);
        panel.add(this.goodMessageTotalButton);
        content.add(panel);
        // Set visible
        this.frame.pack();
        this.frame.setVisible(true);
    }

    /**
     * Create the JTextField and JButton used for adding new Users.
     */
    private void createAddUserComponents() {
        createUserField = new JTextField(15);
        createUserButton = new JButton("Create User");
        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createUser();
            }
        });
    }

    /**
     * Create the JTextField and JButton used for adding new Groups.
     */
    private void createAddGroupComponents() {
        this.createGroupField = new JTextField(15);
        this.createGroupButton = new JButton("Create Group");
        this.createGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createGroup();
            }
        });
    }

    /**
     * Create the JButton for displaying the user window.
     */
    private void createOpenUserViewButton() {
        this.userViewButton = new JButton("Open User View");
        this.userViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openUserView();
            }
        });
    }

    /**
     * Create the JButtons for counting Users, Groups, and messages.
     */
    public void createFunctionButtons() {
        this.userTotalButton = new JButton("Show User Total");
        this.userTotalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showUserTotal();
            }
        });
        this.groupTotalButton = new JButton("Show Group Total");
        this.groupTotalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGroupTotal();
            }
        });
        this.messageTotalButton = new JButton("Show Message Total");
        this.messageTotalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMessageTotal();
            }
        });
        this.goodMessageTotalButton = new JButton("Show Good Message Total");
        this.goodMessageTotalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGoodMessageTotal();
            }
        });
    }

    /**
     * Creates a new User.  Adds a new User to the AdminController and adds a new node to the JTree in the gui object.
     */
    public void createUser() {
        // If there is no selection or a User is selected, add to the root Group
        Group selectedGroup = admin.getGroup("root");
        TreePath selectedPath = userTree.getSelectionPath();
        DefaultMutableTreeNode selectedNode = null;
        if (! (selectedPath == null)) {
            selectedNode = ((DefaultMutableTreeNode) userTree.getSelectionPath().getLastPathComponent());
        }
        if (selectedNode != null && selectedNode.getUserObject() instanceof Group) {
            selectedGroup = ((Group) selectedNode.getUserObject());
        }
        // Add the user to the AdminController object
        A2ComponentError status = admin.addUser(createUserField.getText(), selectedGroup.getId());
        // Display error messages
        if (status == A2ComponentError.NO_ERROR) {
            addA2ComponentNode(admin.getUser(createUserField.getText()));
        } else if (status == A2ComponentError.BLANK_ID) {
            String message = "User cannot be created with blank ID.";
            JOptionPane.showMessageDialog(frame, message, "Create User Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (status == A2ComponentError.DUPLICATE_ID) {
            String message = "User could not be created with ID " + createUserField.getText() + ". ID already "
                    + "exists.";
            JOptionPane.showMessageDialog(frame, message, "Create User Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        // Clear text field
        createUserField.setText("");
    }

    /**
     * Creates a Group.  Adds a new group to the AdminController and adds a new node to the JTree in this gui object.
     */
    private void createGroup() {
        // If there is no selection or a User is selected, add to the root Group
        Group selectedGroup = admin.getGroup("root");
        TreePath selectedPath = userTree.getSelectionPath();
        DefaultMutableTreeNode selectedNode = null;
        if (! (selectedPath == null)) {
            selectedNode = ((DefaultMutableTreeNode) userTree.getSelectionPath().getLastPathComponent());
        }
        if (selectedNode != null && selectedNode.getUserObject() instanceof Group) {
            selectedGroup = ((Group) selectedNode.getUserObject());
        }
        // Add the new Group to the AdminController object
        A2ComponentError status = admin.addGroup(createGroupField.getText(), selectedGroup.getId());
        // Display errors
        if (status == A2ComponentError.NO_ERROR) {
            addA2ComponentNode(admin.getGroup(createGroupField.getText()));
        } else if (status == A2ComponentError.BLANK_ID) {
            String message = "User cannot be created with blank ID.";
            JOptionPane.showMessageDialog(frame, message, "Create User Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (status == A2ComponentError.DUPLICATE_ID) {
            String message = "User could not be created with ID " + createGroupField.getText() + ". ID already "
                    + "exists.";
            JOptionPane.showMessageDialog(frame, message, "Create User Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        // Reset text field
        createGroupField.setText("");
    }

    /**
     * Opens a window for the selected user.
     */
    private void openUserView() {
        TreePath selectedPath = this.userTree.getSelectionPath();
        DefaultMutableTreeNode selectedNode = null;
        if (! (selectedPath == null)) {
            selectedNode = ((DefaultMutableTreeNode) this.userTree.getSelectionPath().getLastPathComponent());
        }
        if (selectedNode != null && selectedNode.getUserObject() instanceof User) {
            UserGUI view = new UserGUI(this.admin, ((User) selectedNode.getUserObject()));
            view.display();
        }
    }

    /**
     * Displays the number of users.
     */
    public void showUserTotal() {
        String message = "User total:  " + this.admin.userTotal();
        JOptionPane.showMessageDialog(this.frame, message, "User Total", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Displays the number of groups.
     */
    public void showGroupTotal() {
        String message = "Group total:  " + this.admin.groupTotal();
        JOptionPane.showMessageDialog(this.frame, message, "Group Total", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Displays the number of messages.
     */
    public void showMessageTotal() {
        String message = "Message total:  " + this.admin.messageTotal();
        JOptionPane.showMessageDialog(this.frame, message, "Message Total", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Display the number of messages with "good" in them.
     */
    public void showGoodMessageTotal() {
        String message = "Good message total:  " + this.admin.goodMessageTotal();
        JOptionPane.showMessageDialog(this.frame, message, "Good Message Total", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Create the JTree used to display Users and Groups
     */
    private void createUserTree() {
        // Create a new JTree with the root node provided by the AdminController object.
        // Use a TreeModel so the tree will update automatically.  Set tree to single selection.  Set tree to display
        // newly added nodes.
        this.userTreeRoot = AdminControllerGUI.getMutableTreeNode(admin.getGroup("root"));
        DefaultTreeModel treeModel = new DefaultTreeModel(this.userTreeRoot);
        this.userTree = new JTree(treeModel);
        this.userTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        this.userTree.setShowsRootHandles(true);
    }

    /**
     * Add a new node to the JTree
     * @param child A2Component that will be contained by the new node.
     */
    private void addA2ComponentNode(A2Component child) {
        DefaultMutableTreeNode parent = null;
        TreePath parentPath = this.userTree.getSelectionPath();
        if (parentPath == null ||
                (((DefaultMutableTreeNode) parentPath.getLastPathComponent()).getUserObject()) instanceof User) {
            // If there is no selection or a User is selected, add the node as a child of the root node.
            parent = this.userTreeRoot;
        } else {
            // Otherwise add the node as a child of the selected node.
            parent = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());
        }
        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
        // Only allow children for Groups.
        if (child instanceof Group) {
            childNode.setAllowsChildren(true);
        } else {
            childNode.setAllowsChildren(false);
        }
        ((DefaultTreeModel) this.userTree.getModel()).insertNodeInto(childNode, parent, parent.getChildCount());
        this.userTree.scrollPathToVisible(new TreePath(childNode.getPath()));
    }

    /**
     * Get a MutableTreeNode containing the given A2Component object.  If the A2Component is a group, add its members
     * as child nodes.
     * @param a
     * @return
     */
    public static DefaultMutableTreeNode getMutableTreeNode(A2Component a) {
        if (a instanceof User) {
            return new DefaultMutableTreeNode(a, false);
        } else if (a instanceof Group) {
            DefaultMutableTreeNode groupNode = new DefaultMutableTreeNode(a, true);
            for (A2Component child : ((Group) a).getMembersSet()) {
                groupNode.add(AdminControllerGUI.getMutableTreeNode(child));
            }
            return groupNode;
        } else {
            return null;
        }
    }

    public JTree getUserTree() {
        return this.userTree;
    }

    public JTextField getCreateUserField() {
        return this.createUserField;
    }

    public JButton getCreateUserButton() {
        return this.createUserButton;
    }
}
