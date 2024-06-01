package baekjoon.이동하기;

import java.util.*;
import java.io.*;

public class Solution_bj_11048_이동하기2 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        // dp를 할 때 이렇게 한줄씩 여분을 두면 if문으로 관리하지 않아도 Math.max()로 범위 밖의 값(0)들은 영향을 주지 않게 된다.
        int[][] dp = new int[N + 1][M + 1];
        for (int i = 1; i < N + 1; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 1; j < M + 1; j++) {
                int value = Integer.parseInt(st.nextToken());
                dp[i][j] = Math.max(Math.max(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + value;
            }
        }
        System.out.println(dp[N][M]);
        br.close();
    }
}
