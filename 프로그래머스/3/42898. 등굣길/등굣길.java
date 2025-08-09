public class Solution {
    public int solution(int m, int n, int[][] puddles) {
        int[][] dp = new int[n + 1][m + 1];
        dp[1][1] = 1;
        for (int[] puddle : puddles) {
            dp[puddle[1]][puddle[0]] = -1;
        }

        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < m + 1; j++) {
                if (dp[i][j] == -1) {
                    dp[i][j]++;
                    continue;
                }
                dp[i][j] += dp[i - 1][j];
                dp[i][j] += dp[i][j - 1];
                dp[i][j] %= 1_000_000_007;
            }
        }
        return dp[n][m];
    }
}
