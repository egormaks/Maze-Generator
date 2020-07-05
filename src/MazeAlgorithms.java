import java.util.*;

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

    // list of walls and list of sets of cells
    // choose random wall. if the cells it is seperating are distinct sets, union the sets, remove wall
    // repeat until size of set list is 1
    public static int[][] generateKruskal(int grid[][]) {
        return grid;
    }


    public static int[][] generateEller(int grid[][]) {
        return grid;
    }

    public static void generatePrim(int grid[][], int startX, int startY) {
        Coordinate start = new Coordinate(startX, startY);
        Set<Coordinate> frontier = new TreeSet<>();
        Set<Coordinate> maze = new TreeSet<>();
        int mazeSize = ((grid.length - 1) / 2) * ((grid[0].length - 1) / 2);
        frontier.addAll(getNeighbors(start, grid));
        maze.add(start);

        Coordinate chosen;
        Coordinate adjacent;
        Coordinate intermediate;
        while (!frontier.isEmpty()) {
            chosen = getRandomElement(frontier);
            adjacent = getAdjactent(maze, chosen);
            intermediate = new Coordinate((chosen.getX() + adjacent.getX()) / 2,
                    (chosen.getY() + adjacent.getY()) / 2);

            if (maze.contains(chosen) || maze.contains(intermediate)) {
                if (frontier.contains(chosen)) frontier.remove(chosen);
                if (frontier.contains(intermediate)) frontier.remove(intermediate);
                continue;
            }

            maze.add(chosen);
            maze.add(intermediate);
            frontier.addAll(getNeighbors(chosen, grid));
            frontier.remove(adjacent);
            frontier.remove(chosen);
            frontier.remove(intermediate);

            grid[chosen.getY()][chosen.getX()] = 0;
            grid[intermediate.getY()][intermediate.getX()] = 0;
        }
    }

    private static Coordinate getRandomElement (Set s) {
        Random rand = new Random();
        int index = rand.nextInt(s.size());
        Iterator<Coordinate> iter = s.iterator();
        for (int i = 0; i < index; i++) {
            iter.next();
        }
        return iter.next();
    }

    private static Coordinate getAdjactent(Set<Coordinate> s, Coordinate c) {
        for (Coordinate coor : s) {
            boolean x = Math.abs(coor.getX() - c.getX()) == 2 && coor.getY() == c.getY();
            boolean y = Math.abs(coor.getY() - c.getY()) == 2 && coor.getX() == c.getX();
            if (x && !y || !x && y) {
                return coor;
            }
        }
        return null;
    }

    private static List<Coordinate> getNeighbors(Coordinate c, int[][] grid) {
        List<Coordinate> neighbors = new ArrayList<>();
        int x = c.getX();
        int y = c.getY();

        if (x - 2 <= 0) {
            neighbors.add(new Coordinate(x + 2, y));

        } else if (x + 2 >= grid[0].length) {
            neighbors.add(new Coordinate(x - 2, y));
        } else {
            neighbors.add(new Coordinate(x + 2, y));
            neighbors.add(new Coordinate(x - 2, y));
        }

        if (y - 2 <= 0) {
            neighbors.add(new Coordinate(x, y + 2));
        } else if (y + 2 >= grid.length - 1) {
            neighbors.add(new Coordinate(x, y - 2));
        } else {
            neighbors.add(new Coordinate(x, y + 2));
            neighbors.add(new Coordinate(x, y - 2));
        }



        return neighbors;
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

    private static List<Integer> generateDirections() {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        return list;
    }


     public static class Coordinate implements Comparable  {
        private int x;
        private int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() { return x;}

        public int getY() { return y;}

        @Override
        public boolean equals(Object other) {
            if (other == null) {
                return false;
            } else if (this.getClass() != other.getClass()) {
                return false;
            }
            Coordinate otherCoor = (Coordinate) other;
            return this.x == otherCoor.x && this.y == otherCoor.y;
        }

        @Override
         public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            sb.append(this.x);
            sb.append(",");
            sb.append(this.y);
            sb.append(")");
            return sb.toString();
        }

        @Override
        public int compareTo(Object o) {
            if (o.getClass() != this.getClass()) {
                return -1;
            }
            Coordinate c = (Coordinate) o;
            int retVal = Integer.compare(getX(), c.getX());
            if (retVal != 0) {
                return retVal;
            }
            return Integer.compare(getY(), c.getY());
        }

    }
}

