package chapter3.section2;

public class Test {


    public static void main(String[] args) {
        BinarySearchTree<Integer, String> binarySearchTree = new BinarySearchTree<>();
        binarySearchTree.print();

        binarySearchTree.put(1, "a");
        binarySearchTree.put(6, "d");
        binarySearchTree.put(7, "e");
        binarySearchTree.put(3, "b");
        binarySearchTree.put(5, "c");
        binarySearchTree.put(7, "e");
        binarySearchTree.print();



        System.out.println(binarySearchTree.select(4));
    }
}
