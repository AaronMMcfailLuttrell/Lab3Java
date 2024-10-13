import javax.swing.*;
import java.awt.*;

public class FiltersPanel extends JPanel {
    public FiltersPanel() {
        String[] filterSort = new String[] {"Filter Cage ID", "Filter Age", "Filter Sex", "Filter Heart Rate"};
        setLayout(new GridLayout(1, 4));
        setBackground(Color.WHITE);
        setVisible(true);
        JCheckBox box = new JCheckBox();
        box.setText("Filter Cage ID");

        for (int i = 0; i < filterSort.length; i++) {
            JCheckBox checkBox = new JCheckBox(filterSort[i]);
            add(checkBox);
        }
    }
}
