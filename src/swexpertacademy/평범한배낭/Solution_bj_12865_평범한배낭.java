package swexpertacademy.평범한배낭;

import java.io.*;
import java.util.*;

public class Solution_bj_12865_평범한배낭 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int[] dp = new int[K + 1]; // 최대 K만큼의 무게만을 넣을 수 있다.
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int W = Integer.parseInt(st.nextToken());
            int V = Integer.parseInt(st.nextToken());
            for (int j = K; j >= W; j--)
                dp[j] = Math.max(dp[j], dp[j - W] + V);
        }
        System.out.println(dp[K]);
        br.close();
    }
}
