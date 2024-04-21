package baekjoon.내리막길;

import java.io.*;
import java.util.*;

/**
 * 한 지점의 높이가 있다.
 * 상하좌우 이동 가능
 * 더 낮은 지점으로만 이동하여 목표 지점까지 가고자한다.
 * 경로의 개수를 구하는 프로그램을 작성
 */
public class Solution_bj_1520_내리막길 {

    static int N, M;
    static int[] di = {-1, 0, 1, 0}, dj = {0, 1, 0, -1};
    static int[][] map, dp;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][M];
        dp = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
            Arrays.fill(dp[i], -1);
        }

        int cnt = dfs(0, 0);
        System.out.println(cnt);
        br.close();
    }

    static int dfs(int r, int c) {
        if (r == N - 1 && c == M - 1) return 1;

        if (dp[r][c] != -1) return dp[r][c];

        dp[r][c] = 0;
        for (int d = 0; d < 4; d++) {
            int nr = r + di[d];
            int nc = c + dj[d];
            if (nr < 0 || nr > N - 1 || nc < 0 || nc > M - 1 || map[nr][nc] >= map[r][c]) continue;
            dp[r][c] += dfs(nr, nc);
        }
        return dp[r][c];
    }
}

