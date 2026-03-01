package baekjoon.LCS2;

import java.io.*;
import java.util.*;

public class Solution_bj_9252_LCS2_2 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str1 = "-" + br.readLine();
        String str2 = "-" + br.readLine();

        int[][] dp = new int[str1.length()][str2.length()];

        for (int i = 1; i < str1.length(); i++) {
            char ch1 = str1.charAt(i);
            for (int j = 1; j < str2.length(); j++) {
                char ch2 = str2.charAt(j);
                if (ch1 == ch2) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        StringBuilder sb = new StringBuilder();

        int target = dp[str1.length() - 1][str2.length() - 1];
        int next = str2.length() - 1;
        for (int i = str1.length() - 1; i > 0; i--) {
            char ch1 = str1.charAt(i);
            for (int j = next; j > 0; j--) {
                char ch2 = str2.charAt(j);
                if (target == dp[i][j] && ch1 == ch2) {
                    sb.append(ch1);
                    target--;
                    next = j - 1;
                    break;
                }
            }
        }

        sb.reverse();
        System.out.println(dp[str1.length() - 1][str2.length() - 1]);
        System.out.println(sb);

        br.close();
    }
}
