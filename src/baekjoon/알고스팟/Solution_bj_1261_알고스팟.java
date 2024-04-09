package baekjoon.알고스팟;

import java.util.*;
import java.io.*;

/**
 * 미로의 크기: N x M
 * 미로는 빈방 또는 벽으로 이루어져있고, 벽은 부술 수 있다.
 * 이동방법: 상하좌우
 * 벽을 최소 몇개 부숴야하는지 구하는 프로그램
 * 입력: N, M
 * 0: 빈방, 1: 벽
 * 다익스트라를 활용해서 각 좌표마다 벽을 최소로 부수고 갈 수 있는 거리를 계산
 */
public class Solution_bj_1261_알고스팟 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int M = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(st.nextToken());
        int[][] maze = new int[N][M];
        int[][] dist = new int[N][M];
        for (int i = 0; i < N; i++) {
            String s = br.readLine();
            for (int j = 0; j < M; j++) {
                maze[i][j] = s.charAt(j) - '0';
                dist[i][j] = Integer.MAX_VALUE;
            }
        }
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1[2], o2[2]));
        pq.offer(new int[]{0, 0, 0});
        dist[0][0] = 0;
        int[] di = {-1, 0, 1, 0}, dj = {0, 1, 0, -1};
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int ci = cur[0];
            int cj = cur[1];
            int cost = cur[2];
            if (cost > dist[ci][cj]) continue;

            for (int d = 0; d < 4; d++) {
                int ni = ci + di[d];
                int nj = cj + dj[d];
                if (ni < 0 || ni > N - 1 || nj < 0 || nj > M - 1) continue;
                if (dist[ni][nj] > cost + maze[ni][nj]) {
                    dist[ni][nj] = cost + maze[ni][nj];
                    pq.offer(new int[]{ni, nj, dist[ni][nj]});
                }
            }
        }
        System.out.println(dist[N - 1][M - 1]);
        br.close();
    }
}
