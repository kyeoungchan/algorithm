import java.util.*;

class Solution {
    
    int MOD = 10007;
    public int solution(int n, int[] tops) {
        // 두번째 수는 오른쪽 아래가 잘린 경우의 수
        int[][] dp = new int[n + 1][2];
        if (tops[0] == 0) {
            dp[1][0] = 3;
            dp[1][1] = 2;
        } else {
            dp[1][0] = 4;
            dp[1][1] = 3;
        }
        if (n == 1) {
            return dp[1][0];
        }
        dp[0][0] = 1;
        /*
        top[n] = 1
        dp[n][0] = dp[n-1][0] * 3  + dp[n-1][1];
        dp[n][1] = dp[n-1][1] * 3 + 2 * dp[n-2][0];
        
        top[n] = 0
        dp[n][0] = dp[n-1][0] * 2 + dp[n-1][1];
        dp[n][1] = dp[n-1][1] * 2 + dp[n-2][0];
        */
        for (int i = 2; i <= n; i++) {
            if (tops[i-1] == 1) {
                dp[i][0] = (3 * dp[i-1][0] % MOD + dp[i-1][1]) % MOD;
                dp[i][1] = (3 * dp[i-1][1] % MOD + 2 * dp[i-2][0] % MOD) % MOD;
            } else {
                dp[i][0] = (2 * dp[i-1][0] % MOD + dp[i-1][1]) % MOD;
                dp[i][1] = (2 * dp[i-1][1] % MOD + dp[i-2][0]) % MOD;
            }
        }

        return dp[n][0];
    }
}