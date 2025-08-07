import java.util.*;

public class Solution {
    final int MOD = 1_000_000_007;

    public int solution(int m, int n, int[][] puddles) {
        int[][] dp = new int[n][m];
        for (int[] puddle : puddles) {
            int r = puddle[1] - 1;
            int c = puddle[0] - 1;
            dp[r][c] = -1;
        }

        dp[0][0] = 1;

        int startC = 0;
        for (int i = 0; i < m - 1; i++) {
            startC++;
            int r = 0;
            int c = startC;
            while (r < n && c >= 0) {
                if (dp[r][c] != -1) {
                    if (r != 0 && dp[r - 1][c] != -1) {
                        dp[r][c] += dp[r - 1][c];
                    }
                    if (c != 0 && dp[r][c - 1] != -1) {
                        dp[r][c] += dp[r][c - 1];
                    }
                    dp[r][c] %= MOD;
                }
                r++;
                c--;
            }
        }

        int startR = 0;
        for (int i = 0; i < n - 1; i++) {
            startR++;
            int r = startR;
            int c = m - 1;
            while (r < n && c >= 0) {
                if (dp[r][c] != -1) {
                    if (r != 0 && dp[r - 1][c] != -1) {
                        dp[r][c] += dp[r - 1][c];
                    }
                    if (c != 0 && dp[r][c - 1] != -1) {
                        dp[r][c] += dp[r][c - 1];
                    }
                    dp[r][c] %= MOD;
                }
                r++;
                c--;
            }
        }
        return dp[n - 1][m - 1];
    }
}
