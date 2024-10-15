import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Filter;

public class TablePanel extends JPanel {

    JTable table;
    JScrollPane scrollPane;
    DefaultTableModel model;
    public TablePanel(Map<String, Map<String, Object>> tableData, DetailsPanel detailsPanel) {
        setLayout(null);
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

    public void regenTableFilter(Map<String, Map<String, Object>> tableData, String[] FilterString, boolean[] filterBoxVisible) {
        /*
        this.model.setRowCount(0);
        for (Map.Entry<String, Map<String, Object>> entry : tableData.entrySet()) {
            Map<String, Object> innerMap = entry.getValue();


            if (filterBoxVisible[0]) {
                if (FileHandler.isChar(FilterString[0])) {
                    char filt = FilterString[0].charAt(0);
                    if (innerMap.get("sex").toString().charAt(0) == filt) {

                        model.addRow(new Object[]{innerMap.get("cageID"), innerMap.get("age"), innerMap.get("sex"), innerMap.get("HeartRate")});

                    }
                }
            }
            if (filterBoxVisible[1]) {
                if (FileHandler.isChar(FilterString[1])) {
                    char filt = FilterString[1].charAt(0);
                    if (innerMap.get("sex").toString().charAt(0) == filt) {
                        model.addRow(new Object[]{innerMap.get("cageID"), innerMap.get("age"), innerMap.get("sex"), innerMap.get("HeartRate")});
                    }
                }
            }
            if (filterBoxVisible[2]) {
                if (FileHandler.isDouble(FilterString[2])) {
                    double filt = Double.parseDouble(FilterString[2]);
                    if (Double.parseDouble(innerMap.get("HeartRate").toString()) < filt) {
                        model.addRow(new Object[]{innerMap.get("cageID"), innerMap.get("age"), innerMap.get("sex"), innerMap.get("HeartRate")});
                    }
                }
            }
            if (filterBoxVisible[3]) {
                if (FileHandler.isDouble(FilterString[3])) {
                    double filt = Double.parseDouble(FilterString[3]);
                    if (Double.parseDouble(innerMap.get("HeartRate").toString()) >= filt) {
                        model.addRow(new Object[]{innerMap.get("cageID"), innerMap.get("age"), innerMap.get("sex"), innerMap.get("HeartRate")});
                    }
                }
            }

            if (filterBoxVisible[0] == false && filterBoxVisible[1] == false && filterBoxVisible[2] == false && filterBoxVisible[3] == false) {
                model.addRow(new Object[]{innerMap.get("cageID"), innerMap.get("age"), innerMap.get("sex"), innerMap.get("HeartRate")});
            }



        } */




        this.model.setRowCount(0); // Clear the table

        for (Map.Entry<String, Map<String, Object>> entry : tableData.entrySet()) {
            Map<String, Object> innerMap = entry.getValue();

            // Initialize a flag that will only be true if all active filters are satisfied
            boolean matchesFilters = true;

            // Filter for sex (male or female)
            if ((filterBoxVisible[0] && filterBoxVisible[1]) == false) { // If both are selected, show all
                if (filterBoxVisible[0] && !innerMap.get("sex").toString().equalsIgnoreCase("M")) {
                    matchesFilters = false;
                }
                if (filterBoxVisible[1] && !innerMap.get("sex").toString().equalsIgnoreCase("F")) {
                    matchesFilters = false;
                }
            }

            // Filter for heart rate below 600
            if ((filterBoxVisible[2] && filterBoxVisible[3]) == false) { // If both are selected, show all
                if (filterBoxVisible[2] && FileHandler.isDouble(FilterString[2])) {
                    double filt = Double.parseDouble(FilterString[2]);
                    if (Double.parseDouble(innerMap.get("HeartRate").toString()) >= filt) {
                        matchesFilters = false;
                    }
                }

                // Filter for heart rate above 600
                if (filterBoxVisible[3] && FileHandler.isDouble(FilterString[3])) {
                    double filt = Double.parseDouble(FilterString[3]);
                    if (Double.parseDouble(innerMap.get("HeartRate").toString()) < filt) {
                        matchesFilters = false;
                    }
                }
            }

            // If all active filters match, add the row
            if (matchesFilters) {
                model.addRow(new Object[]{innerMap.get("cageID"), innerMap.get("age"), innerMap.get("sex"), innerMap.get("HeartRate")});
            }

            // If no filters are active, show all data
            if (!filterBoxVisible[0] && !filterBoxVisible[1] && !filterBoxVisible[2] && !filterBoxVisible[3]) {
                model.addRow(new Object[]{innerMap.get("cageID"), innerMap.get("age"), innerMap.get("sex"), innerMap.get("HeartRate")});
            }
        }



    }

}
