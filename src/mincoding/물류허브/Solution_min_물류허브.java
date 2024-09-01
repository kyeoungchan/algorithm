package mincoding.물류허브;

import java.util.*;

public class Solution_min_물류허브 {

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

    private int N, idx;
    private List<City>[] cities, backCities;
    private Map<Integer, Integer> idxMap = new HashMap<>();
    private int[] numbers = new int[1400];
    private PriorityQueue<City> pq = new PriorityQueue<>();

    public int init(int N, int[] sCity, int[] eCity, int[]mCost) {
        cities = new List[600];
        backCities = new List[600];
        idxMap.clear();
        idx = 0;
        for (int i = 0; i < N; i++) {
            int start = sCity[i];
            if (!idxMap.containsKey(start)) {
                idxMap.put(start, idx);
                cities[idx] = new ArrayList<>();
                backCities[idx] = new ArrayList<>();
                numbers[idx] = start;
                idx++;
            }
            int end = eCity[i];
            if (!idxMap.containsKey(end)) {
                idxMap.put(end, idx);
                cities[idx] = new ArrayList<>();
                backCities[idx] = new ArrayList<>();
                numbers[idx] = end;
                idx++;
            }
            int cost = mCost[i];
            cities[idxMap.get(start)].add(new City(end, cost));
            backCities[idxMap.get(end)].add(new City(start, cost));
        }
        this.N = idx;
        return idx;
    }

    public void add(int sCity, int eCity, int mCost) {
        cities[idxMap.get(sCity)].add(new City(eCity, mCost));
        backCities[idxMap.get(eCity)].add(new City(sCity, mCost));
    }

    public int cost(int mHub) {
        int result = 0;
        result += getMultiCost(mHub, false);
        result += getMultiCost(mHub, true);

        return result;
    }

    private int getMultiCost(int from, boolean reverse) {
        List<City>[] thisCities;
        if (reverse) thisCities = backCities;
        else thisCities = cities;
        pq.clear();
        int[] dist = new int[N];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[idxMap.get(from)] = 0;
        pq.offer(new City(from, 0));
        int result = 0;
        while (!pq.isEmpty()) {
            City cur = pq.poll();
            int curIdx = idxMap.get(cur.number);

            if (dist[curIdx] < cur.cost) continue;
            result += cur.cost;

            for (City next : thisCities[curIdx]) {
                int nextIdx = idxMap.get(next.number);
                if (dist[nextIdx] > cur.cost + next.cost) {
                    dist[nextIdx] = cur.cost + next.cost;
                    pq.offer(new City(next.number, dist[nextIdx]));
                }
            }
        }
        return result;
    }


}
