import java.util.*;

class Solution {
    int INF = 123456789;
    public int solution(int alp, int cop, int[][] problems) {
        
        // Arrays.sort(problems, (p1, p2) -> p1[0] == p2[0] ? Integer.compare(p1[1], p2[1]) : Integer.compare(p1[0], p2[0]));
        int alpMax = 0;
        int copMax = 0;
        for (int[] problem: problems) {
            alpMax = Math.max(alpMax, problem[0]);
            copMax = Math.max(copMax, problem[1]);
        }
        int[][] dp = new int[alpMax + 1][copMax + 1];
        
        for (int i = 0; i <= alpMax; i++) Arrays.fill(dp[i], INF);
        
        int startAlp = Math.min(alp, alpMax);
        int startCop = Math.min(cop, copMax);
        // dp[startAlp][startCop] = 0;
        
        for (int i = startAlp; i <= alpMax; i++) {
            int studyCost = i-startAlp;
            for (int j = startCop; j <= copMax; j++) {
                dp[i][j] = Math.min(dp[i][j], studyCost++);
                for (int[] problem: problems) {
                    int alpReq = problem[0];
                    int copReq = problem[1];
                    int alpRwd = problem[2];
                    int copRwd = problem[3];
                    int problemCost = problem[4];
                    if (i < alpReq || j < copReq) continue;
                    int afterAlp = i + alpRwd;
                    afterAlp = Math.min(afterAlp, alpMax);
                    int afterCop = j + copRwd;
                    afterCop = Math.min(afterCop, copMax);
                    
                    
                    dp[afterAlp][afterCop] = Math.min(dp[afterAlp][afterCop], dp[i][j] + problemCost);
                }
            }
        }
        
        return dp[alpMax][copMax];
    }
}