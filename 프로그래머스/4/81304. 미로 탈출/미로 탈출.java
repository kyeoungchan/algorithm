import java.io.*;
import java.util.*;

public class Solution {

    static class Road implements Comparable<Road> {
        int number, cost, status;

        public Road(int number, int cost, int status) {
            this.number = number;
            this.cost = cost;
            this.status = status;
        }

        @Override
        public int compareTo(Road o) {
            return Integer.compare(cost, o.cost);
        }
    }
    public int solution(int n, int start, int end, int[][] roads, int[] traps) {

        int ans = 0;
        int[][] g = new int[n + 1][n + 1];
        for (int[] edge : roads) {
            int from = edge[0], to = edge[1];
            int cost = edge[2];
            if (g[from][to] == 0)
                g[from][to] = cost;
            else
                g[from][to] = Math.min(cost, g[from][to]);
        }

        int[][] dist = new int[n + 1][1 << traps.length];
        for (int i = 1; i < n + 1; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }
        // 번호로 트랩 인덱스 찾기
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < traps.length; i++) {
            map.put(traps[i], i);
        }
        PriorityQueue<Road> pq = new PriorityQueue<>();
        pq.offer(new Road(start, 0, 0));
        dist[start][0] = 0;
        while (!pq.isEmpty()) {
            Road cur = pq.poll();
            int curNum = cur.number;
            int curCost = cur.cost;
            int curStatus = cur.status;
            if (dist[curNum][curStatus] < curCost) continue;
            if (curNum == end) {
                ans = curCost;
                break;
            }

            boolean isTrap = map.containsKey(curNum);
            boolean currentTrapped = isTrap && (curStatus & 1 << map.get(curNum)) > 0;
            for (int i = 1; i < n + 1; i++) {
                int nextStatus = curStatus;
                boolean isNextTrap = map.containsKey(i);
                boolean nextTrapped = isNextTrap && (curStatus & 1 << map.get(i)) > 0;
                if (isNextTrap) {
                    if (!nextTrapped)
                        nextStatus |= 1 << map.get(i);
                    else
                        nextStatus &= ~(1 << map.get(i));
                }
                if (currentTrapped == nextTrapped) {
                    if (g[curNum][i] != 0 && dist[i][nextStatus] > curCost + g[curNum][i]) {
                        dist[i][nextStatus] = curCost + g[curNum][i];
                        pq.offer(new Road(i, dist[i][nextStatus], nextStatus));
                    }
                } else {
                    if (g[i][curNum] != 0 && dist[i][nextStatus] > curCost + g[i][curNum]) {
                        dist[i][nextStatus] = curCost + g[i][curNum];
                        pq.offer(new Road(i, dist[i][nextStatus], nextStatus));
                    }
                }
            }
        }

        return ans;
    }
}
