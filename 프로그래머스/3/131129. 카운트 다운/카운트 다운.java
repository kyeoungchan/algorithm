import java.util.*;

class Solution {
    int INF = 123456789;
    public int[] solution(int target) {
        int[][] dp = new int[target + 1][2];
        for (int i = 0; i <= target; i++) dp[i][0] = INF;
        
        dp[0][0] = dp[0][1] = 0;
        dp[1][0] = dp[1][1] = 1;
        for (int score = 2; score <= target; score++) {
            if (score >= 50) {
                update(dp, score, 50, true);
            }
            for (int type = 1; type <= 3; type++) {
                for (int i = 20; i >= 1; i--) {
                    if (i * type > score) continue;
                    update(dp, score, i * type, type == 1);
                }
            }
            // printDp(dp);
        }
        return dp[target];
    }
    
    void update(int[][] dp, int targetScore, int plusScore, boolean singleBool) {
        int singleBoolCnt = singleBool ? 1 : 0;
        if (dp[targetScore][0] > dp[targetScore-plusScore][0] + 1) {
            dp[targetScore][0] = dp[targetScore-plusScore][0] + 1;
            dp[targetScore][1] = dp[targetScore-plusScore][1] + singleBoolCnt;
        } else if (dp[targetScore][0] == dp[targetScore-plusScore][0] + 1 && 
                  dp[targetScore][1] < dp[targetScore-plusScore][1] + singleBoolCnt) {
            dp[targetScore][1] = dp[targetScore-plusScore][1] + singleBoolCnt;
        }
        
    }
    
    void printDp(int[][] dp) {
        for (int i = 0; i < dp.length; i++) {
            System.out.println(dp[i][0] + ", " + dp[i][1]);
        }
        System.out.println();
    }
}