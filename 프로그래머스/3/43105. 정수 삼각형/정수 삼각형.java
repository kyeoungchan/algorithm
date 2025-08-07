public class Solution {
    public int solution(int[][] triangle) {
        int answer = 0;
        int height = triangle.length;
        int[][] dp = new int[height][triangle[height - 1].length];
        dp[0][0] = triangle[0][0];
        for (int i = 1; i < height; i++) {
            for (int j = 0; j < i + 1; j++) {
                int leftUp = j == 0 ? 0 : dp[i - 1][j - 1];
                int rightUp = j == i ? 0 : dp[i - 1][j];
                dp[i][j] = triangle[i][j] + Math.max(leftUp, rightUp);
                if (i == height - 1) {
                    answer = Math.max(answer, dp[i][j]);
                }
            }
        }
        return answer;
    }
}
