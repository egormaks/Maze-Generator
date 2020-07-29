import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AnalysisPanel extends JFrame {
    private static final int START_SIZE = 0;
    private static final int DEFAULT_WIDTH = 865;
    private static final int DEFAULT_HEIGHT = 504;
    private static final Color PRIM_COLOR = Color.RED;
    private static final Color KRUSKAL_COLOR = Color.BLUE;
    private static final Color ELLER_COLOR = Color.GREEN;
    private static final Color DEPTH_COLOR = Color.ORANGE;

    private JFreeChart chart;
    private JCheckBox[] algorithms;
    private JTextField numRuns;

    public AnalysisPanel() {
        super("Analysis");
        this.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        algorithms = new JCheckBox[Driver.NO_ALGOS];
        algorithms[0] = new JCheckBox("Depth First");
        algorithms[0].setForeground(DEPTH_COLOR);
        algorithms[1] = new JCheckBox("Eller");
        algorithms[1].setForeground(ELLER_COLOR);
        algorithms[2] = new JCheckBox("Kruskal");
        algorithms[2].setForeground(KRUSKAL_COLOR);
        algorithms[3] = new JCheckBox("Prim");
        algorithms[3].setForeground(PRIM_COLOR);
        JButton initiate = new JButton("Start Simulation");
        initiate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performAnalysis();
            }
        });

        JPanel checkList = new JPanel();
        checkList.setName("Algorithms");
        checkList.setLayout(new GridLayout(1, Driver.NO_ALGOS, 0, 10));
        for (int i = 0; i < Driver.NO_ALGOS; i++) {
            checkList.add(algorithms[i]);
        }

        JPanel simulatorOptions = new JPanel();
        JLabel label = new JLabel("Maximum size");
        numRuns = new JTextField("10");
        numRuns.setEnabled(true);
        numRuns.setSize(30, 10);
        simulatorOptions.add(label);
        simulatorOptions.add(numRuns);
        simulatorOptions.add(checkList);
        simulatorOptions.add(initiate);

        this.add(simulatorOptions, BorderLayout.EAST);
        this.pack();
    }

    public void performAnalysis() {
        int num;
        try {
            num = Integer.valueOf(numRuns.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid size input.");
            return;
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries prim = new XYSeries("Prim");
        XYSeries kruskal = new XYSeries("Kruskal");
        XYSeries eller = new XYSeries("Eller");
        XYSeries depth = new XYSeries("Depth First");
        for (int i = 1; i <= num; i++) {
            int grid[][] = new int[2 * (START_SIZE + i) + 1][2 * (START_SIZE + i) + 1];
            for (int j = 0; j < grid.length; j++)
                for (int k = 0; k < grid[0].length; k++)
                    grid[j][k] = 1;
            int startX = (int)(Math.random() * grid[0].length);
            if (startX % 2 == 0) {
                if (startX == 0) {
                    startX++;
                } else if (startX == grid[0].length - 1) {
                    startX--;
                } else startX--;
            }
            int startY = (int)(Math.random() * grid.length);
            if (startY % 2 == 0) {
                if (startY == 0) {
                    startY++;
                } else if (startX == grid.length - 1) {
                    startY--;
                } else startY--;
            }

            long startTime;
            if (algorithms[0].isSelected()) {
                startTime = System.nanoTime();
                MazeAlgorithms.generateDepthFirstRecursive(grid, startX, startY);
                depth.add(START_SIZE + i, System.nanoTime() - startTime);
                startTime = System.nanoTime();
            }
            if (algorithms[1].isSelected()) {
                startTime = System.nanoTime();
                MazeAlgorithms.generateEller(grid);
                eller.add(START_SIZE + i, System.nanoTime() - startTime);
            }
            if (algorithms[2].isSelected()) {
                startTime = System.nanoTime();
                MazeAlgorithms.generateKruskal(grid);
                kruskal.add(START_SIZE + i, System.nanoTime() - startTime);
            }
            if (algorithms[3].isSelected()) {
                startTime = System.nanoTime();
                MazeAlgorithms.generatePrim(grid, startX, startY);
                prim.add(START_SIZE + i, System.nanoTime() - startTime);
            }
        }
        dataset.addSeries(prim);
        dataset.addSeries(kruskal);
        dataset.addSeries(eller);
        dataset.addSeries(depth);

        initializeGraph(dataset);
    }

    private void initializeGraph(XYSeriesCollection sc) {
        NumberAxis xAxis = new NumberAxis("Size");
        xAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        LogAxis yAxis = new LogAxis("Time (nanoseconds in base 2");
        yAxis.setBase(2);
        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        XYPlot plot = new XYPlot(sc,
                xAxis, yAxis, new XYLineAndShapeRenderer(true, false));
        chart = new JFreeChart(
                "Chart", JFreeChart.DEFAULT_TITLE_FONT, plot, false);
        customizeChart(chart);
        this.add(new ChartPanel(chart), BorderLayout.NORTH);
        this.pack();
        this.revalidate();
        this.setVisible(true);
    }

    private void customizeChart(JFreeChart chart) {   // here we make some customization
        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        // sets paint color for each series
        renderer.setSeriesPaint(0, PRIM_COLOR);
        renderer.setSeriesPaint(1, KRUSKAL_COLOR);
        renderer.setSeriesPaint(2, ELLER_COLOR);
        renderer.setSeriesPaint(3, DEPTH_COLOR);

        // sets thickness for series (using strokes)
        renderer.setSeriesStroke(0, new BasicStroke(4.0f));
        renderer.setSeriesStroke(1, new BasicStroke(3.0f));
        renderer.setSeriesStroke(2, new BasicStroke(2.0f));

        // sets paint color for plot outlines
        plot.setOutlinePaint(Color.BLUE);
        plot.setOutlineStroke(new BasicStroke(2.0f));

        // sets renderer for lines
        plot.setRenderer(renderer);

        // sets plot background
        plot.setBackgroundPaint(Color.DARK_GRAY);

        // sets paint color for the grid lines
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

    }
}
