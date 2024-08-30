package mincoding.연락처DB;

import java.util.TreeSet;

public class Test {

    static class Node implements Comparable<Node> {
        int number;

        public Node(int number) {
            this.number = number;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(number, o.number);
        }
    }

    public static void main(String[] args) {
        TreeSet<Node> ts = new TreeSet<>();
        Node target = new Node(5);
        ts.add(target);
        ts.add(new Node(2));
        ts.add(new Node(4));
        ts.add(new Node(7));
        target.number = 9;
        System.out.println("ts.size() = " + ts.size());
        ts.remove(target);
        System.out.println("ts.size( = " + ts.size());
    }
}
