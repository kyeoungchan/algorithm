import java.util.*;

class Solution {
    public int solution(int n) {
        int MOD = 1_000_000_007;
        long[] dp = new long[Math.max(n + 1, 4)];
        long[] H = new long[3];
        dp[0] = 1;
        dp[1] = 1;
        dp[2] = 3;
        dp[3] = 10;
        /*
        
        dp[n] = 1 * dp[n - 1] + 2 * dp[n - 2] + 5 * dp[n - 3]
              + 2 * dp[n - 4] + 2 * dp[n - 5] + 4 * dp[n - 6]
        dp[4] = dp[3] + 2 * dp[2] + 5 * dp[1] 
          + 2 * dp[0]; 4->0, 1
        dp[5] = dp[4] + 2 * dp[3] + 5 * dp[2]
          + 2 * dp[1] + 2 * dp[0] 5->1, 2
        dp[6] = dp[5] + 2 * dp[4] + 5 * dp[3]
          + 2 * dp[2] + 2 * dp[1] + 5 * dp[0]
        dp[n] - dp[n - 1] = dp[n - 1] + dp[n] + 3 * dp[n - 2] - 3 * dp[n - 3]
        */
        
        H[0] = 11;
        H[1] = 1;
        H[2] = 3;
        
        for (int i = 4; i <= n; i++) {
            int idx = i % 3;
            dp[i] = (2 * (H[0] + H[1] + H[2]) - dp[i - 1] + MOD) % MOD;
            dp[i] += 2 * H[idx];
            dp[i] %= MOD;
            dp[i] += dp[i - 3];
            dp[i] %= MOD;
            H[idx] += dp[i];
            H[idx] %= MOD;
        }
        
        return (int) dp[n];
    }
}