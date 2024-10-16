import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Filter;
import java.util.stream.Collectors;

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

    public static int[] regenTableFilter(Map<String, Map<String, Object>> tableData, String[] FilterString, boolean[] filterBoxVisible) {
        /*
          Generate Panels based on filter
        */
        //Create a counter that will pass to the generation of the chart
        int[] values = new int[4];
        for (int i = 0; i < values.length; i++) {
            values[i] = 0;
        }

        if (filterBoxVisible[0]) {
            Map<String, Map<String, Object>> filteredMap = tableData.entrySet().stream()
                    .filter(sex -> sex.getValue().get("sex").toString().equalsIgnoreCase("M"))
                    .filter(lessThan -> Double.parseDouble(lessThan.getValue().get("HeartRate").toString()) < 600)
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            Map.Entry::getValue));
            values[0] = filteredMap.size();
            System.out.println(values[0]);
        }
        if (filterBoxVisible[1]) {
            Map<String, Map<String, Object>> filteredMap = tableData.entrySet().stream()
                    .filter(sex -> sex.getValue().get("sex").toString().equalsIgnoreCase("F"))
                    .filter(lessThan -> Double.parseDouble(lessThan.getValue().get("HeartRate").toString()) < 600)
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            Map.Entry::getValue));
            values[1] = filteredMap.size();
            System.out.println(values[1]);
        }
        if (filterBoxVisible[2]) {
            Map<String, Map<String, Object>> filteredMap = tableData.entrySet().stream()
                    .filter(sex -> sex.getValue().get("sex").toString().equalsIgnoreCase("M"))
                    .filter(greaterThan -> Double.parseDouble(greaterThan.getValue().get("HeartRate").toString()) >= 600)
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            Map.Entry::getValue));
            values[2] = filteredMap.size();
            System.out.println(values[2]);
        }
        if (filterBoxVisible[3]) {
            Map<String, Map<String, Object>> filteredMap = tableData.entrySet().stream()
                    .filter(sex -> sex.getValue().get("sex").toString().equalsIgnoreCase("F"))
                    .filter(greaterThan -> Double.parseDouble(greaterThan.getValue().get("HeartRate").toString()) >= 600)
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            Map.Entry::getValue));
            values[3] = filteredMap.size();
            System.out.println(values[3]);
        }







        return values;
    }

}
