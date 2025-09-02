package baekjoon.내리막길;

import java.io.*;
import java.util.*;

public class Solution_bj_1520_내리막길4 {

    static int M, N;
    static int[] dr = new int[]{-1, 0, 1, 0}, dc = new int[]{0, 1, 0, -1};
    static int[][] map, dp;

    public static void main(String[] args) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            M = Integer.parseInt(st.nextToken());
            N = Integer.parseInt(st.nextToken());

            map = new int[M][N];
            dp = new int[M][N];
            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                    dp[i][j] = -1;
                }
            }
            dp[0][0] = 1;

            System.out.println(search(M - 1, N - 1));
        }
    }

    private static int search(int r, int c) {
        if (dp[r][c] != -1) {
            return dp[r][c];
        }

        dp[r][c]++;

        for (int d = 0; d < 4; d++) {
            int nr = r + dr[d];
            int nc = c + dc[d];
            if (nr < 0 || nr >= M || nc < 0 || nc >= N || map[nr][nc] <= map[r][c]) continue;
            dp[r][c] += search(nr, nc);
        }
        return dp[r][c];
    }
}
