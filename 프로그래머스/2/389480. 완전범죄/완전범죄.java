import java.util.*;

class Solution {
    
    int INF = Integer.MAX_VALUE;
    
    public int solution(int[][] info, int n, int m) {
        int[] dp = new int[n];
        
        dp[0] = 0;
        for (int[] subInfo: info) {
            int aTrace = subInfo[0];
            int bTrace = subInfo[1];
            
            int[] newDp = new int[n];
            Arrays.fill(newDp, INF);
            
            for (int aSum = 0; aSum < n; aSum++) {
                if (dp[aSum] == INF) continue;
                
                
                int bSum = dp[aSum];
                
                int newASum = aSum + aTrace;
                if (newASum < n) {
                    newDp[newASum] = Math.min(newDp[newASum], bSum);
                }
                
                int newBSum = bSum + bTrace;
                if (newBSum < m) {
                    newDp[aSum] = Math.min(newDp[aSum], newBSum);
                }
            }
            dp = newDp;
            
        }
        
        for (int aSum = 0; aSum < n; aSum++) {
            if (dp[aSum] != INF) return aSum;
        }
        return -1;
    }
}