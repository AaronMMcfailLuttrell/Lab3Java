import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
    public ChartPane() throws IOException {
        setLayout(null);
        setBackground(Color.blue);
        setVisible(true);
        for (int i = 0; i < totals.length; i++) {
            totals[i] = 0;
        }
        dataset = new DefaultPieDataset();
        chartPanel = new ChartPanel(chart);

        //Test
        Map<String, Map<String, Object>> testMap = FileHandler.readFile("src\\miceData.txt");

        for (Map.Entry<String, Map<String, Object>> entry : testMap.entrySet()) {
            String key = entry.getKey();
            Map<String, Object> innerMap = entry.getValue();
            if (innerMap.get("sex").toString().equals("M") && (Double.parseDouble(innerMap.get("HeartRate").toString()) < 600)) {
                totals[0]++;
            } else if (innerMap.get("sex").toString().equals("M") && (Double.parseDouble(innerMap.get("HeartRate").toString()) >= 600)) {
                totals[2]++;
            } else if (innerMap.get("sex").toString().equals("F") && (Double.parseDouble(innerMap.get("HeartRate").toString()) < 600)) {
                totals[1]++;
            } else if (innerMap.get("sex").toString().equals("F") && (Double.parseDouble(innerMap.get("HeartRate").toString()) >= 600)) {
                totals[3]++;
            }
        }

        buildPieChart(totals);
    }

    public void CollectTotals(int[] totals) {
        for (int i = 0; i < totals.length; i++) {
            this.totals[i] = totals[i];
        }
    }

    public void buildPieChart(int[] pieData) {

        CollectTotals(pieData);
        dataset.clear();

        for (int i = 0; i < pieSectionNames.length; i++) {
            if (totals[i] != 0) {
                dataset.setValue(pieSectionNames[i], totals[i]);
            }
        }

        chart = ChartFactory.createPieChart(
                "Basic Pie Chart",  // Chart title
                dataset,            // Dataset
                true,               // Include legend
                true,               // Use tooltips
                false               // No URLs
        );

        chartPanel.setChart(chart);
        chartPanel.setBounds(0, 0, Constants.TABLE_BOUND_WIDTH, Constants.TABLE_BOUND_HEIGHT); // Set size and position
        add(chartPanel); // Add ChartPanel to the JPanel
    }

}
