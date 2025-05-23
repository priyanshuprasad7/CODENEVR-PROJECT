
import javax.swing.SwingUtilities;
import ui.MainUI;

public class Main {
    public static void main(String[] args) {
        // Launch the UI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new MainUI());
    }
}
