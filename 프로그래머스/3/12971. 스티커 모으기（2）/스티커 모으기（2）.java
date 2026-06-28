import java.util.*;

class Solution {
    public int solution(int sticker[]) {
        int N = sticker.length;
        if (N == 1) return sticker[0];
        if (N == 2) return Math.max(sticker[0], sticker[1]);
        if (N == 3) return Math.max(Math.max(sticker[0], sticker[1]), sticker[2]);

        // 0번째 인덱스 포함한 경우와 0번째 인덱스를 포함하지 않은 경우로 dp
        int[][] dp = new int[N][2];
        dp[0][0] = sticker[0];
        
        dp[1][0] = dp[1][1] = sticker[1];
        
        dp[2][0] = dp[0][0] + sticker[2];
        dp[2][1] = sticker[2];
        
        for (int i = 3; i < N - 1; i++) {
            dp[i][0] = Math.max(dp[i-2][0], dp[i-3][0]) + sticker[i];
            dp[i][1] = Math.max(dp[i-2][1], dp[i-3][1]) + sticker[i];
        }
        
        
        // N-1번째 인덱스는 0을 포함하지 않은 경우만 볼 수 있다.
        dp[N-1][0] = Math.max(dp[N-2][0], dp[N-3][0]);
        dp[N-1][1] = Math.max(dp[N-3][1], dp[N-4][1]) + sticker[N-1];
        
        
        return Math.max(dp[N-1][0], dp[N-1][1]);
    }
}