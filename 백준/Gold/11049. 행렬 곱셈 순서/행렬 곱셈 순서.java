import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N =  Integer.parseInt(br.readLine());

        // 각 행렬의 행 수와 마지막 행렬의 열
        int[][] matrices = new int[N][2];

        StringTokenizer st;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            matrices[i][0] = Integer.parseInt(st.nextToken());
            matrices[i][1] = Integer.parseInt(st.nextToken());
        }

        int[][] dp = new int[N][N];
        for (int s = 0; s < N - 1; s++) {
            dp[s][s + 1] = matrices[s][0] * matrices[s][1] * matrices[s + 1][1];
        }

        for (int d = 2; d < N; d++) {
            for (int s = 0; s < N - d; s++) {
                int min = Integer.MAX_VALUE;

                for (int d2 = 0; d2 < d; d2++) {
                    int A = matrices[s][0];
                    int B = matrices[s + d2][1];
                    int C = matrices[s + d][1];

                    int ops = A * B * C;
                    ops += dp[s][s + d2];
                    ops += dp[s + d2 + 1][s + d];
                    min = Math.min(min, ops);
                }

                dp[s][s + d] = min;
            }
        }

        System.out.println(dp[0][N - 1]);
        br.close();
    }
}