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




        //chartPanel constructors always start with boxes activated
        boolean[] allActivated = new boolean[4];
        for (int g = 0; g < allActivated.length; g++) {
            allActivated[g] = true;
        }

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
        StatsPanel statsPanel = new StatsPanel(entryData);
        frame.add(statsPanel);
        frame.revalidate();
        frame.repaint();

    }


    /*
    Intermediate method called by the constructor to initialize the window.
     */
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
