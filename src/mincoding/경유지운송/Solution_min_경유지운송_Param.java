package mincoding.경유지운송;

import java.util.*;

public class Solution_min_경유지운송_Param {

    static class City {
        int number;
        int weight;

        public City(int number, int weight) {
            this.number = number;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "City{" +
                    "number=" + number +
                    ", weight=" + weight +
                    '}';
        }
    }

    List<City>[] graph = new List[2_000];
    int min, max, N;

    public void init(int N, int K, int[] sCity, int[] eCity, int[] mLimit) {
        this.N = N;
        for (int i = 0; i < N; i++) {
            if (graph[i] == null) graph[i] = new ArrayList<>();
            else graph[i].clear();
        }

        // mLimit: 도로를 이용할 수 있는 최대 중량 ( 1 ≤ mLimit ≤ 30,000 )
        min = 30_001;
        max = 0;
        for (int i = 0; i < K; i++) {
            graph[sCity[i]].add(new City(eCity[i], mLimit[i]));
            graph[eCity[i]].add(new City(sCity[i], mLimit[i]));
            min = Math.min(min, mLimit[i]);
            max = Math.max(max, mLimit[i]);
        }

    }

    public void add(int sCity, int eCity, int mLimit) {
        graph[sCity].add(new City(eCity, mLimit));
        graph[eCity].add(new City(sCity, mLimit));
        min = Math.min(min, mLimit);
        max = Math.max(max, mLimit);
    }

    public int calculate(int sCity, int eCity, int M, int[] mStopover) {
        ArrayDeque<Integer> q = new ArrayDeque<>();
        boolean[] visited = new boolean[N];
        visited[sCity] = true;
        q.offer(sCity);
        while (!q.isEmpty()) {
            int cur = q.poll();
            for (City city : graph[cur]) {
                int next = city.number;
                if (visited[next]) continue;
                visited[next] = true;
                q.offer(next);
            }
        }
        if (!visited[eCity]) {
            // 목적지에 도달할 수 없는 경우
            return -1;
        }
        for (int i = 0; i < M; i++) {
            if (!visited[mStopover[i]]) {
                // 경유지에 도달할 수 없는 경우
                return -1;
            }
        }

        int left = min;
        int right = max;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (!underLimit(mid, sCity, eCity, M, mStopover)) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return right;
    }

    private boolean underLimit(int mid, int start, int end, int M, int[] stopovers) {

        boolean[] visited = new boolean[N];
        ArrayDeque<Integer> q = new ArrayDeque<>();
        q.offer(start);
        visited[start] = true;

        while (!q.isEmpty()) {
            int cur = q.poll();
            for (City city : graph[cur]) {
                int next = city.number;
                if (visited[next] || city.weight < mid) continue;
                visited[next] = true;
                q.offer(next);
            }
        }
        if (!visited[end]) return false;
        for (int i = 0; i < M; i++) {
            if (!visited[stopovers[i]]) return false;
        }
        return true;
    }
}