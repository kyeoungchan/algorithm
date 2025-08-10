package programmers.등굣길;

import java.util.Arrays;

public class Solution_pg_등굣길2 {

    final int MOD = 1_000_000_007;

    public int solution(int m, int n, int[][] puddles) {
        int[][] dp = new int[n + 1][m + 1];
        for (int[] pos : puddles) {
            dp[pos[1]][pos[0]]--;
        }
        dp[1][1] = 1;
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < m + 1; j++) {
                if (dp[i][j] == -1) {
                    // 물 웅덩이라면 다음 길에 영향을 주지 않도록 0으로 변경 후 건너뛰기
                    dp[i][j]++;
                    continue;
                }
                dp[i][j] += dp[i - 1][j];
                dp[i][j] += dp[i][j - 1];
                dp[i][j] %= MOD;
            }
        }
        for (int i = 0; i < n + 1; i++) {
            System.out.println(Arrays.toString(dp[i]));
        }
        return dp[n][m];
    }
}
