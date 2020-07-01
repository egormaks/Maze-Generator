import javax.swing.*;
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
        mazePanels[3] = new MazePanel("Recursive Backtracking");
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

    class MazePanel extends JPanel implements PropertyChangeListener {

        public MazePanel(String name) {
            this.setName(name);
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
            int grid[][] = new int[height][width];
            if (this.getName().equals("Prim")) {
                grid = MazeAlgorithms.generatePrim(grid);
            } else if (this.getName().equals("Kruskal")) {
                grid = MazeAlgorithms.generateKruskal(grid);
            } else if (this.getName().equals("Eller")) {
                grid = MazeAlgorithms.generateEller(grid);
            } else if (this.getName().equals("Recursive Backtracking")) {
                grid = MazeAlgorithms.generateBacktrack(grid);
            }
            // display maze grid
        }
    }

}

