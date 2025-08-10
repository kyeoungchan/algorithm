package baekjoon.LCS;

import java.io.*;

public class Solution_bj_9251_LCS {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s1 = "-" + br.readLine(); // 인덱스 처리를 1부터 하기 위해 "-" 더 추가
        String s2 = "-" + br.readLine();
        int[][] dp = new int[s1.length()][s2.length()];

        for (int i = 1; i < s1.length(); i++) {
            char c1 = s1.charAt(i);
            for (int j = 1; j < s2.length(); j++) {
                if (c1 == s2.charAt(j)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        System.out.println(dp[s1.length() - 1][s2.length() - 1]);
        br.close();
    }
}
