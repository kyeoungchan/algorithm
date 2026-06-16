class Solution {
    
    final int MOD = 1_000_000_007;
    
    public int solution(int n) {
        long[] dp = new long[Math.max(n + 1, 7)];
        dp[1] = 1; dp[2] = 3; dp[3] = 10;
        dp[4] = 23; dp[5] = 62; dp[6] = 170;

        for (int i = 7; i <= n; i++) {
            dp[i] = dp[i-1] + 2*dp[i-2] + 6*dp[i-3] + dp[i-4] - dp[i-6];
            dp[i] = ((dp[i] % MOD) + MOD) % MOD;  // -dp[i-6] 때문에 음수 가능
        }
        return (int) dp[n];
    }
}