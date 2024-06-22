import java.io.*;
import java.util.*;

/**
 * 0 1 1 1 1 1
 * 1 1 1 2 2 2
 */
public class Solution {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            st = new StringTokenizer(br.readLine(), " ");
            String a = "_" + st.nextToken();
            String b = "_" + st.nextToken();
            int aLength = a.length();
            int bLength = b.length();

            int[][] dp = new int[aLength][bLength];
            for (int i = 1; i < aLength; i++) {
                char aChar = a.charAt(i);
                for (int j = 1; j < bLength; j++) {
                    if (aChar == b.charAt(j)) {
                        dp[i][j] = dp[i - 1][j - 1] + 1;
                    } else {
                        dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                    }
                }
            }
            sb.append("#").append(tc).append(" ").append(dp[aLength-1][bLength-1]).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }
}