package baekjoon.주난의난;

import java.util.*;
import java.io.*;

public class Solution_bj_14491_주난의난 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine(), " ");
        int x1 = Integer.parseInt(st.nextToken()) - 1;
        int y1 = Integer.parseInt(st.nextToken()) - 1;
        int x2 = Integer.parseInt(st.nextToken()) - 1;
        int y2 = Integer.parseInt(st.nextToken()) - 1;
        int[][] map = new int[N][M];
        int[][] dist = new int[N][M];
        for (int i = 0; i < N; i++) {
            String s = br.readLine();
            for (int j = 0; j < M; j++) {
                if (s.charAt(j) == '#' || s.charAt(j) == '*') map[i][j] = 0;
                else map[i][j] = s.charAt(j) - '0';
                dist[i][j] = Integer.MAX_VALUE;
            }
        }
        int[] di = {-1, 0, 1, 0}, dj = {0, 1, 0, -1};
        boolean[][] v = new boolean[N][M];
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1[2], o2[2]));
        pq.offer(new int[]{x1, y1, 1});
        dist[x1][y1] =  1;
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int ci = cur[0];
            int cj = cur[1];
            int cost = cur[2];

            if (v[ci][cj]) continue;
            v[ci][cj] = true;
            if (ci == x2 && cj == y2) break;

            for (int d = 0; d < 4; d++) {
                int ni = ci + di[d];
                int nj = cj + dj[d];
                if (ni < 0 || ni > N - 1 || nj < 0 || nj > M - 1) continue;
                if (dist[ni][nj] > cost) {
                    dist[ni][nj] = cost;
                    pq.offer(new int[]{ni, nj, map[ni][nj] + cost});
                }
            }
        }
        System.out.println(dist[x2][y2]);
        br.close();
    }
}
