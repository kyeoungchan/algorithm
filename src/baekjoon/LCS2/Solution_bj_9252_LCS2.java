package baekjoon.LCS2;

import java.io.*;
import java.util.*;

public class Solution_bj_9252_LCS2 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s1 = "-" + br.readLine();
        String s2 = "-" + br.readLine();
        StringBuilder sb = new StringBuilder();

        int[][] dp = new int[s1.length()][s2.length()];

        for (int i = 1; i < s1.length(); i++) {
            for (int j = 1; j < s2.length(); j++) {
                if (s1.charAt(i) == s2.charAt(j)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        int target = dp[s1.length() - 1][s2.length() - 1];
        int next = s2.length() - 1;
        for (int i = s1.length() - 1; i > 0; i--) {

            for (int j = next; j > 0; j--) {
                if (s1.charAt(i) == s2.charAt(j) && dp[i][j] == target) {
                    sb.append(s1.charAt(i));
                    target--;
                    next = j - 1;
                    break;
                }
            }
        }

/*
        for (int i = 0; i < s1.length(); i++) {
            System.out.println(Arrays.toString(dp[i]));
        }
*/

        System.out.println(dp[s1.length() - 1][s2.length() - 1]);
        System.out.println(sb.reverse().toString());
        br.close();
    }
}
