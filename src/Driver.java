import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class Driver extends JPanel  {
    private static final int CELL_X = 10;
    private static final int CELL_Y = 10;
    private static final int NO_ALGOS = 4;
    private static final String GENERATE_MAZE = "Received dim, generate maze";

    private int width;
    private int height;
    private MazePanel[] mazePanels;

    public Driver() {
        this.setName("Simulator");
        mazePanels = new MazePanel[NO_ALGOS];
        mazePanels[0] = new MazePanel("Prim");
        mazePanels[1] = new MazePanel("Kruskal");
        mazePanels[2] = new MazePanel("Eller");
        mazePanels[3] = new MazePanel("Recursive Depth First");
    }

    public static void main(String[] args) {
        InitFrame init = new InitFrame();
        Driver driver = new Driver();
        for (int i = 0; i < NO_ALGOS; i++) {
            init.addPropertyChangeListener(driver.mazePanels[i]);
        }
        boolean running = true;
        driver.setVisible(true);
        while (running) {

        }
    }

    static class MazePanel extends JPanel implements PropertyChangeListener {
        private static int startX;
        private static int startY;
        public MazePanel(String name) {
            this.setName(name);
            this.setSize(100, 100);
        }

        @Override
        public void propertyChange(PropertyChangeEvent ev) {
            if (ev.getPropertyName().equals(InitFrame.GENERATE)) {
                System.out.println(this.getName() + " received data.");
                List<Integer> dim = (List<Integer>) ev.getNewValue();
                createMaze(dim.get(0), dim.get(1));
            }
        }

        private void createMaze(int width, int height) {
            int grid[][] = new int[2 * height + 1][2 * width + 1];
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    grid[i][j] = 1;
                }
            }
            startX = (int)(Math.random() * grid[0].length);
            if (startX % 2 == 0) {
                if (startX == 0) {
                    startX++;
                } else if (startX == grid[0].length - 1) {
                    startX--;
                } else startX--;
            }
            startY = (int)(Math.random() * grid.length);
            if (startY % 2 == 0) {
                if (startY == 0) {
                    startY++;
                } else if (startX == grid.length - 1) {
                    startY--;
                } else startY--;
            }
            if (this.getName().equals("Prim")) {
                grid[startY][startX] = 0;
                MazeAlgorithms.generatePrim(grid, startX, startY);
            } else if (this.getName().equals("Kruskal")) {
                MazeAlgorithms.generateKruskal(grid);
            } else if (this.getName().equals("Eller")) {
                MazeAlgorithms.generateEller(grid);
            } else if (this.getName().equals("Recursive Depth First")) {
                grid[startY][startX] = 0;
                MazeAlgorithms.generateDepthFirstRecursive(grid, startX, startY);
            }
            int i,j;
            for (i = 0; i < grid.length; i++) {
                for (j = 0; j < grid[0].length; j++) {
                    char c;
                    if (grid[i][j] == 0) c = ' ';
                    else c = '#';
                    System.out.print(c + " ");
                }
                System.out.println();
            }
        }
    }

}

