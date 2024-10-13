import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.Map;

public class TablePanel extends JPanel {

    JTable table;
    JScrollPane scrollPane;
    public TablePanel(Map<String, Map<String, Object>> tableData) {
        setLayout(null);
        DefaultTableModel model = new DefaultTableModel();
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
        scrollPane.setBounds(0, 0, Constants.FRAME_WIDTH/2, Constants.FRAME_HEIGHT/3 - 20);
        add(scrollPane);
        //setSize(600, 400);
        setVisible(true);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

    }

    public JTable getTable() {
        return table;
    }

}
