public class Solution {
    public int solution(String arr[]) {
        int operatorCount = arr.length / 2;
        int operandCount = operatorCount + 1;
        String[] operators = new String[operatorCount];
        int[] operands = new int[operandCount];
        for (int i = 0; i < arr.length; i++) {
            if (i % 2 == 0) {
                operands[i / 2] = Integer.parseInt(arr[i]);
            } else {
                operators[i / 2] = arr[i];
            }
        }

        int[][][] dp = new int[operandCount][operandCount][2];
        for (int i = 0; i < operandCount; i++) {
            dp[i][i][0] = dp[i][i][1] = operands[i];
        }

        for (int len = 1; len < operandCount; len++) {
            for (int s = 0; s < operandCount - len; s++) {
                int e = s + len;
                int max = Integer.MIN_VALUE;
                int min = Integer.MAX_VALUE;
                for (int i = s; i < e; i++) {
                    if (operators[i].equals("+")) {
                        max = Math.max(max, dp[s][i][0] + dp[i + 1][e][0]);
                        min = Math.min(min, dp[s][i][1] + dp[i + 1][e][1]);
                    } else {
                        max = Math.max(max, dp[s][i][0] - dp[i + 1][e][1]);
                        min = Math.min(min, dp[s][i][1] - dp[i + 1][e][0]);   
                    }
                }
                dp[s][e][0] = max;
                dp[s][e][1] = min;
            }
        }

        return dp[0][operandCount - 1][0];
    }
}
