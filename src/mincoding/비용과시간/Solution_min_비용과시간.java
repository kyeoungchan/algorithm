package mincoding.비용과시간;

import java.io.*;
import java.util.*;

public class Solution_min_비용과시간 {

    static class Road implements Comparable<Road> {
        int toCity, cost, time;

        public Road(int toCity, int cost, int time) {
            this.toCity = toCity;
            this.cost = cost;
            this.time = time;
        }

        @Override
        public int compareTo(Road o) {
            return Integer.compare(time, o.time);
        }
    }

    private int N;
    private List<Road>[] roads;
    private PriorityQueue<Road> pq = new PriorityQueue<>();

    public void init(int N, int K, int[]sCity, int[]eCity, int[]mCost, int[]mTime) {
        this.N = N;
        roads = new List[N];
        for (int i = 0; i < N; i++) roads[i] = new ArrayList<>();

        for (int i = 0; i < K; i++) {
            int start = sCity[i];
            int end = eCity[i];
            int cost = mCost[i];
            int time = mTime[i];
            roads[start].add(new Road(end, cost, time));
        }
    }

    public void add(int sCity, int eCity, int mCost, int mTime) {
        roads[sCity].add(new Road(eCity, mCost, mTime));
    }

    public int cost(int M, int sCity, int eCity) {
        pq.clear();
        int[][] dist = new int[N][M + 1];
        for (int i = 0; i < N; i++) Arrays.fill(dist[i], Integer.MAX_VALUE);
        PriorityQueue<Road> pq = new PriorityQueue<>();
        dist[sCity][0] = 0;
        pq.offer(new Road(sCity, 0, 0));
        int result = -1;
        while (!pq.isEmpty()) {
            Road cur = pq.poll();
            if (dist[cur.toCity][cur.cost] < cur.time) continue;
            if (cur.toCity == eCity) {
                result = cur.time;
                break;
            }

            for (Road next: roads[cur.toCity]) {
                if (next.cost + cur.cost > M) continue;
                int nextCost = next.cost + cur.cost;
                if (dist[next.toCity][nextCost] > next.time + cur.time) {
                    dist[next.toCity][nextCost] = next.time + cur.time;
                    pq.offer(new Road(next.toCity, nextCost, dist[next.toCity][nextCost]));
                }
            }
        }
        return result;
    }
}
