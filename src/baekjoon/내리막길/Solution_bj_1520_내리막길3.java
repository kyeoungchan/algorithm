package baekjoon.내리막길;

import java.util.*;
import java.io.*;

public class Solution_bj_1520_내리막길3 {

    static int N, M;
    static int[] dr = new int[] {-1, 0, 1, 0}, dc = new int[]{0, 1, 0, -1};
    static int[][] dp, map;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        dp = new int[N][M];
        map = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
            Arrays.fill(dp[i], -1);
        }
        dp[0][0] = 1;
        System.out.println(dfs(N - 1, M - 1));
        br.close();
    }

    private static int dfs(int r, int c) {
        if (dp[r][c] != -1) {
            return dp[r][c];
        }

        dp[r][c]++;
        for (int d = 0; d < 4; d++) {
            int nr = r + dr[d];
            int nc = c + dc[d];
            if (nr >= 0 && nr < N && nc >= 0 && nc < M && map[nr][nc] > map[r][c]) {
                dp[r][c] += dfs(nr, nc);
            }
        }
        return dp[r][c];
    }
}
