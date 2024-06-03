import java.io.*;
import java.util.*;

/**
 * 계단에는 점수가 있다.
 * 다음 계단이나, 다다음 계단으로 오를 수 있다.
 * 마지막 도착 계단은 반드시 밟아야한다.
 * 점수의 최댓값을 구하는 프로그램
 */
public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int stairCnt = Integer.parseInt(br.readLine());
        int[] stairScores = new int[stairCnt + 1];
        for (int i = 1; i < stairCnt + 1; i++)
            stairScores[i] = Integer.parseInt(br.readLine());

        // 한칸 건너뛰고의 점수 누적과, 연속으로 2칸 갔을 때의 점수 누적
        int[][] dp = new int[stairCnt + 1][2];
        dp[1][0] = stairScores[1];

        for (int i = 2; i < stairCnt + 1; i++) {
            dp[i][0] = stairScores[i] + Math.max(dp[i - 2][0], dp[i - 2][1]);
            // 연속된 3개의 계단을 모두 밟을 수는 없다.
            dp[i][1] = stairScores[i] + dp[i - 1][0];
        }
        System.out.println(Math.max(dp[stairCnt][0], dp[stairCnt][1]));
        br.close();
    }
}