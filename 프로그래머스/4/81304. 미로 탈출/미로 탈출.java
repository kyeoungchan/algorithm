import java.util.*;

class Solution {
    static class Road implements Comparable<Road> {
        int vertex, time, bitMask;

        public Road(int vertex, int time) {
            this.vertex = vertex;
            this.time = time;
            bitMask = 0;
        }

        public Road(int vertex, int time, int bitMask) {
            this.vertex = vertex;
            this.time = time;
            this.bitMask = bitMask;
        }

        @Override
        public int compareTo(Road o) {
            return Integer.compare(time, o.time);
        }

        @Override
        public String toString() {
            return "Road{" +
                    "vertex=" + vertex +
                    ", time=" + time +
                    ", bitMask=" + bitMask +
                    '}';
        }
    }

    public int solution(int n, int start, int end, int[][] roads, int[] traps) {
        int answer = 0;
        int trapCnt = traps.length;
        int[][] g = new int[n + 1][n + 1];
        int[][] dist = new int[n + 1][1 << trapCnt];
        for (int i = 1; i < n + 1; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }

        for (int[] edge : roads) {
            int from = edge[0];
            int to = edge[1];
            int time = edge[2];
            if (g[from][to] == 0) g[from][to] = time;
            else g[from][to] = Math.min(g[from][to], time);
        }

        PriorityQueue<Road> pq = new PriorityQueue<>();
        pq.offer(new Road(start, 0));
        Map<Integer, Integer> map = new HashMap<>(); // 번호로 트랩 인덱스를 찾기 위한 맵
        for (int i = 0; i < traps.length; i++) {
            map.put(traps[i], i);
        }

        while (!pq.isEmpty()) {
            Road cur = pq.poll();
            int curVertex = cur.vertex;
            int curTime = cur.time;
            int curBitMask = cur.bitMask;

            if (dist[curVertex][curBitMask] < curTime) continue;
            if (curVertex == end) {
                answer = curTime;
                break;
            }

            boolean curTrapped = map.containsKey(curVertex) ? (curBitMask & (1 << map.get(curVertex))) > 0 : false;
            for (int i = 1; i < n + 1; i++) {
                if (i == cur.vertex) continue;
                boolean nextIsTrap = map.containsKey(i);
                boolean nextTrapped = nextIsTrap && (curBitMask & (1 << map.get(i))) > 0;
                int newBitMask = curBitMask;
                if (nextIsTrap && !nextTrapped) {
                    newBitMask |= (1 << map.get(i));
                } else if (nextIsTrap && nextTrapped) {
                    newBitMask &= ~(1 << map.get(i));
                }
                if (curTrapped == nextTrapped) {
                    if (g[curVertex][i] != 0 && dist[i][newBitMask] > curTime + g[curVertex][i]) {
                        dist[i][newBitMask] = curTime + g[curVertex][i];
                        pq.offer(new Road(i, dist[i][newBitMask], newBitMask));
                    }
                } else {

                    if (g[i][curVertex] != 0 && dist[i][newBitMask] > curTime + g[i][curVertex]) {
                        dist[i][newBitMask] = curTime + g[i][curVertex];
                        pq.offer(new Road(i, dist[i][newBitMask], newBitMask));
                    }
                }
            }

        }
        return answer;
    }
}