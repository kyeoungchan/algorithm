import java.io.*;
import java.util.*;

public class Main {

    static class Jewel implements Comparable<Jewel> {
        int weight, value;

        public Jewel (int weight, int value) {
            this.weight = weight;
            this.value = value;
        }

        @Override
        public int compareTo(Jewel o) {
            return weight == o.weight ? Integer.compare(o.value, value) :  Integer.compare(weight, o.weight);
        }
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        PriorityQueue<Jewel> pq = new PriorityQueue<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int M = Integer.parseInt(st.nextToken());
            int V = Integer.parseInt(st.nextToken());
            pq.offer(new Jewel(M, V));
        }

        List<Integer> bags = new ArrayList<>(K);
        for (int i = 0; i < K; i++) {
            int C =  Integer.parseInt(br.readLine());
            bags.add(C);
        }

        Collections.sort(bags);

        PriorityQueue<Integer> pq2 = new PriorityQueue<>(Comparator.reverseOrder());

        long answer = 0;
        for (int i = 0; i < K; i++) {
            int C = bags.get(i);
            while (!pq.isEmpty()) {
                Jewel jewel = pq.poll();
                if (jewel.weight > C) {
                    pq.offer(jewel);
                    break;
                }
                pq2.offer(jewel.value);
            }
            if (!pq2.isEmpty()) {
                answer += pq2.poll();
            }
        }
        System.out.println(answer);
        br.close();
    }
}