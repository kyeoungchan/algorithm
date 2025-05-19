package mincoding.경유지운송;

import java.util.*;

public class Solution_min_경유지운송_Prim {

    static class City implements Comparable<City> {
        int number;
        int weight;
        int visited;

        City(int number, int weight) {
            this.number = number;
            this.weight = weight;
        }

        City(int number, int weight, int visited) {
            this.number = number;
            this.weight = weight;
            this.visited = visited;
        }

        void update(int number, int weight) {
            this.number = number;
            this.weight = weight;
        }

        void update (int number, int weight, int visited) {
            this.number = number;
            this.weight = weight;
            this.visited = visited;
        }

        @Override
        public int compareTo(City o) {
            return Integer.compare(o.number, number);
        }

        @Override
        public String toString() {
            return "City [number=" + number + ", weight=" + weight + ", visited=" + visited + "]";
        }
    }

    City[] cities = new City[8_100_000];
    List<City>[] graph = new List[20_000];
    PriorityQueue<City> pq = new PriorityQueue<>();
    int N, instanceIdx;
    Map<Integer, Integer> stopOverIdxMap = new HashMap<>();

    City getInstance(int number, int weight) {
        if (cities[instanceIdx] == null) cities[instanceIdx] = new City(number, weight);
        else cities[instanceIdx].update(number, weight, 0);
        return cities[instanceIdx++];
    }

    City getInstance(int number, int weight, int visited) {
        if (cities[instanceIdx] == null) cities[instanceIdx] = new City(number, weight, visited);
        else cities[instanceIdx].update(number, weight, visited);
        return cities[instanceIdx++];
    }
/*
    N: 도시의 개수 ( 5 ≤ N ≤ 1,000 )
    K: 도로의 개수 ( 2 ≤ K ≤ 2,000 )
    4_000
*/
    public void init(int N, int K, int[] sCity, int[] eCity, int[] mLimit) {
        instanceIdx = 0;
        this.N = N;
        for (int i = 0; i < N; i++) {
            if (graph[i] == null) graph[i] = new ArrayList<>();
            else graph[i].clear();
        }
        for (int i = 0; i < K; i++) {
            graph[sCity[i]].add(getInstance(eCity[i], mLimit[i]));
            graph[eCity[i]].add(getInstance(sCity[i], mLimit[i]));
        }
    }

    // 1,400 이하
    // 2_800
    public void add(int sCity, int eCity, int mLimit) {
        graph[sCity].add(getInstance(eCity, mLimit));
        graph[eCity].add(getInstance(sCity, mLimit));
    }

    // M: 경유지 개수 ( 1 ≤ M ≤ 3)
    // 100
    public int calculate(int sCity, int eCity, int M, int[] mStopover) {
        stopOverIdxMap.clear();

        for (int i = 0; i < M; i++) {
            stopOverIdxMap.put(mStopover[i], i);
        }

        pq.clear();
        int[][] dist = new int[N][1 << M];

        for (City next : graph[sCity]) {
            int visited = 0;
            if (stopOverIdxMap.containsKey(next.number)) {
                visited = 1 << stopOverIdxMap.get(next.number);
            }
            dist[next.number][visited] = next.weight;
            pq.offer(getInstance(next.number, next.weight, visited));
        }

        while (!pq.isEmpty()) {
            City cur = pq.poll();
            if (dist[cur.number][cur.visited] > cur.weight) continue;
            dist[cur.number][cur.visited] = cur.weight;

            for (City next : graph[cur.number]) {
                int nextWeight = Math.min(next.weight, cur.weight);
                int nextVisited = cur.visited;
                if (stopOverIdxMap.containsKey(next.number)) {
                    nextVisited = nextVisited | (1 << stopOverIdxMap.get(next.number));
                }

                if (dist[next.number][nextVisited] < nextWeight) {
                    dist[next.number][nextVisited] = nextWeight;
                    pq.offer(getInstance(next.number, nextWeight, nextVisited));
                }
            }

        }

        int result = dist[eCity][(1 << M) - 1];
        return result == 0 ? -1 : result;
    }

}
