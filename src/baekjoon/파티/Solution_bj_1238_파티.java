package baekjoon.파티;

import java.util.*;
import java.io.*;

/**
 * N개의 숫자로 구분된 마을에 한 명의 학생이 산다.
 * N명의 학생이 X번 마을에 모여서 파티를 하기로 했다.
 * N명의 학생들 중 가장 오고 가는데 가장 많은 시간을 소비하는 학생을 구하라.
 */
public class Solution_bj_1238_파티 {

    static final int INF = 123456789;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int X = Integer.parseInt(st.nextToken());
        List<int[]>[] edges = new List[N + 1];
        int[] dist = new int[N + 1];
        for (int i = 1; i < N + 1; i++) {
            edges[i] = new ArrayList<>();
            dist[i] = INF;
        }
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            edges[a].add(new int[]{b, cost});
        }

        // X까지 가는데 걸린 최소시간들 업데이트
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1[1], o2[1]));
        pq.offer(new int[]{X, 0});
        dist[X] = 0;
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int minVertex = cur[0];
            int min = cur[1];
            if (dist[minVertex] < min) continue;
            for (int[] edge : edges[minVertex]) {
                int nextV = edge[0];
                int cost = edge[1];
                if (dist[nextV] > min + cost) {
                    dist[nextV] = min + cost;
                    pq.offer(new int[]{nextV, dist[nextV]});
                }
            }
        }

        // X에서 각각 원래 위치로 돌아가는 데 걸린 시간들 업데이트
        int max = 0;
        int[] toDist = new int[N + 1];
        for (int i = 1; i < N + 1; i++) {
            if (i == X) continue;
            Arrays.fill(toDist, INF);
            toDist[i] = 0;
            pq.clear();
            pq.offer(new int[]{i, 0});
            while (!pq.isEmpty()) {
                int[] cur = pq.poll();
                int minVertex = cur[0];
                if (minVertex == X) break;
                int min = cur[1];
                if (toDist[minVertex] < min) continue;
                for (int[] edge : edges[minVertex]) {
                    int nextV = edge[0];
                    int cost = edge[1];
                    if (toDist[nextV] > min + cost) {
                        toDist[nextV] = min + cost;
                        pq.offer(new int[]{nextV, toDist[nextV]});
                    }
                }
            }
            // 왕복 시간으로 업데이트, 그리고 최댓값 업데이트
            dist[i] += toDist[X];
            if (dist[i] > max) max = dist[i];
        }
        System.out.println(max);
        br.close();
    }
}
