import java.io.*;
import java.util.*;

/**
 * https://www.acmicpc.net/problem/10942
 */
public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        int[] arr = new int[N + 1];

        for (int i = 1; i <= N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        Integer[][] dp = new Integer[N + 1][N + 1];

        for (int i = 1; i <= N; i++) {
            dp[i][i] = 1;
        }
        if (N > 1) {
            // 연속 두자리 체크
            for (int i = 1; i < N; i++) {
                dp[i][i + 1] = arr[i] == arr[i + 1] ? 1 : 0;
            }
        }

        for (int d = 2; d < N; d++) {

            for (int i = 1; i <= N - d; i++) {
                int S = i;
                int E = i + d;
                dp[S][E] = arr[S] == arr[E] && dp[S + 1][E - 1] == 1 ? 1 : 0;
            }
        }
/*
        for (int i = 0; i < N + 1; i++) {
            System.out.println(Arrays.toString(dp[i]));
        }
        System.out.println();
*/

        int M =  Integer.parseInt(br.readLine());

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int S = Integer.parseInt(st.nextToken());
            int E = Integer.parseInt(st.nextToken());
            sb.append(dp[S][E]).append("\n");
        }

        System.out.println(sb.toString());
        br.close();
    }
}