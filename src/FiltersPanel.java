import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Map;

public class FiltersPanel extends JPanel {
    private TablePanel tableRef;
    private Map<String, Map<String, Object>> originMap;
    public FiltersPanel(TablePanel tableRef, Map<String, Map<String, Object>> originMap, ChartPane pieSet) {
        this.originMap = originMap;
        this.tableRef = tableRef;
        String[] filterSort = new String[] {"Filter Males", "Filter Females", "Filter Heart Rate below 600", "Filter Heart Rate 600 and above"};
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
        String[] filterString = new String[] {"M", "F", "600", "600"};

        JCheckBox[] filterBoxes = new JCheckBox[filterSort.length];
        for (int i = 0; i < filterSort.length; i++) {
            filterBoxes[i] = new JCheckBox(filterSort[i]);
        }

        boolean[] boxesSelected = new boolean[filterSort.length];
        for (int i = 0; i < filterSort.length; i++) {
            boxesSelected[i] = false;
        }

        //addActionListener made it not possible for me to do this within a loop
        //Filter Cage ID
        filterBoxes[0].addActionListener(e -> {
            boxesSelected[0] = filterBoxes[0].isSelected();
            int[] placeholder1 = tableFilter(originMap, boxesSelected, filterString);
            pieSet.buildPieChart(placeholder1);
        });
        add(filterBoxes[0]);

        //Filter Age
        filterBoxes[1].addActionListener(e -> {
            boxesSelected[1] = filterBoxes[1].isSelected();
            int[] placeholder2 = tableFilter(originMap, boxesSelected, filterString);
            pieSet.buildPieChart(placeholder2);
        });
        add(filterBoxes[1]);

        //Filter Sex
        filterBoxes[2].addActionListener(e -> {
            boxesSelected[2] = filterBoxes[2].isSelected();
            int[] placeholder3 = tableFilter(originMap, boxesSelected, filterString);
            pieSet.buildPieChart(placeholder3);
        });
        add(filterBoxes[2]);

        //Filter Heart Rate
        filterBoxes[3].addActionListener(e -> {
            boxesSelected[3] = filterBoxes[3].isSelected();
            int[] placeholder4 = tableFilter(originMap, boxesSelected, filterString);
            pieSet.buildPieChart(placeholder4);
        });
        add(filterBoxes[3]);

    }

    private int[] tableFilter(Map<String, Map<String, Object>> entryData, boolean[] filterBoxVisible, String[] FilterString) {
        int[] values = tableRef.regenTableFilter(entryData, FilterString, filterBoxVisible);
        for (int i = 0; i < filterBoxVisible.length; i++) {
            System.out.println(filterBoxVisible[i]);
        }
        return values;
    }

}
