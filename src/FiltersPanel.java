import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class FiltersPanel extends JPanel {
    private TablePanel tableRef;
    public FiltersPanel(TablePanel tableRef) {
        this.tableRef = tableRef;
        String[] filterSort = new String[] {"Filter Cage ID", "Filter Age", "Filter Sex", "Filter Heart Rate"};
        setLayout(new GridLayout(2, 4));
        setBackground(Color.WHITE);
        setVisible(true);
        JTextField textBox = new JTextField();
        JPanel[] emptyPanels = new JPanel[filterSort.length - 1];
        for (int i = 0; i < 4; i++) {
            if (i == 0) {
                textBox = new JTextField("Filters Panel:");
                textBox.setEditable(false);
                add(textBox);
            } else {
                emptyPanels[i - 1] = new JPanel();
                add(emptyPanels[i - 1]);
            }

        }

        JCheckBox[] filterBoxes = new JCheckBox[filterSort.length];
        for (int i = 0; i < filterSort.length; i++) {
            filterBoxes[i] = new JCheckBox(filterSort[i]);
        }

        boolean[] filterBoxVisible = new boolean[filterSort.length];
        for (int i = 0; i < filterSort.length; i++) {
            filterBoxVisible[i] = false;
        }

        //addActionListener made it not possible for me to do this within a loop
        //Filter Cage ID
        filterBoxes[0].addActionListener(e -> {
            tableFilter(0, filterBoxVisible);
        });
        add(filterBoxes[0]);

        //Filter Age
        filterBoxes[1].addActionListener(e -> {
            tableFilter(1, filterBoxVisible);
        });
        add(filterBoxes[1]);

        //Filter Sex
        filterBoxes[2].addActionListener(e -> {
            tableFilter(2, filterBoxVisible);
        });
        add(filterBoxes[2]);

        //Filter Heart Rate
        filterBoxes[3].addActionListener(e -> {
            tableFilter(3, filterBoxVisible);
        });
        add(filterBoxes[3]);

    }

    private void tableFilter(int colIndex, boolean[] filterBoxVisible) {
        tableRef.visibilityColumnSetter(colIndex, filterBoxVisible[colIndex]);
        if (filterBoxVisible[colIndex]) {
            filterBoxVisible[colIndex] = false;
        } else {
            filterBoxVisible[colIndex] = true;
        }
    }

}
