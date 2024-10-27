import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.stream.Collectors;

public class StatsPanel extends JPanel {
    JScrollPane scrollPaneStat;
    static DefaultTableModel model;
    static JTable statTable;
    JScrollPane scrollStat;
    Map<String, Map<String, Object>> dataRef;

    public StatsPanel(Map<String, Map<String, Object>> instanceMap) {
        setLayout(null);
        dataRef = instanceMap;
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Double[] calculatedInitialResults = new Double[dataRef.size()];
        model.addColumn("Stat");
        model.addColumn("Calculated Result");
        statTable = new JTable(model);
        scrollStat = new JScrollPane(statTable);
        scrollStat.setBounds(0, 0, Constants.TABLE_BOUND_WIDTH, Constants.TABLE_BOUND_HEIGHT);
        add(scrollStat);
        //setSize(600, 400);
        setVisible(true);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        //Sets all default activated boxes to true
        boolean[] activatedBoxes = new boolean[4];
        for (int i = 0; i < activatedBoxes.length; i++) {
            activatedBoxes[i] = true;
        }
        //Get the initial sorted data based on all information being visible
        LinkedHashMap<String, Map<String, Object>> sortMap = createSortedMap(activatedBoxes, instanceMap);
        drawToStatTable(sortMap, activatedBoxes);
    }


    /*
    Calculates the information that will be displayed on the stats panel based on the information provided, such as the filtered map
     */
    public static Object[] calculateStatistics(LinkedHashMap<String, Map<String, Object>> sortedMap) {

        Object[] statistics = new Object[5];
        double meanValue = 0.0;
        /*
        Calculate Mean
         */
        int count = 0;
        for (Map.Entry<String, Map<String, Object>> entry : sortedMap.entrySet()) {
            meanValue += Double.parseDouble(entry.getValue().get("HeartRate").toString());
            count++;
        }
        meanValue /= count;
        statistics[0] = meanValue;

        /*
        Calculate Median
         */
        int centerIndex = 0;
        double medianValue = 0.0;

        ArrayList<Double> HeartRateValues = new ArrayList<>();
        for (Map.Entry<String, Map<String, Object>> entry : sortedMap.entrySet()) {
            HeartRateValues.add(Double.parseDouble(entry.getValue().get("HeartRate").toString()));
        }

        Collections.sort(HeartRateValues);

        if (HeartRateValues.size() % 2 == 1) {
            medianValue = HeartRateValues.get(HeartRateValues.size() / 2);
        } else {
            medianValue = (HeartRateValues.get(HeartRateValues.size() / 2) + (HeartRateValues.get(HeartRateValues.size() / 2 - 1))) / 2.0;
        }
        statistics[1] = medianValue;

        /*
        Calculate Range
         */

        System.out.println( HeartRateValues.getLast() + ", " + HeartRateValues.getFirst());
        statistics[2] = HeartRateValues.getFirst();
        statistics[3] = HeartRateValues.getLast();
        statistics[4] = HeartRateValues.getLast() - HeartRateValues.getFirst();

        return statistics;
    }

    /*
    Method that will actually display the information to the stats panel
     */
    public static void drawToStatTable(Map<String, Map<String, Object>> data, boolean[] activatedBoxes) {
        LinkedHashMap<String, Map<String, Object>> filteredSortMap = createSortedMap(activatedBoxes, data);
        Object[] results = calculateStatistics(filteredSortMap);
        model.setRowCount(0);
        for (int i = 0; i < 5; i++) {
            model.addRow(new Object[] {Constants.statisticName[i], results[i]});
        }

        statTable.setModel(model);

    }


    /*
    Creates the sorted map based on what header the user selects.
     */
    private static LinkedHashMap<String, Map<String, Object>> createSortedMap(boolean[] activatedBoxes, Map<String, Map<String, Object>> data) {
        LinkedHashMap<String, Map<String, Object>> sortedMap = new LinkedHashMap<>();
        if (activatedBoxes[0]) {
            Map<String, Map<String, Object>> filteredMap = data.entrySet().stream()
                    .filter(sex -> sex.getValue().get("sex").toString().equalsIgnoreCase("M"))
                    .filter(lessThan -> Double.parseDouble(lessThan.getValue().get("HeartRate").toString()) < 600)
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            Map.Entry::getValue));
            sortedMap.putAll(filteredMap);
        }
        if (activatedBoxes[1]) {
            Map<String, Map<String, Object>> filteredMap = data.entrySet().stream()
                    .filter(sex -> sex.getValue().get("sex").toString().equalsIgnoreCase("F"))
                    .filter(lessThan -> Double.parseDouble(lessThan.getValue().get("HeartRate").toString()) < 600)
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            Map.Entry::getValue));
            sortedMap.putAll(filteredMap);
        }
        if (activatedBoxes[2]) {
            Map<String, Map<String, Object>> filteredMap = data.entrySet().stream()
                    .filter(sex -> sex.getValue().get("sex").toString().equalsIgnoreCase("M"))
                    .filter(greaterThan -> Double.parseDouble(greaterThan.getValue().get("HeartRate").toString()) >= 600)
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            Map.Entry::getValue));
            sortedMap.putAll(filteredMap);
        }
        if (activatedBoxes[3]) {
            Map<String, Map<String, Object>> filteredMap = data.entrySet().stream()
                    .filter(sex -> sex.getValue().get("sex").toString().equalsIgnoreCase("F"))
                    .filter(greaterThan -> Double.parseDouble(greaterThan.getValue().get("HeartRate").toString()) >= 600)
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            Map.Entry::getValue));
            sortedMap.putAll(filteredMap);
        }

        return sortedMap;

    }

    public static void clearTable() {
        model.setRowCount(0);
        statTable.setModel(model);
    }

}
