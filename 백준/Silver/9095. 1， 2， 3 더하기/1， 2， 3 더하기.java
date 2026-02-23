import java.io.*;
import java.util.*;

/**
 * https://www.acmicpc.net/problem/9095
 */
public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());
        for (int t = 0; t < T; t++) {
            int n = Integer.parseInt(br.readLine());
            int[] dp = new int[n + 1];
            dp[1] = 1;
            if (n > 1) {
                dp[2] = 2;
                if (n > 2) {
                    dp[3] = 4;
                }
            }
            /* 1
            * 1+1 2
            * 1+1+1 2+1 / 1+2 3
            * 1+1+1+1 2+1+1 1+2+1 3+1 / 1+1+2 / 2+2 / 1+3
            * 1+1+1+1+1 2+1+1+1 1+2+1+1 3+1+1 1+1+2+1 2+2+1 1+3+1 / 1+1+1+2 2+1+2 1+2+2 3+2 / 1+1+3 2+3 /*/
            for (int i = 4; i <= n; i++) {
                dp[i] = dp[i - 1] + dp[i - 2] + dp[i - 3];
            }
            sb.append(dp[n]).append("\n");
        }
        System.out.print(sb);
        br.close();
    }
}