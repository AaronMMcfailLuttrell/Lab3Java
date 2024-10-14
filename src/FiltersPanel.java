import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Map;

public class FiltersPanel extends JPanel {
    private TablePanel tableRef;
    private Map<String, Map<String, Object>> originMap;
    public FiltersPanel(TablePanel tableRef, Map<String, Map<String, Object>> originMap) {
        this.originMap = originMap;
        this.tableRef = tableRef;
        String[] filterSort = new String[] {"Filter Males", "Filter Females", "Filter Age 19 and below", "Filter Age 20 and above"};
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

        //Filter strings
        String[] filterString = new String[] {"M", "F", "19", "20"};

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
            tableFilter(originMap, filterBoxVisible, filterString[0]);
        });
        add(filterBoxes[0]);

        //Filter Age
        filterBoxes[1].addActionListener(e -> {
            tableFilter(originMap, filterBoxVisible, filterString[1]);
        });
        add(filterBoxes[1]);

        //Filter Sex
        filterBoxes[2].addActionListener(e -> {
            tableFilter(originMap, filterBoxVisible, filterString[2]);
        });
        add(filterBoxes[2]);

        //Filter Heart Rate
        filterBoxes[3].addActionListener(e -> {
            tableFilter(originMap, filterBoxVisible, filterString[3]);
        });
        add(filterBoxes[3]);

    }

    private void tableFilter(Map<String, Map<String, Object>> entryData, boolean[] filterBoxVisible, String FilterString) {
        tableRef.regenTableFilter(entryData, FilterString);
    }

}
