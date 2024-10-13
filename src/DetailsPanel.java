import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

public class DetailsPanel extends JPanel {
    JScrollPane scrollPaneDetail;
    DefaultTableModel model;
    JTable detailTable;
    JScrollPane scrollDetail;
    public DetailsPanel() {
        setLayout(null);
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.addColumn("Key");
        model.addColumn("Value");

        detailTable = new JTable(model);
        scrollDetail = new JScrollPane(detailTable);
        scrollDetail.setBounds(0, 0, Constants.TABLE_BOUND_WIDTH, Constants.TABLE_BOUND_HEIGHT);
        add(scrollDetail);
        //setSize(600, 400);
        setVisible(true);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    public void setDetailInfo(Map<String, Object> instanceTable) {
        model.setRowCount(0);
        for (Map.Entry<String, Object> entry : instanceTable.entrySet()) {
            model.addRow(new Object[]{entry.getKey(), entry.getValue()}); // Use Object[] instead of String[]
        }
        detailTable.setModel(model);
    }
}
