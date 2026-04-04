package baekjoon.가장큰정사각형;

import java.io.*;
import java.util.*;

public class Solution_bj_1915_가장큰정사각형2 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[][] grid = new int[n + 1][m + 1];
        int[][] dp = new int[n + 1][m + 1];

        for (int i = 1; i <= n; i++) {
            String s = br.readLine();
            for (int j = 1; j <= m; j++) {
                grid[i][j] = s.charAt(j - 1) - '0';
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1] - dp[i - 1][j - 1] + grid[i][j];
            }
        }

        int answer = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (grid[i][j] == 0) continue;
                int temp = 1;
                int d = 1;
                while (i + d <= n && j + d <= m) {
                    int ni = i + d;
                    int nj = j + d;
                    int temp2 = dp[ni][nj] - dp[ni][j - 1] - dp[i - 1][nj] + dp[i - 1][j - 1];
                    d++;
                    if (grid[ni][nj] == 0 || temp2 != d * d) {
                        break;
                    }
                    temp = temp2;
                }
                answer = Math.max(answer, temp);
            }
        }
        System.out.println(answer);
        br.close();
    }
}
