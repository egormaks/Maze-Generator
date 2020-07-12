import java.util.*;

public class CoordinateTree {
    private Node root;

    public CoordinateTree() {
        this(null);
    }

    public CoordinateTree(Coordinate c) {
        this.root = new Node(null, null, null, c);
    }

    public Node getRoot() {
        return root;
    }


    public void setRoot(Node n) {
        this.root = n;
    }

    public int height(Node n){
        if (n == null) return 0;
        if(n.getLeft() == null && n.getRight() == null) {
            return 1;
        } else if(n.getRight() == null) {
            return 1 + height(n.getLeft());
        } else if(n.getLeft() == null) {
            return 1 + height(n.getRight());
        } else {
            return 1 + height(n.getRight()) + height(n.getLeft());
        }
    }

    public void insert(Node n) {
        insert(n, this.root);
    }

    public void insert(Node n, Node root){
        if(root.getLeft() == null) {
            root.setLeft(n);
        } else if(root.getRight() == null) {
            root.setRight(n);
        } else {
            if(height(n.getLeft()) <= height(n.getRight())) {
                insert(n, root.getLeft());
            } else {
                insert(n, root.getRight());
            }
        }
    }
}

 class Node {
     private Node left;
     private Node parent;
     private Node right;
     private Coordinate data;

     public Node() {
         this(null, null, null, null);
     }

     public Node(Coordinate c) {
         this(null, null, null, c);
     }

     public Node(Node left, Node parent, Node right, Coordinate data) {
         this.left = left;
         this.parent = parent;
         this.right = right;
         this.data = data;
     }

     public void setLeft(Node left) {
         this.left = left;
         left.setParent(this);
     }

     public void setRight(Node right) {
         this.right = right;
         right.setParent(this);
     }

     public void setParent(Node parent) {
         this.parent = parent;
     }

     public Node getLeft() {
         return left;
     }

     public Node getRight() {
         return right;
     }

     public Coordinate getData() {
         return data;
     }

     @Override
     public String toString() {
         return data.toString();
     }
 }
