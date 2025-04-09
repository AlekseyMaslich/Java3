package task02;

/**
 * The {@code Main} class is the entry point of the application.
 * It initializes the application by creating a {@code Dialog} object
 * and starts the interactive menu loop by calling the {@code run()} method.
 */
public class Main {

    /**
     * The main method that serves as the entry point of the application.
     * It creates a {@code Dialog} object with a {@code ViewableResult} object
     * and starts the interactive menu loop.
     *
     * @param args the command-line arguments (not used in this application)
     */
    public static void main(String[] args) {
        Dialog dialog = new Dialog(new ViewableResult().getView());
        dialog.run();
    }
}
