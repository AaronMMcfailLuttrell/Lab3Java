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
        dataset = new DefaultPieDataset();
        chartPanel = new ChartPanel(chart);

        //Create a new map for temporary use, creates an initial display for all filters being selected
        Map<String, Map<String, Object>> testMap = FileHandler.readFile("src\\miceData.txt");
        String[] filterString = new String[] {"M", "F", "600", "600"};
        boolean[] allSelected = new boolean[4];
        //allSelected by default will be true to pass into the function that generates the chart
        for (int i = 0; i < allSelected.length; i++) {
            allSelected[i] = true;
        }
        TablePanel panel = new TablePanel(testMap, new DetailsPanel());
        //Construction of ChartPane assumes all boxes are true, as constructor for Filters checks all boxes by default
        totals = panel.regenTableFilter(testMap, filterString, allSelected);
        buildPieChart(totals);
    }

    /*
    CollectTotals: Once the filtering has properly been done with the information, this calls as a sort of setter to build the chart.
     */
    public void CollectTotals(int[] totals) {
        for (int i = 0; i < totals.length; i++) {
            this.totals[i] = totals[i];
        }
    }

    /*
    Name is sort of self explanitory, takes all information in about what is gathered and builds a chart based on it.
     */
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
