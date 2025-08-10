package programmers.사칙연산;

public class Solution_pg_사칙연산 {

    public int solution(String arr[]) {
        int operatorCount = arr.length / 2;
        String[] operators = new String[operatorCount];
        int operandCount = operatorCount + 1;
        int[] operands = new int[operandCount]; // 피연산자들 메모
        for (int i = 0; i < arr.length; i++) {
            if (i % 2 != 0) {
                operators[i / 2] = arr[i];
            } else {
                operands[i / 2] = Integer.parseInt(arr[i]);
            }
        }

        int[][][] dp = new int[operandCount][operandCount][2];
        // 길이가 0인 경우
        for (int i = 0; i < operandCount; i++) {
            dp[i][i][0] = dp[i][i][1] = operands[i];
            // [0]이 최대, [1]이 최소
        }

        // 길이가 1~operandCount-1까지
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

/*
        for (int i = 0; i < operandCount; i++) {
            for (int j = 0; j < operandCount; j++) {
                System.out.print("(" + Arrays.toString(dp[i][j]) + ") ");
            }
            System.out.println();
        }
*/
        return dp[0][operandCount - 1][0];
    }
}
