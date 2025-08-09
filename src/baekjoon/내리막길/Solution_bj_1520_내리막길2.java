package baekjoon.내리막길;

import java.io.*;
import java.util.*;

public class Solution_bj_1520_내리막길2 {
    static int M, N;
    static int[] dr = new int[]{-1, 0, 1, 0};
    static int[] dc = new int[]{0, 1, 0, -1};
    static int[][] map, dp;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        M = Integer.parseInt(st.nextToken()); // 세로 크기
        N = Integer.parseInt(st.nextToken()); // 가로 크기
        map = new int[M][N];
        dp = new int[M][N];
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
            Arrays.fill(dp[i], -1);
        }

        dp[0][0] = 1;
        System.out.println(dfs(M - 1, N - 1));
        br.close();
    }

    private static int dfs(int r, int c) {
        if (dp[r][c] != -1) {
            // 방문한적이 있다면
            return dp[r][c];
        }

        // 방문한적이 있어도 갈 수 있는 경우가 없을 수 있다. 0인 경우
        dp[r][c]++;
        for (int d = 0; d < 4; d++) {
            int nr = r + dr[d];
            int nc = c + dc[d];
            if (nr < 0 || nr > M - 1 || nc < 0 || nc > N - 1 || map[nr][nc] <= map[r][c]) continue;
            dp[r][c] += dfs(nr, nc);
        }
        return dp[r][c];
    }
}
