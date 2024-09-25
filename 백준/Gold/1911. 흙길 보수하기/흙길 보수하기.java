import java.io.*;
import java.util.*;

public class Main {

    static class Puddle implements Comparable<Puddle> {
        int start, end;

        public Puddle(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(Puddle o) {
            return Integer.compare(start, o.start);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken());
        int L = Integer.parseInt(st.nextToken());
        PriorityQueue<Puddle> pq = new PriorityQueue<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            pq.offer(new Puddle(start, end));
        }

        int answer = 0;
        boolean shouldSuccess = false;
        int current = 0;
        while (!pq.isEmpty()) {
            Puddle p = pq.poll();
            if (current < p.start) current = p.start;
            while (current < p.end) {
                current += L;
                answer++;
            }
        }
        System.out.println(answer);
    }
}