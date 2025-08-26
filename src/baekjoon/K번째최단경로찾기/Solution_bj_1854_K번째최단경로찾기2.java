package baekjoon.K번째최단경로찾기;

import java.io.*;
import java.util.*;

public class Solution_bj_1854_K번째최단경로찾기2 {

    static class City implements Comparable<City> {
        int number, cost;

        public City(int number, int cost) {
            this.number = number;
            this.cost = cost;
        }

        @Override
        public int compareTo(City o) {
            return Integer.compare(cost, o.cost);
        }
    }

    public static void main(String[] args) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());
            List<City>[] cities = new List[n + 1];
            PriorityQueue<Integer>[] dist = new PriorityQueue[n + 1];
            PriorityQueue<City> pq = new PriorityQueue<>();

            for (int i = 1; i <= n; i++) {
                cities[i] = new ArrayList<>();
                dist[i] = new PriorityQueue<>(Collections.reverseOrder());
            }

            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                cities[a].add(new City(b, c));
            }

            pq.offer(new City(1, 0));
            dist[1].add(0);

            while (!pq.isEmpty()) {
                City cur = pq.poll();

                int curNumber = cur.number;
                int curCost = cur.cost;

                for (City next : cities[curNumber]) {
                    int nextCost = next.cost + curCost;
                    if (dist[next.number].size() < k) {
                        dist[next.number].add(nextCost);
                        pq.offer(new City(next.number, nextCost));
                    } else if (dist[next.number].peek() > nextCost) {
                        dist[next.number].poll();
                        dist[next.number].add(nextCost);
                        pq.offer(new City(next.number, nextCost));
                    }
                }
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 1; i <= n; i++) {
                if (dist[i].size() < k) sb.append(-1).append("\n");
                else sb.append(dist[i].poll()).append("\n");
            }

            System.out.println(sb);
        }
    }
}
