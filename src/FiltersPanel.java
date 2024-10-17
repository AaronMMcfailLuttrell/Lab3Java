import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class FiltersPanel extends JPanel {
    private TablePanel tableRef;
    private static boolean[] boxesSelected;
    private Map<String, Map<String, Object>> originMap;
    private LinkedHashMap<String, Map<String, Object>> filterMap;
    public FiltersPanel(TablePanel tableRef, Map<String, Map<String, Object>> originMap, ChartPane pieSet) {
        this.originMap = originMap;
        this.tableRef = tableRef;
        String[] filterSort = new String[] {"Filter Males, Heart Rate below 600", "Filter Females, Heart Rate below 600", "Filter Males, Heart Rate above 600", "Filter Females, Heart Rate above 600"};
        setLayout(new GridLayout(6, 1));
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
            filterBoxes[i].setSelected(true);
        }

        boxesSelected = new boolean[filterSort.length];
        for (int i = 0; i < filterSort.length; i++) {
            boxesSelected[i] = true;
        }

        //addActionListener made it not possible for me to do this within a loop
        //Filter Cage ID
        filterBoxes[0].addActionListener(e -> {
            boxesSelected[0] = filterBoxes[0].isSelected();
            int[] placeholder1 = tableFilter(originMap, boxesSelected, Constants.filterString);
            pieSet.buildPieChart(placeholder1);
            genStatPane(boxesSelected);
        });
        add(filterBoxes[0]);

        //Filter Age
        filterBoxes[1].addActionListener(e -> {
            boxesSelected[1] = filterBoxes[1].isSelected();
            int[] placeholder2 = tableFilter(originMap, boxesSelected, Constants.filterString);
            pieSet.buildPieChart(placeholder2);
            genStatPane(boxesSelected);
        });
        add(filterBoxes[1]);

        //Filter Sex
        filterBoxes[2].addActionListener(e -> {
            boxesSelected[2] = filterBoxes[2].isSelected();
            int[] placeholder3 = tableFilter(originMap, boxesSelected, Constants.filterString);
            pieSet.buildPieChart(placeholder3);
            genStatPane(boxesSelected);
        });
        add(filterBoxes[2]);

        //Filter Heart Rate
        filterBoxes[3].addActionListener(e -> {
            boxesSelected[3] = filterBoxes[3].isSelected();
            int[] placeholder4 = tableFilter(originMap, boxesSelected, Constants.filterString);
            pieSet.buildPieChart(placeholder4);
            genStatPane(boxesSelected);
        });
        add(filterBoxes[3]);

    }

    private int[] tableFilter(Map<String, Map<String, Object>> entryData, boolean[] filterBoxVisible, String[] FilterString) {
        int[] values = tableRef.regenTableFilter(entryData, FilterString, filterBoxVisible);
        return values;
    }

    public static boolean[] getBoolValues() {
        return boxesSelected;
    }

    private LinkedHashMap<String, Map<String, Object>> setFilteredMap(Map<String, Map<String, Object>> updatedMap) {
        LinkedHashMap<String, Map<String, Object>> sortedMap = updatedMap.entrySet().stream()
                .sorted((inst1, inst2) -> {
                    Double id1 = Double.parseDouble(inst1.getValue().get("HeartRate").toString());
                    Double id2 = Double.parseDouble(inst2.getValue().get("HeartRate").toString());
                    return id1.compareTo(id2);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new));
        return sortedMap;
    }

    private void genStatPane(boolean[] boxesSelected) {
        if (boxesSelected[0] || boxesSelected[1] || boxesSelected[2] || boxesSelected[3]) {
            StatsPanel.drawToStatTable(originMap, boxesSelected);
        } else {
            StatsPanel.clearTable();
        }
    }

}
