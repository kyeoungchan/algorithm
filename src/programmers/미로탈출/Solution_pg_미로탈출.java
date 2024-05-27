package programmers.미로탈출;

import java.util.*;

/**
 * 방과 방 사이를 연결하는 길에는 이동하는 데 걸리는 시간이 있다.
 * 미로에는 함정이 존재, 함정으로 이동 => "함정과 연결된" 모든 화살표의 방향이 반대로 바뀐다.
 * roads의 행은 [P, Q, S]로 이루어져 있습니다 => P에서 Q로 갈 수 있는 길이 있으며, 길을 따라 이동하는데 S만큼 시간이 걸립니다.
 * 함정 방의 번호를 담은 정수 배열 traps
 * 시작 방과 도착 방은 함정이 아닙니다.
 * 항상 미로를 탈출할 수 있는 경우만 주어집니다.
 */
public class Solution_pg_미로탈출 {
    public static void main(String[] args) {
        System.out.println(new Solution().solution(3, 1, 3, new int[][]{
                {1, 2, 2,},
                {3, 2, 3}
        }, new int[]{2}));
        System.out.println(new Solution().solution(4, 1, 4, new int[][]{
                {1, 2, 1,},
                {3, 2, 1},
                {2, 4, 1}
        }, new int[]{2, 3}));
    }

}

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

            boolean curTrapped = map.containsKey(curVertex) && (curBitMask & (1 << map.get(curVertex))) > 0;
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
                    // 양쪽 다 트랩이 아니거나 트랩이 맞더라도 활성화가 안 된 경우, 그냥 가면 된다.
                    if (g[curVertex][i] != 0 && dist[i][newBitMask] > curTime + g[curVertex][i]) {
                        dist[i][newBitMask] = curTime + g[curVertex][i];
                        pq.offer(new Road(i, dist[i][newBitMask], newBitMask));
                    }
                } else {
                    // 한 쪽만 트랩이 활성화 된 경우에는, 반대 방향으로 연결된 가중치로 가면 된다.
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
