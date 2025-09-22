package programmers.산모양타일링;

public class Solution_pg_산모양타일링 {
    public int solution(int n, int[] tops) {
        int mod = 10_007;
        int[][] dp = new int[n][2];
        //[n][0]: 윗변이 n일 때, 제일 오른쪽에 마름모를 붙이지 않은 경우
        //[n][1]: 윗변이 n일 때, 제일 오른쪽에 마름모를 붙인 경우
        dp[0][0] = tops[0] == 1 ? 3 : 2;
        dp[0][1] = 1;
        for (int i = 1; i < n; i++) {
            int mul1 = tops[i] == 1 ? 3 : 2;
            int mul2 = tops[i] == 1 ? 2 : 1;
            dp[i][0] = (dp[i-1][0] * mul1 + dp[i-1][1] * mul2) % mod;
            dp[i][1] = (dp[i-1][0] + dp[i-1][1]) % mod;
        }
        return (dp[n-1][0] + dp[n-1][1]) % mod;
    }
}
