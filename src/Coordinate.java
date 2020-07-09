public class Coordinate implements Comparable {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

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