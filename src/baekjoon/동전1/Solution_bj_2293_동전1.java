package baekjoon.동전1;

import java.io.*;
import java.util.*;

/**
 * n 종류의 동전이 있다.
 * 그 가치의 합이 k원이 되도록 하고 싶다.
 * 경우의 수를 구하라.
 * 구성이 같지만 순서만 다른 것은 같은 경우다.
 *
 * 참고: https://lotuslee.tistory.com/113
 */
public class Solution_bj_2293_동전1 {

    static int n, k;
    static int[] dp;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        dp = new int[k + 1];
        dp[0] = 1;

        for (int i = 0; i < n; i++) {
            int coin = Integer.parseInt(br.readLine());
            for (int j = 1; j <= k; j++) {
                if (j - coin < 0) continue;
                dp[j] += dp[j - coin];
            }
        }

        System.out.println(dp[k]);

        br.close();
    }

}
