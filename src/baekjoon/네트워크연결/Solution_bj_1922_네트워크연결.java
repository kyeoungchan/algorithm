package baekjoon.네트워크연결;

import java.io.*;
import java.util.*;

public class Solution_bj_1922_네트워크연결 {
    static class Host implements Comparable<Host> {
        int number, cost;

        public Host(int number, int cost) {
            this.number = number;
            this.cost = cost;
        }

        @Override
        public int compareTo(Host o) {
            return Integer.compare(cost, o.cost);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int N = Integer.parseInt(br.readLine());
        int M = Integer.parseInt(br.readLine());
        List<Host>[] network = new List[N + 1];
        for (int i = 1; i < N + 1; i++) {
            network[i] = new ArrayList<>();
        }
        int min = 0;
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            network[a].add(new Host(b, c));
            if (a != b) network[b].add(new Host(a, c));
        }

        PriorityQueue<Host> pq = new PriorityQueue<>();
        boolean[] v = new boolean[N + 1];
        pq.offer(new Host(1, 0));
        while (!pq.isEmpty()) {
            Host cur = pq.poll();

            if (v[cur.number]) continue;
            v[cur.number] = true;
            min += cur.cost;

            for (Host next: network[cur.number]) {
                if (v[next.number]) continue;
                pq.offer(next);
            }
        }
        System.out.println(min);
        br.close();
    }
}
