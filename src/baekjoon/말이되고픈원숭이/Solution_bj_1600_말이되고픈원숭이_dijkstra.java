package baekjoon.말이되고픈원숭이;

import java.io.*;
import java.util.*;

public class Solution_bj_1600_말이되고픈원숭이_dijkstra {

    static int W, H, map[][], INF = 987654321;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int K = Integer.parseInt(br.readLine()); // 원숭이가 흉내낼 수 있는 횟수
        st = new StringTokenizer(br.readLine(), " ");
        W = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());
        map = new int[H][W];
        int[][][] dist = new int[H][W][K + 1];
        for (int i = 0; i < H; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < W; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                for (int k = 0; k < K + 1; k++) {
                    dist[i][j][k] = INF;
                }
            }
        }

        int[] di = {-1, 0, 1, 0}, dj = {0, 1, 0, -1}, dhi = {-2, -1, 1, 2, 2, 1, -1, -2}, dhj = {1, 2, 2, 1, -1, -2, -2, -1};

        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1[2], o2[2]));
        for (int k = 0; k < K + 1; k++) {
            dist[0][0][k] = 0;
        }
        pq.offer(new int[]{0, 0, 0, 0});
        int answer = INF;
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int ci = cur[0];
            int cj = cur[1];
            int cnt = cur[2];
            int likeHorse = cur[3];
            if (ci == H - 1 && cj == W - 1 && cnt < answer) answer = cnt;

            if (dist[ci][cj][likeHorse] < cnt) continue;

            for (int d = 0; d < 4; d++) {
                int ni = ci + di[d];
                int nj = cj + dj[d];
                if (impossible(ni, nj)) continue;
                if (dist[ni][nj][likeHorse] > dist[ci][cj][likeHorse] + 1) {
                    dist[ni][nj][likeHorse] = dist[ci][cj][likeHorse] + 1;
                    pq.offer(new int[]{ni, nj, dist[ni][nj][likeHorse], likeHorse});
                }
            }

            if (likeHorse == K) continue;
            for (int d = 0; d < 8; d++) {
                int ni = ci + dhi[d];
                int nj = cj + dhj[d];
                if (impossible(ni, nj)) continue;
                int nLikeHorse = likeHorse + 1;
                if (dist[ni][nj][nLikeHorse] > dist[ci][cj][likeHorse] + 1) {
                    dist[ni][nj][nLikeHorse] = dist[ci][cj][likeHorse] + 1;
                    pq.offer(new int[]{ni, nj, dist[ni][nj][nLikeHorse], nLikeHorse});
                }
            }
        }
//        for (int i = 0; i < H; i++) {
//            for (int j = 0; j < W; j++) {
//                System.out.print(dist[i][j] + " ");
//            }
//            System.out.println();
//        }
//        System.out.println();
        if (answer == INF) answer = -1;
        System.out.println(answer);
        br.close();
    }

    static boolean impossible(int r, int c) {
        return r < 0 || r > H - 1 || c < 0 || c > W - 1 || map[r][c] == 1;
    }
}
