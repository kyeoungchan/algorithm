class Solution {
    public int[] solution(int e, int[] starts) {
        int[] count = new int[e + 1];
        for (int i = 1; i <= e; i++) {
            for (int j = i; j <= e; j += i) {
                count[j]++;
            }
        }
        int max = 0;
        int num = 0;
        int[] dp = new int[e + 1];
        for (int i = e; i >= 1; i--) {
            if (max <= count[i]) {
                max = count[i];
                num = i;
            }
            dp[i] = num;
        }
        int n = starts.length;
        int[] answer = new int[n];
        for (int i = 0; i < n; i++) {
            answer[i] = dp[starts[i]];
        }
        return answer;
    }
}