import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Filter;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;

public class TablePanel extends JPanel {

    JTable table;
    JScrollPane scrollPane;
    DefaultTableModel model;
    int[] values;
    public TablePanel(Map<String, Map<String, Object>> tableData, DetailsPanel detailsPanel) {
        setLayout(null);
        values = new int[4];
        for (int i = 0; i < values.length; i++) {
            values[i] = 0;
        }
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("Cage ID");
        model.addColumn("Age (months)");
        model.addColumn("Sex");
        model.addColumn("Heart Rate (BPM)");

        for (Map.Entry<String, Map<String, Object>> entry : tableData.entrySet()) {
            Map<String, Object> innerMap = entry.getValue();
            model.addRow(new Object[]{innerMap.get("cageID"), innerMap.get("age"), innerMap.get("sex"), innerMap.get("HeartRate")});
        }

        table = new JTable(model);
        scrollPane = new JScrollPane(table);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        scrollPane.setBounds(0, 0, Constants.TABLE_BOUND_WIDTH, Constants.TABLE_BOUND_HEIGHT);
        add(scrollPane);
        //setSize(600, 400);
        setVisible(true);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));


        //This is for the details panel
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                for (Map.Entry<String, Map<String, Object>> entry : tableData.entrySet()) {
                    Map<String, Object> innerMap = entry.getValue();
                    //If the selected column equals the id of a specific map entry, get that map entry to then manipulate

                    if (selectedRow != -1 && innerMap.get("cageID") == table.getValueAt(selectedRow, 0).toString()) {

                        //Put map into generation of information to Detail Pane
                        detailsPanel.setDetailInfo(innerMap);
                        detailsPanel.revalidate();
                        detailsPanel.repaint();
                    }
                }
            }
        });

        JTableHeader header = table.getTableHeader();
        header.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = table.columnAtPoint(e.getPoint());
                if (col == 0) {
                    LinkedHashMap<String, Map<String, Object>> sortedMap = tableData.entrySet().stream()
                            .sorted((inst1, inst2) -> {
                                String id1 = inst1.getValue().get("cageID").toString();
                                String id2 = inst2.getValue().get("cageID").toString();
                                return id1.compareTo(id2);
                            })
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                    (oldValue, newValue) -> oldValue,
                                    LinkedHashMap::new));
                    regenTableFilter(sortedMap, Constants.filterString, FiltersPanel.getBoolValues());

                } else if (col == 1) {
                    LinkedHashMap<String, Map<String, Object>> sortedMap = tableData.entrySet().stream()
                            .sorted((inst1, inst2) -> {
                                Integer id1 = Integer.parseInt(inst1.getValue().get("age").toString());
                                Integer id2 = Integer.parseInt(inst2.getValue().get("age").toString());
                                return id1.compareTo(id2);
                            })
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                    (oldValue, newValue) -> oldValue,
                                    LinkedHashMap::new));
                    regenTableFilter(sortedMap, Constants.filterString, FiltersPanel.getBoolValues());
                } else if (col == 2) {
                    LinkedHashMap<String, Map<String, Object>> sortedMap = tableData.entrySet().stream()
                            .sorted((inst1, inst2) -> {
                                String id1 = inst1.getValue().get("sex").toString();
                                String id2 = inst2.getValue().get("sex").toString();
                                return id1.compareTo(id2);
                            })
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                    (oldValue, newValue) -> oldValue,
                                    LinkedHashMap::new));
                    regenTableFilter(sortedMap, Constants.filterString, FiltersPanel.getBoolValues());
                } else if (col == 3) {
                    LinkedHashMap<String, Map<String, Object>> sortedMap = tableData.entrySet().stream()
                            .sorted((inst1, inst2) -> {
                                Double id1 = Double.parseDouble(inst1.getValue().get("HeartRate").toString());
                                Double id2 = Double.parseDouble(inst2.getValue().get("HeartRate").toString());
                                return id1.compareTo(id2);
                            })
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                    (oldValue, newValue) -> oldValue,
                                    LinkedHashMap::new));
                    regenTableFilter(sortedMap, Constants.filterString, FiltersPanel.getBoolValues());
                }


            }
        });
    }

    public JTable getTable() {
        return table;
    }

    public void visibilityColumnSetter(int colVal, boolean visible) {
        TableColumn column = table.getColumnModel().getColumn(colVal);
        if (visible) {
            // Show the column
            column.setMinWidth(0); // Set to desired width
            column.setMaxWidth(Integer.MAX_VALUE);
            column.setPreferredWidth(100);
        } else {
            // Hide the column
            column.setMinWidth(0);
            column.setMaxWidth(0);
            column.setPreferredWidth(0);
        }
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        table.revalidate(); // Revalidate the table to update the display
        table.repaint(); // Repaint the table to reflect changes

    }


    public int[] regenTableFilter(Map<String, Map<String, Object>> tableData, String[] FilterString, boolean[] filterBoxVisible) {

        //Create a counter that will pass to the generation of the chart
        int[] values = new int[4];
        for (int i = 0; i < values.length; i++) {
            values[i] = 0;
        }
        this.model.setRowCount(0);
        if (filterBoxVisible[0]) {
            Map<String, Map<String, Object>> filteredMap = tableData.entrySet().stream()
                    .filter(sex -> sex.getValue().get("sex").toString().equalsIgnoreCase("M"))
                    .filter(lessThan -> Double.parseDouble(lessThan.getValue().get("HeartRate").toString()) < 600)
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            Map.Entry::getValue));
            values[0] = filteredMap.size();
            addInstancesToTable(filteredMap);
        }
        if (filterBoxVisible[1]) {
            Map<String, Map<String, Object>> filteredMap = tableData.entrySet().stream()
                    .filter(sex -> sex.getValue().get("sex").toString().equalsIgnoreCase("F"))
                    .filter(lessThan -> Double.parseDouble(lessThan.getValue().get("HeartRate").toString()) < 600)
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            Map.Entry::getValue));
            values[1] = filteredMap.size();
            addInstancesToTable(filteredMap);
        }
        if (filterBoxVisible[2]) {
            Map<String, Map<String, Object>> filteredMap = tableData.entrySet().stream()
                    .filter(sex -> sex.getValue().get("sex").toString().equalsIgnoreCase("M"))
                    .filter(greaterThan -> Double.parseDouble(greaterThan.getValue().get("HeartRate").toString()) >= 600)
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            Map.Entry::getValue));
            values[2] = filteredMap.size();
            addInstancesToTable(filteredMap);
        }
        if (filterBoxVisible[3]) {
            Map<String, Map<String, Object>> filteredMap = tableData.entrySet().stream()
                    .filter(sex -> sex.getValue().get("sex").toString().equalsIgnoreCase("F"))
                    .filter(greaterThan -> Double.parseDouble(greaterThan.getValue().get("HeartRate").toString()) >= 600)
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            Map.Entry::getValue));
            values[3] = filteredMap.size();
            addInstancesToTable(filteredMap);
        }

        if (!(filterBoxVisible[0] || filterBoxVisible[1] || filterBoxVisible[2] || filterBoxVisible[3])) {

        }





        return values;
    }

    public int[] regenTableFilter(LinkedHashMap<String, Map<String, Object>> tableData, String[] FilterString, boolean[] filterBoxVisible) {
        Map<String, Map<String, Object>> listOfVisibleEntries = new HashMap<>();
        //Create a counter that will pass to the generation of the chart
        int[] values = new int[4];
        for (int i = 0; i < values.length; i++) {
            values[i] = 0;
        }
        this.model.setRowCount(0);
        if (filterBoxVisible[0]) {
            Map<String, Map<String, Object>> filteredMap = tableData.entrySet().stream()
                    .filter(sex -> sex.getValue().get("sex").toString().equalsIgnoreCase("M"))
                    .filter(lessThan -> Double.parseDouble(lessThan.getValue().get("HeartRate").toString()) < 600)
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            Map.Entry::getValue));
            values[0] = filteredMap.size();
            listOfVisibleEntries.putAll(filteredMap);
        }
        if (filterBoxVisible[1]) {
            Map<String, Map<String, Object>> filteredMap = tableData.entrySet().stream()
                    .filter(sex -> sex.getValue().get("sex").toString().equalsIgnoreCase("F"))
                    .filter(lessThan -> Double.parseDouble(lessThan.getValue().get("HeartRate").toString()) < 600)
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            Map.Entry::getValue));
            values[1] = filteredMap.size();
            listOfVisibleEntries.putAll(filteredMap);
        }
        if (filterBoxVisible[2]) {
            Map<String, Map<String, Object>> filteredMap = tableData.entrySet().stream()
                    .filter(sex -> sex.getValue().get("sex").toString().equalsIgnoreCase("M"))
                    .filter(greaterThan -> Double.parseDouble(greaterThan.getValue().get("HeartRate").toString()) >= 600)
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            Map.Entry::getValue));
            values[2] = filteredMap.size();
            listOfVisibleEntries.putAll(filteredMap);
        }
        if (filterBoxVisible[3]) {
            Map<String, Map<String, Object>> filteredMap = tableData.entrySet().stream()
                    .filter(sex -> sex.getValue().get("sex").toString().equalsIgnoreCase("F"))
                    .filter(greaterThan -> Double.parseDouble(greaterThan.getValue().get("HeartRate").toString()) >= 600)
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            Map.Entry::getValue));
            values[3] = filteredMap.size();
            listOfVisibleEntries.putAll(filteredMap);
        }

        if (!(filterBoxVisible[0] || filterBoxVisible[1] || filterBoxVisible[2] || filterBoxVisible[3])) {

        }

        LinkedHashMap<String, Map<String, Object>> placeholder = new LinkedHashMap<>();
        placeholder.putAll(tableData);

        for (Map.Entry<String, Map<String, Object>> entry : tableData.entrySet()) {
            if (!listOfVisibleEntries.containsKey(entry.getKey())) {
                placeholder.remove(entry.getKey());
            }
        }

        addInstancesToTable(placeholder);

        for (Map.Entry<String, Map<String, Object>> entry : placeholder.entrySet()) {
            System.out.println(entry.getValue().get("cageID"));
        }


        return values;
    }

    private void addInstancesToTable(Map<String, Map<String, Object>> tableData) {
        for (Map.Entry<String, Map<String, Object>> entry : tableData.entrySet()) {
            model.addRow(new Object[]{entry.getValue().get("cageID"), entry.getValue().get("age"), entry.getValue().get("sex"), entry.getValue().get("HeartRate")});
        }
    }

    private void addInstancesToTable(LinkedHashMap<String, Map<String, Object>> tableData) {
        for (Map.Entry<String, Map<String, Object>> entry : tableData.entrySet()) {
            model.addRow(new Object[]{entry.getValue().get("cageID"), entry.getValue().get("age"), entry.getValue().get("sex"), entry.getValue().get("HeartRate")});
        }
    }

}
