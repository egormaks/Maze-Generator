import java.util.*;

public class MazeAlgorithms {
    private static final int NUM_DIR = 4;
    private static final Random RAND = new Random();

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

    private static <T> boolean setContains(Set<T> set, T obj) {
        boolean contains = false;
        for (T t : set) {
            if (t.equals(obj))
                contains = true;
        }
        return contains;
    }


    // TODO: Improve Algorithm, currently backtracking through entire set to update root node when adding new element
    // constant time reassignment??

    public static void generateKruskal(int grid[][]) {
        CoordinateTree setGrid[][] = new CoordinateTree[(grid.length - 1) / 2][(grid.length - 1) / 2];
        Set<Edge> edges = new HashSet<>();
        for (int i = 0; i < setGrid.length; i++) {
            for (int j = 0; j < setGrid[0].length; j++) {
                setGrid[i][j] = new CoordinateTree(new Coordinate(1 + 2 * j,1 + 2 * i));
            }
        }

        for (int i = 1; i < grid.length; i += 2) {
            for (int j = 1; j < grid[0].length; j += 2) {
                Coordinate curr = new Coordinate(j, i);
                List<Coordinate> neighbors = getNeighbors(curr, grid);
                for (Coordinate c : neighbors) {
                    Edge edge = new Edge(curr, c);
                    if (!setContains(edges, edge))
                        edges.add(edge);
                }
            }
        }

        while (!edges.isEmpty()) {
            Edge edge = getRandomElement(edges);
            Coordinate c1 = (Coordinate) edge.getItemOne();
            Coordinate c2 = (Coordinate) edge.getItemTwo();

            int x1 = (c1.getX() - 1) / 2;
            int y1 = (c1.getY() - 1) / 2;
            int x2 = (c2.getX() - 1) / 2;
            int y2 = (c2.getY() - 1) / 2;
            int intX = (c1.getX() + c2.getX()) / 2;
            int intY = (c1.getY() + c2.getY()) / 2;

            if (setGrid[y1][x1].getRoot() != setGrid[y2][x2].getRoot()) {
                setGrid[y1][x1].insert(setGrid[y2][x2].getRoot());
                updateTable(setGrid[y1][x1].getRoot(), setGrid[y1][x1].getRoot(), setGrid);
                grid[y1 * 2 + 1][x1 * 2 + 1] = 0;
                grid[y2 * 2 + 1][x2 * 2 + 1] = 0;
                grid[intY][intX] = 0;
            }
            edges.remove(edge);
        }
    }

    private static void updateTable(Node n, Node root, CoordinateTree[][] grid) {
        if (n != null) {
            Coordinate c1;
            if (n.getRight() != null) {
                c1 = n.getRight().getData();
                grid[(c1.getY() - 1) / 2][(c1.getX() - 1) / 2].setRoot(root);
            }
            if (n.getLeft() != null) {
                c1 = n.getLeft().getData();
                grid[(c1.getY() - 1) / 2][(c1.getX() - 1) / 2].setRoot(root);
            }
            updateTable(n.getRight(), root, grid);
            updateTable(n.getLeft(), root, grid);
        }
    }

    public static void generateEller(int grid[][]) {
        int setGrid[][] = new int[(grid.length - 1) / 2][(grid.length - 1) / 2];
        int setCounter = 1;

        for (int i = 0; i < setGrid.length - 1; i++) {
            // initialize all cell sets if not already in a set
            // (since sets are indicated by a number >= 1, 0 means not a set.
            for (int j = 0; j < setGrid[0].length; j++) {
                if (setGrid[i][j] == 0) {
                    setGrid[i][j] = setCounter;
                    setCounter++;
                    int x = j * 2 + 1;
                    int y = i * 2 + 1;
                    grid[y][x] = 0;
                }
            }
            // join and create right walls
            for (int j = 0; j < setGrid[0].length - 1; j++) {
                boolean toJoin = RAND.nextBoolean();
                if (setGrid[i][j] != setGrid[i][j + 1]) {
                    for (int k = j + 1; k < setGrid[0].length - 1 && toJoin; k++) {
                        if (setGrid[i][j] != setGrid[i][k]) {
                            setGrid[i][k] = setGrid[i][j];
                            toJoin = RAND.nextBoolean();
                            int x1 = j * 2 + 1;
                            int x2 = (j + 1) * 2 + 1;
                            int y = i * 2 + 1;
                            grid[y][(x1 + x2) / 2] = 0;
                        } else {
                            setGrid[i][j + 1] = setGrid[i][j];
                            j = k - 1;
                            break;
                        }
                    }
                }
            }
            // randomly determine vertical connections
            for (int j = 0; j < setGrid[0].length; j++) {
                int maxRange = j;
                boolean hasOneExit = false;
                boolean createExit;
                while (setGrid[i][maxRange] == setGrid[i][maxRange + 1]) {
                    createExit = RAND.nextBoolean();
                    if (createExit) {
                        hasOneExit = true;
                        setGrid[i + 1][j] = setGrid[i][j];
                    } else {
                        int x = j * 2 + 1;
                        int y1 = i * 2 + 1;
                        int y2 = (i + 1) * 2 + 1;
                        grid[(y1 + y2) / 2][x] = 1;
                    }
                    maxRange++;
                }
                if (!hasOneExit) {
                    if (j == maxRange) {
                        setGrid[i + 1][j] = setGrid[i][j];
                    } else {
                        int randX = (int)((Math.random() * (maxRange - j)) + j);
                        setGrid[i + 1][randX] = setGrid[i][randX];
                        int x = randX * 2 + 1;
                        int y1 = i * 2 + 1;
                        int y2 = (i + 1) * 2 + 1;
                        grid[(y1 + y2) / 2][x] = 0;
                    }
                }
            }
        }
    }

    public static void generatePrim(int grid[][], int startX, int startY) {
        Coordinate start = new Coordinate(startX, startY);
        Set<Coordinate> frontier = new TreeSet<>();
        Set<Coordinate> maze = new TreeSet<>();
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

    private static <T> T getRandomElement (Set s) {
        Random rand = new Random();
        int index = rand.nextInt(s.size());
        Iterator<T> iter = s.iterator();
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
        if (grid[0].length > 3) {
            if (x - 2 <= 0) {
                neighbors.add(new Coordinate(x + 2, y));

            } else if (x + 2 >= grid[0].length - 1) {
                neighbors.add(new Coordinate(x - 2, y));
            } else {
                neighbors.add(new Coordinate(x + 2, y));
                neighbors.add(new Coordinate(x - 2, y));
            }
        }

        if (grid.length > 3) {
            if (y - 2 <= 0) {
                neighbors.add(new Coordinate(x, y + 2));
            } else if (y + 2 >= grid.length - 1) {
                neighbors.add(new Coordinate(x, y - 2));
            } else {
                neighbors.add(new Coordinate(x, y + 2));
                neighbors.add(new Coordinate(x, y - 2));
            }
        }
        Collections.shuffle(neighbors);
        return neighbors;
    }

    private static void printGrid(int[][] grid) {
        int i,j;
        for (i = 0; i < grid.length; i++) {
            for (j = 0; j < grid[0].length; j++) {
                System.out.print(grid[i][j] + " ");
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
}

class Edge<T> {
    private T itemOne;
    private T itemTwo;

    public Edge(T itemOne, T itemTwo) {
        this.itemOne = itemOne;
        this.itemTwo = itemTwo;
    }

    public T getItemOne() {
        return itemOne;
    }

    public T getItemTwo() {
        return itemTwo;
    }

    @Override
    public boolean equals(Object other) {
        if (this.getClass() != other.getClass()) {
            return false;
        }
        Edge e = (Edge) other;
        return (this.itemOne.equals(e.itemOne) && this.itemTwo.equals(e.itemTwo)) ||
                (this.itemOne.equals(e.itemTwo) && this.itemTwo.equals(e.itemOne));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(itemOne.toString());
        sb.append(" -> ");
        sb.append(itemTwo.toString());
        return sb.toString();
    }

}
