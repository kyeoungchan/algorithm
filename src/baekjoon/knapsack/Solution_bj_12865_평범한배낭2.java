package baekjoon.knapsack;

import java.util.*;
import java.io.*;

public class Solution_bj_12865_평범한배낭2 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken()); // 준서가 버틸 수 있는 무게
        int[] dp = new int[K + 1]; // 무게별로 배낭이 담을 수 있는 최대 가격 메모
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int wi = Integer.parseInt(st.nextToken()); // W
            int vi = Integer.parseInt(st.nextToken()); // V
            for (int w = K; w >= wi; w--) {
                dp[w] = Math.max(dp[w], dp[w - wi] + vi);
            }
        }

        System.out.println(dp[K]);
        br.close();
    }
}
