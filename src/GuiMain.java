import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Map;

public class GuiMain {
    public static void main(String[] args) throws IOException {
        JFrame frame = createMainFrame();

        //Manages the map for the file
        String fileLink = "src\\miceData.txt";
        Map<String, Map<String, Object>> entryData = FileHandler.readFile(fileLink);



        //Temp
        ChartPane chartPanel = new ChartPane();
        DetailsPanel detailsPanel = new DetailsPanel();
        TablePanel tablePanel = new TablePanel(entryData, detailsPanel);
        FiltersPanel filtersPanel = new FiltersPanel(tablePanel, entryData, chartPanel);
        frame.add(filtersPanel);
        ExtraPanel extraPanel = new ExtraPanel();
        frame.add(extraPanel);
        frame.add(tablePanel);

        frame.add(detailsPanel);
        frame.add(chartPanel);
        StatsPanel statsPanel = new StatsPanel();
        frame.add(statsPanel);
        frame.revalidate();
        frame.repaint();

    }

    private static JFrame createMainFrame() {
        JFrame mainFrame = new JFrame("Main Frame");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new GridLayout(3,2));
        mainFrame.setSize(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);
        mainFrame.getContentPane().setBackground(Color.darkGray);
        mainFrame.setVisible(true);
        return mainFrame;
    }

}
