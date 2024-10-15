import javax.swing.*;
import java.awt.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class ChartPane extends JPanel {

    int[] totals = new int[4];
    String[] pieSectionNames = new String[] {"Male, Heart rate less than 600", "Female, Heart rate less than 600", "Male, Heart rate greater than 600", "Female, Heart rate greater than 600"};
    DefaultPieDataset dataset;
    JFreeChart chart;
    ChartPanel chartPanel;
    public ChartPane() {
        setLayout(null);
        setBackground(Color.blue);
        setVisible(true);
        for (int i = 0; i < totals.length; i++) {
            totals[i] = 0;
        }
    }

    public void CollectTotals(int[] totals) {
        for (int i = 0; i < totals.length; i++) {
            totals[i] += totals[i];
        }
    }

    public void buildPieChart() {
        dataset = new DefaultPieDataset();
        for (int i = 0; i < pieSectionNames.length; i++) {
            dataset.setValue(pieSectionNames[i], totals[i]);
        }

        chart = ChartFactory.createPieChart(
                "Basic Pie Chart",  // Chart title
                dataset,            // Dataset
                true,               // Include legend
                true,               // Use tooltips
                false               // No URLs
        );

        chartPanel = new ChartPanel(chart);
        chartPanel.setBounds(0, 0, Constants.TABLE_BOUND_WIDTH, Constants.TABLE_BOUND_HEIGHT); // Set size and position
        add(chartPanel); // Add ChartPanel to the JPanel
    }
}
