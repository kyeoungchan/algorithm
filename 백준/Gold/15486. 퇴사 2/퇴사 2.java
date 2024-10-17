import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int N = Integer.parseInt(br.readLine());
        long[] dp = new long[N + 1];
        // 50 50 50
        //    20
        //       100
        for (int i = 1; i < N + 1; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int t = Integer.parseInt(st.nextToken()); // 기간
            int p = Integer.parseInt(st.nextToken()); // 금액
            if (i + t - 1 > N) {
                dp[i] = Math.max(dp[i], dp[i - 1]);
                continue;
            }
            dp[i + t - 1] = Math.max(dp[i - 1] + p, dp[i + t - 1]);
            if (t != 1) dp[i] = Math.max(dp[i], dp[i - 1]);
        }
        long ans = 0L;
        for (int i = 1; i < N + 1; i++) {
//            System.out.println(dp[i]);
            ans = Math.max(ans, dp[i]);
        }
        System.out.println(ans);
        br.close();
    }
}