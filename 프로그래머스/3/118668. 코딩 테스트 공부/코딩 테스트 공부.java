import java.util.*;

class Solution {
    
    int INF = 123456789;
    
    public int solution(int alp, int cop, int[][] problems) {
        int answer = 0;
        int alpMax = 0;
        int copMax = 0;
        for (int[] problem: problems) {
            alpMax = Math.max(alpMax, problem[0]);
            copMax = Math.max(copMax, problem[1]);
        }
        
        
        if (alp >= alpMax && cop >= copMax) return 0;
        int[][] dp = new int[Math.max(alp, alpMax) + 1][Math.max(cop, copMax) + 1];
        
        for (int i = 0; i <= Math.max(alp, alpMax); i++) Arrays.fill(dp[i], INF);
        
        int startAlp = Math.min(alp, alpMax);
        int startCop = Math.min(cop, copMax);
        dp[startAlp][startCop] = 0;
        for (int i = startAlp; i <= alpMax; i++) {
            
            for (int j = startCop; j <= copMax; j++) {
                // if (i == alp && j == cop) continue;
                if (i > startAlp) {
                    dp[i][j] = Math.min(dp[i][j], dp[i-1][j] + 1);
                }
                if (j > startCop) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][j-1] + 1);
                }
                for (int[] problem: problems) {
                    if (i < problem[0] || j < problem[1]) continue;
                    int afterAlp = Math.min(alpMax, i + problem[2]);
                    int afterCop = Math.min(copMax, j + problem[3]);
                    dp[afterAlp][afterCop] = Math.min(dp[afterAlp][afterCop], dp[i][j] + problem[4]);
                    
                }
            }
        }
        
        return dp[alpMax][copMax];
    }
}