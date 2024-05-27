import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int[][] dp = new int[N + 1][K + 1];

        // 0C0 = 1
        // 1C0 1C1
        // 2C0 2C1 2C2
        // 0Ck = 1
        // nCk = n-1Ck-1 + n-1Ck
        dp[0][0] = 1;
        for (int i = 1; i < N + 1; i++) {
            dp[i][0] = 1;
            for (int j = 1; j < Math.min(i + 1, K + 1); j++) {
                dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];
            }
        }
        System.out.println(dp[N][K]);
        br.close();
    }
}