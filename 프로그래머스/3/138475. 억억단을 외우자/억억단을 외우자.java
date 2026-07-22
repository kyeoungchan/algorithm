import java.util.*;

class Solution {
    public int[] solution(int e, int[] starts) {
        int[] dp = new int[e+1];
        // dp[1] = 1;
        // 1부터 e까지 각 숫자의 약수 개수 구하기
        for (int i = 1; i <= e; i++) {
            for (int j = i; j <= e; j += i) {
                dp[j]++;
            }
        }
        // System.out.println(Arrays.toString(dp));
        int[] answerArr = new int[e + 1];
        int maxCnt = 0;
        int maxNum = 0;
        for (int i = e; i >= 1; i--) {
            if (dp[i] >= maxCnt) {
                maxCnt = dp[i];
                maxNum = i;
            }
            answerArr[i] = maxNum;
        }
        
        int[] answer = new int[starts.length];
        for (int i = 0; i < starts.length; i++) {
            answer[i] = answerArr[starts[i]];
        }
        return answer;
    }
}