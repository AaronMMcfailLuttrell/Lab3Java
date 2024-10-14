import javax.swing.*;
import java.awt.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class ChartPane extends JPanel {

    public ChartPane() {
        setLayout(null);
        setBackground(Color.blue);
        setVisible(true);



        // Create dataset
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Category A", 40);
        dataset.setValue("Category B", 30);
        dataset.setValue("Category C", 20);
        dataset.setValue("Category D", 10);

        // Create chart
        JFreeChart pieChart = ChartFactory.createPieChart(
                "Basic Pie Chart",  // Chart title
                dataset,            // Dataset
                true,               // Include legend
                true,               // Use tooltips
                false               // No URLs
        );

        // Create a ChartPanel and set its bounds
        ChartPanel chartPanel = new ChartPanel(pieChart);
        chartPanel.setBounds(0, 0, Constants.TABLE_BOUND_WIDTH, Constants.TABLE_BOUND_HEIGHT); // Set size and position
        add(chartPanel); // Add ChartPanel to the JPanel




    }



}
