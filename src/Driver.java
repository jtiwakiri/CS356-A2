/**
 * Created by Joshua on 11/4/2017.
 */
public class Driver {
    public static void main (String[] args) {
        AdminController admin = AdminController.getInstance();
        AdminControllerGUI a1 = new AdminControllerGUI(admin);
        a1.display();
    }
}
