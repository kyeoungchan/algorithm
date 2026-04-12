import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 0; tc < T; tc++) {
            int N = Integer.parseInt(br.readLine());
            int[] coins = new int[N];
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                coins[i] = Integer.parseInt(st.nextToken());
            }
//            System.out.println(Arrays.toString(coins));
            int M = Integer.parseInt(br.readLine());
            int[] dp = new int[M + 1];
            dp[0]++;
            for (int i = 0; i < N; i++) {
                int coin = coins[i];
                for (int c = coin; c <= M; c++) {
                    dp[c] += dp[c - coin];
                }
//                System.out.println("coin = " + coin);
//                System.out.println("Arrays.toString(dp) = " + Arrays.toString(dp));
//                System.out.println();
            }
            sb.append(dp[M]).append("\n");
//            System.out.println();
        }
        System.out.println(sb);
        br.close();
    }
}