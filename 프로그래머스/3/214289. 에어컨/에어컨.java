import java.util.*;

class Solution {
    
    int INF = 123456789;
    
    public int solution(int temperature, int t1, int t2, int a, int b, int[] onboard) {
        int len = onboard.length;
        // -10 ~ 40 -> 0 ~ 50
        // dp[i][j] = i시간에 j 온도가 되는데 최소 비용
        int[][] dp = new int[len][51];
        
        for (int i = 0; i < len; i++) {
            Arrays.fill(dp[i], INF);
        }
        
        int initTemp = temperature + 10;
        dp[0][initTemp] = 0;
        
        
        for (int i = 1; i < len; i++) {
            for (int j = 0; j <= 50; j++) {
                if (dp[i-1][j] == INF) continue;
                
                // 끄는 경우
                int nextTemp = j == initTemp ? initTemp : j > initTemp ? j - 1 : j + 1;
                // 추가 소비전력 없음
                if (canReach(nextTemp, onboard[i], t1, t2)) {
                    dp[i][nextTemp] = Math.min(dp[i][nextTemp], dp[i-1][j]);    
                }
                
                
                // 켜서 온도를 유지시키는 경우
                nextTemp = j;
                if (canReach(nextTemp, onboard[i], t1, t2)) {
                    dp[i][nextTemp] = Math.min(dp[i][nextTemp], dp[i-1][j] + b);
                }
                
                // 온도를 높이는 경우
                nextTemp = j + 1;
                if (canReach(nextTemp, onboard[i], t1, t2)) {
                    dp[i][nextTemp] = Math.min(dp[i][nextTemp], dp[i-1][j] + a);
                }
                
                // 온도를 낮추는 경우
                nextTemp = j - 1;
                if (canReach(nextTemp, onboard[i], t1, t2)) {
                    dp[i][nextTemp] = Math.min(dp[i][nextTemp], dp[i-1][j] + a);
                }
            }
        }
        // printDp(dp);
        int answer = INF;
        for (int i = 0; i <= 50; i++) {
            answer = Math.min(answer, dp[len - 1][i]);
        }
        return answer;
    }
    
    boolean canReach(int temp, int isConsumer, int t1, int t2) {
        if (isConsumer == 1) {
            // 승객이 있다면 t1~t2 범위내의 온도만 가능하다.
            return (t1 + 10) <= temp && temp <= (t2 + 10);
        }
        // 승객이 없다면 0~50 사이면 OK
        return 0 <= temp && temp <= 50;
    }
    
    void printDp(int[][] dp) {
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                if (dp[i][j] == INF) System.out.printf("INF ", dp[i][j]);
                else System.out.printf("%3d ", dp[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
}