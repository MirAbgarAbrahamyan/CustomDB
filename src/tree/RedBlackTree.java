package tree;

import java.util.LinkedList;
import java.util.List;

public class RedBlackTree {
    private static class Node {
        List<String> info = new LinkedList<>();
        String key;
        Node parent;
        Node left;
        Node right;
        boolean color; // false/0 = black, true/1 = red
    }

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private Node root;
    private Node TNULL;

    public RedBlackTree() {
        TNULL = new Node();
        root = TNULL;
        root.parent = TNULL;
    }

    private void inOrder(Node root) {
        if (root != TNULL) {
            inOrder(root.left);
            System.out.println(root.info);
            inOrder(root.right);
        }
    }

    public void inOrderTraverse() {
        inOrder(this.root);
    }

    private void leftRotation(Node x) {
        Node y = x.right; // set y
        x.right = y.left; // turn y's left subtree into x's right subtree
        if (y.left != TNULL) {
            y.left.parent = x;
        }
        y.parent = x.parent; // link x's parent to y
        if (x.parent == TNULL) {
            this.root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x; // put x on y's left
        x.parent = y;
    }

    private void rightRotation(Node x) {
        Node y = x.left;
        x.left = y.right;
        if (y.right != TNULL) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == TNULL) {
            this.root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.right = x;
        x.parent = y;
    }

    private void fixUpForInsert(Node k) {
        Node uncle;
        while (k.parent.color == RED) {
            if (k.parent == k.parent.parent.right) {
                uncle = k.parent.parent.left;
                if (uncle.color == RED) {   // case 1
                    uncle.color = BLACK;
                    k.parent.color = BLACK;
                    k.parent.parent.color = RED;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.left) {   // case 2 => case 3
                        k = k.parent;
                        rightRotation(k);
                    }
                    k.parent.color = BLACK; // case 3
                    k.parent.parent.color = RED;
                    leftRotation(k.parent.parent);
                }
            } else {
                uncle = k.parent.parent.right; // uncle

                if (uncle.color == RED) {   // case 1
                    uncle.color = BLACK;
                    k.parent.color = BLACK;
                    k.parent.parent.color = RED;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.right) {  // case 2 => case 3
                        k = k.parent;
                        leftRotation(k);
                    }
                    k.parent.color = BLACK; // case 3
                    k.parent.parent.color = RED;
                    rightRotation(k.parent.parent);
                }
            }
        }
        root.color = BLACK;
    }

    public void insert(String key, String info) {
        Node newNode = new Node();
        newNode.parent = TNULL;
        newNode.left = TNULL;
        newNode.right = TNULL;
        newNode.color = RED;
        newNode.key = key;
        newNode.info.add(info);;
        Node fakePtr = this.root;
        Node realPtr = fakePtr;
        while (fakePtr != TNULL) {
            realPtr = fakePtr;
            if (key.compareTo(fakePtr.key) < 0) {
                fakePtr = fakePtr.left;
            } else if (key.compareTo(fakePtr.key) > 0) {
                fakePtr = fakePtr.right;
            } else {
                fakePtr.info.add(info);
                return;
            }
        }
        newNode.parent = realPtr;
        if (realPtr == TNULL) {
            this.root = newNode;
        } else if (key.compareTo(realPtr.key) < 0) {
            realPtr.left = newNode;
        } else {
            realPtr.right = newNode;
        }
        if (newNode.parent == TNULL) {
            newNode.color = BLACK;
            return;
        }
        if (newNode.parent.parent == TNULL) {
            return;
        }
        fixUpForInsert(newNode);
    }

    private void searchh(Node root, String key, boolean isAsteriskOn, int idxOfColumn) {
        while (root != TNULL) {
            if (key.equals(root.key)) {
                if (isAsteriskOn) {
                    root.info.forEach(System.out::println);
                } else {
                    root.info.forEach(info -> System.out.println(info.split(",")[idxOfColumn]));
                }
                break;
            } else if (key.compareTo(root.key) < 0) {
                root = root.left;
            } else {
                root = root.right;
            }
        }
    }

    public void search(String key, boolean isAsteriskOn, int idxOfColumn) {
        searchh(this.root, key, isAsteriskOn, idxOfColumn);
    }
}
