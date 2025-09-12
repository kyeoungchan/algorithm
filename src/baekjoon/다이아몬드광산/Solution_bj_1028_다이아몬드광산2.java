package baekjoon.다이아몬드광산;

import java.io.*;
import java.util.*;

public class Solution_bj_1028_다이아몬드광산2 {

    private static int R, C;
    private static int[] dr = new int[]{-1, -1, 1, 1}, dc = new int[]{-1, 1, 1, -1};
    private static int[][] map;
    private static int[][][] dp;

    public static void main(String[] args) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            R = Integer.parseInt(st.nextToken());
            C = Integer.parseInt(st.nextToken());
            map = new int[R][C];
            dp = new int[R][C][4];
            for (int i = 0; i < R; i++) {
                String s = br.readLine();
                for (int j = 0; j < C; j++) {
                    map[i][j] = s.charAt(j) - '0';
                }
            }

            int answer = 0;
            for (int i = 0; i < R; i++) {
                for (int j = 0; j < C; j++) {
                    if (map[i][j] == 0) continue;
                    setSize(i, j);

                    if (dp[i][j][0] > answer && dp[i][j][1] > answer) {
                        int targetSize = Math.min(dp[i][j][0], dp[i][j][1]);
                        for (int len = targetSize; len > answer; len--) {
                            int upR = i - 2 * (len - 1);
                            if (upR >= 0) {
                                if (dp[upR][j][2] >= len && dp[upR][j][3] >= len) {
                                    answer = len;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            System.out.println(answer);
        }
    }

    private static void setSize(int r, int c) {
        for (int d = 0; d < 4; d++) {
            int size = 1;
            int nr = r + dr[d];
            int nc = c + dc[d];
            while (nr >= 0 && nr < R && nc >= 0 && nc < C && map[nr][nc] == 1) {
                size++;
                nr += dr[d];
                nc += dc[d];
            }
            dp[r][c][d] = size;
        }
    }
}
