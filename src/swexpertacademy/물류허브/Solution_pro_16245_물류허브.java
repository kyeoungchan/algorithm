package swexpertacademy.물류허브;

import java.util.*;

/**
 * 여러 도시를 연결하는 단방향의 도로 N개
 * 한 곳에 물류 허브를 설치햇을 때 총 운송 비용 계산
 * 총 운송비용: 각 도시에서 허브 도시까지 왕복에 필요한 최소 비용을 모두 합한 값
 * 허브 도시의 운송 비용: 0
 * 도로는 단방향이기 때문에 오가는 거리가 다를 수 있다.
 */
public class Solution_pro_16245_물류허브 {

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

    List<City>[] cities;
    List<City>[] backCities;
    Map<Integer, Integer> idxMap = new HashMap<>();
    int idx;

    /**
     * @param N     도로의 개수
     * @param sCity 도로 i의 출발도시
     * @param eCity 도로 i의 도착 도시
     * @param mCost 도로 i의 운송 비용
     * @return 도시의 총 개수
     * 도시의 번호: 1~ 1_000_000_000
     * 운송비용: 1~100
     */
    public int init(int N, int sCity[], int eCity[], int mCost[]) {
        idxMap.clear();
        cities = new List[1400];
        backCities = new List[1400];
        int res = idx = 0;

        for (int i = 0; i < N; i++) {
            if (!idxMap.containsKey(sCity[i])) {
                idxMap.put(sCity[i], idx++);
                res++;
            }
            int startIdx = idxMap.get(sCity[i]);

            if (!idxMap.containsKey(eCity[i])) {
                idxMap.put(eCity[i], idx++);
                res++;
            }
            int endIdx = idxMap.get(eCity[i]);

            if (cities[startIdx] == null) cities[startIdx] = new ArrayList<>();
            // 단방향 그래프이므로 일단은 start만 넣는다.
            cities[startIdx].add(new City(eCity[i], mCost[i]));

            if (backCities[endIdx] == null) backCities[endIdx] = new ArrayList<>();
            // 반대로 가는 다익스트라를 한 번에 구하기 위해 반대 방향의 간선들도 저장한다.
            backCities[endIdx].add(new City(sCity[i], mCost[i]));
        }
        return res;
    }

    /**
     * @param sCity 출발 도시
     * @param eCity 도착 도시
     * @param mCost 운송 비용
     */
    public void add(int sCity, int eCity, int mCost) {
        if (!idxMap.containsKey(sCity)) {
            idxMap.put(sCity, idx++);
        }
        int startIdx = idxMap.get(sCity);
        if (!idxMap.containsKey(eCity)) {
            idxMap.put(eCity, idx++);
        }
        int endIdx = idxMap.get(eCity);
        cities[startIdx].add(new City(eCity, mCost));
        backCities[endIdx].add(new City(sCity, mCost));
    }

    /**
     * mHub에 물류 허브를 설칳라 경우 총 운송비용 반환
     */
    public int cost(int mHub) {
        int[] dist = new int[idx];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[idxMap.get(mHub)] = 0;
        PriorityQueue<City> pq = new PriorityQueue<>();
        pq.offer(new City(mHub, 0));
        while (!pq.isEmpty()) {
            City cur = pq.poll();
            int curIdx = idxMap.get(cur.number);
            if (cur.cost > dist[curIdx]) continue;
            for (City next : cities[curIdx]) {
                int nextIdx = idxMap.get(next.number);
                if (dist[nextIdx] > dist[curIdx] + next.cost) {
                    dist[nextIdx] = dist[curIdx] + next.cost;
                    pq.offer(new City(next.number, dist[nextIdx]));
                }
            }
        }
        int res = 0;
        for (int i = 0; i < idx; i++)
            res += dist[i];

        int[] reverseDist = new int[idx];
        Arrays.fill(reverseDist, Integer.MAX_VALUE);
        reverseDist[idxMap.get(mHub)] = 0;
        pq.offer(new City(mHub, 0));
        while (!pq.isEmpty()) {
            City cur = pq.poll();
            int curIdx = idxMap.get(cur.number);
            if (cur.cost > reverseDist[curIdx]) continue;
            for (City next : backCities[curIdx]) {
                int nextIdx = idxMap.get(next.number);
                if (reverseDist[nextIdx] > reverseDist[curIdx] + next.cost) {
                    reverseDist[nextIdx] = reverseDist[curIdx] + next.cost;
                    pq.offer(new City(next.number, reverseDist[nextIdx]));
                }
            }
        }

        for (int i = 0; i < idx; i++) {
            res += reverseDist[i];
        }
        return res;
    }
}
