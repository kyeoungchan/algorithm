package example;

import java.util.TreeMap;

public class TreeMapTest {
    static class Sample implements Comparable<Sample> {
        int id, factor;

        public Sample(int id, int factor) {
            this.id = id;
            this.factor = factor;
        }

        @Override
        public int compareTo(Sample o) {
            return Integer.compare(factor, o.factor);
        }
    }
    
    public static void main(String[] args) {
        TreeMap<Sample, Integer> tm = new TreeMap<>();
        tm.put(new Sample(1, 1), 1);
        tm.put(new Sample(2, 1), 1);
        tm.put(new Sample(3, 1), 1);
        System.out.println("tm.size() = " + tm.size());
        System.out.println("tm.pollFirstEntry().getKey().id = " + tm.pollFirstEntry().getKey().id);
    }
}
