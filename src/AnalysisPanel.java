import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;


public class AnalysisPanel extends JFrame {
    private static final int START_SIZE = 5;
    private static final int MAX_RUNS = 20;
    private static final int DEFAULT_WIDTH = 640;
    private static final int DEFAULT_HEIGHT = 480;
    private JFreeChart chart;

    public AnalysisPanel() {
        super("Analysis");
        this.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        this.setLayout();
        this.add()
    }

    public void performAnalysis() {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries prim = new XYSeries("Prim");
        XYSeries kruskal = new XYSeries("Kruskal");
        XYSeries eller = new XYSeries("Eller");
        XYSeries depth = new XYSeries("Depth First");
        for (int i = 1; i <= MAX_RUNS; i++) {
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

            long startTime = System.nanoTime();
            MazeAlgorithms.generateDepthFirstRecursive(grid, startX, startY);
            depth.add(START_SIZE + i, System.nanoTime() - startTime);
            startTime = System.nanoTime();
            MazeAlgorithms.generateEller(grid);
            eller.add(START_SIZE + i, System.nanoTime() - startTime);
            startTime = System.nanoTime();
            MazeAlgorithms.generateKruskal(grid);
            kruskal.add(START_SIZE + i, System.nanoTime() - startTime);
            startTime = System.nanoTime();
            MazeAlgorithms.generatePrim(grid, startX, startY);
            prim.add(START_SIZE + i, System.nanoTime() - startTime);
        }
        dataset.addSeries(prim);
        dataset.addSeries(kruskal);
        dataset.addSeries(eller);
        dataset.addSeries(depth);

        initializeGraph(dataset);
    }

    private void initializeGraph(XYSeriesCollection sc) {
        chart = ChartFactory.createXYLineChart("Runttime Analyisis", "Size",
                "Time (nanoseconds", sc);
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
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.GREEN);
        renderer.setSeriesPaint(2, Color.YELLOW);

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
