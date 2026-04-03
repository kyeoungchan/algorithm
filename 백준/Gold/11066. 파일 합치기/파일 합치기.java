import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 0; tc < T; tc++) {
            int K = Integer.parseInt(br.readLine());
            int[] sumFiles = new int[K + 1];
            int[][] dp = new int[K + 1][K + 1];
            st = new StringTokenizer(br.readLine());
            sumFiles[1] = Integer.parseInt(st.nextToken());
            for (int i = 2; i <= K; i++) {
                sumFiles[i] = Integer.parseInt(st.nextToken()) + sumFiles[i - 1];
            }

            for (int len = 1; len < K; len++) {

                for (int start = 1; start + len <= K; start++) {
                    int end = start + len;
                    dp[start][end] = Integer.MAX_VALUE;
                    for (int idx = start; idx < end; idx++) {
                        int sum = dp[start][idx] + dp[idx + 1][end] + sumFiles[idx] - sumFiles[start - 1] + sumFiles[end] - sumFiles[idx];
                        
                        dp[start][end] = Math.min(dp[start][end], sum);
                    }
                }
            }
            sb.append(dp[1][K]).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }
}