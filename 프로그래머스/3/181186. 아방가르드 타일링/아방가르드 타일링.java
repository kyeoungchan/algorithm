class Solution {
    
    final int MOD = 1_000_000_007;
    
    public int solution(int n) {
//         long[] dp = new long[Math.max(n + 1, 7)];
//         dp[1] = 1; dp[2] = 3; dp[3] = 10;
//         dp[4] = 23; dp[5] = 62; dp[6] = 170;

//         for (int i = 7; i <= n; i++) {
//             dp[i] = dp[i-1] + 2*dp[i-2] + 6*dp[i-3] + dp[i-4] - dp[i-6];
//             dp[i] = ((dp[i] % MOD) + MOD) % MOD;  // -dp[i-6] 때문에 음수 가능
//         }
//         return (int) dp[n];
        long[] dp = new long[Math.max(n + 1, 4)];
        dp[0] = 1;
        dp[1] = 1;
        dp[2] = 3;
        dp[3] = 10;
        
        long[] h = new long[3];
        
        for (int i = 4; i <= n; i++) {
            dp[i] += dp[i - 1] + 2 * dp[i - 2] + 5 * dp[i - 3];
            dp[i] %= MOD;
            
            h[(i - 4) % 3] += dp[i - 4];
            h[(i - 4) % 3] %= MOD;
            
            dp[i] += (h[0] + h[1] + h[2] + h[i % 3]) * 2;
            dp[i] %= MOD;
        }
        
        return (int) dp[n];
    }
}