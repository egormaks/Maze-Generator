import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class MazeAlgorithms {
    private static final int NUM_DIR = 4;

    public static void generateDepthFirstRecursive(int grid[][], int col, int row) {
       List<Integer> directions = generateDirections();
       for (int i = 0; i < NUM_DIR; i++) {
           switch (directions.get(i)) {
               case 1:
                   if (row - 2 <= 0) {
                        continue;
                   }
                   if (grid[row - 2][col] == 1) {
                        grid[row - 1][col] = 0;
                        grid[row - 2][col] = 0;
                        generateDepthFirstRecursive(grid, col, row - 2);
                   }
               case 2:
                   if (col + 2 >= grid[0].length - 1) {
                        continue;
                   }
                   if (grid[row][col + 2] == 1) {
                        grid[row][col + 1] = 0;
                        grid[row][col + 2] = 0;
                        generateDepthFirstRecursive(grid, col + 2, row);
                   }
               case 3:
                   if (row + 2 >= grid.length - 1) {
                       continue;
                   }
                   if (grid[row + 2][col] == 1) {
                       grid[row + 1][col] = 0;
                       grid[row + 2][col] = 0;
                       generateDepthFirstRecursive(grid, col, row + 2);
                   }
               case 4:
                   if (col - 2 <= 0) {
                       continue;
                   }
                   if (grid[row][col - 2] == 1) {
                       grid[row][col - 1] = 0;
                       grid[row][col - 2] = 0;
                       generateDepthFirstRecursive(grid, col - 2, row);
                   }
           }
       }
    }

    public static void generateDepthFirstStack(int grid[][], int initX, int initY) {
        // TODO: implement stack variation of depth-first search
    }

    public static int[][] generateKruskal(int grid[][]) {
        return grid;
    }

    public static int[][] generateEller(int grid[][]) {
        return grid;
    }

    public static int[][] generatePrim(int grid[][]) {
        return grid;
    }

    private static List<Integer> generateDirections() {
        int i;
        List<Integer> list = new ArrayList<>();
        for (i = 1; i <= NUM_DIR; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        return list;
    }

    private static void printGrid(int[][] grid) {
        int i,j;
        for (i = 0; i < grid.length; i++) {
            for (j = 0; j < grid[0].length; j++) {
                System.out.print(grid[j][i] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

     public static class Coordinate {
        private int x;
        private int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() { return x;}

        public int getY() { return y;}
    }
}

