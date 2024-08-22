package example;

import java.util.*;

public class SortTest {

    static class Node implements Comparable<Node> {
        int order;

        public Node(int order) {
            this.order = order;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(order, o.order);
        }
    }

    public static void main(String[] args) {

        int arraySize = 10_000_000;
        Node[] array = new Node[arraySize];
        List<Node> list = new ArrayList<>(arraySize);
        for (int i = arraySize - 1; i >= 0; i--) {
            array[i] = new Node(i);
        }
        long startTime = System.currentTimeMillis();
        Arrays.sort(array); // 결과가 거의 15
//        Collections.sort(list); // 결과가 거의 1
        long endTime = System.currentTimeMillis();
        System.out.println("totalTime = " + (endTime - startTime));
    }
}
