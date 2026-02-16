import java.io.*;
import java.util.*;

/**
 * a층의 b호에 살기 위해서 -> a-1층의 1호부터 b호까지 사람들의 수의 합만큼 사람들을 데려와 살아야 한다.
 * dp[a][b] = dp[a-1][1] + ... + dp[a-1][b]
 */
public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int T = Integer.parseInt(br.readLine());

        int[][] dp = new int[15][15];
        for (int i = 1; i < 15; i++) {
            dp[0][i] = i;
        }

        for (int i = 1; i < 15; i++) {
            for (int j = 1; j < 15; j++) {
                if (j == 1) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i][j - 1] + dp[i - 1][j];
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int t = 1; t <= T; t++) {
            int k = Integer.parseInt(br.readLine());
            int n = Integer.parseInt(br.readLine());
            sb.append(dp[k][n]).append("\n");
        }

        System.out.println(sb.toString());
        br.close();
    }
}