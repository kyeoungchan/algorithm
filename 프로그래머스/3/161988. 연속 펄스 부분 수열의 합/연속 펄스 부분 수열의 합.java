import java.util.*;

class Solution {
    
    long INF = 50_000_000_001L;
    public long solution(int[] sequence) {
        // System.out.println(INF);
        long answer = -INF;
        int n = sequence.length;
        long[][] sum = new long[n + 1][2];
        
        long sign = 1L;
        long min1 = 0L;
        long min2 = 0L;
        answer = Math.max(min1, min2);
        for (int i = 1; i <= n; i++) {
            sum[i][0] = sequence[i-1] * sign + sum[i-1][0];
            sum[i][1] = -sequence[i-1] * sign + sum[i-1][1];
            sign *= -1;
            
            min1 = Math.min(min1, sum[i][0]);
            
            answer = Math.max(answer, sum[i][0] - min1);
            
            min2 = Math.min(min2, sum[i][1]);
            
            answer = Math.max(answer, sum[i][1] - min2);
            
        }
        // for (int i = 0; i < n; i++) {
        //     System.out.println(sum[i][0] + ", " + sum[i][1]);
        // }
        return answer;
    }
}