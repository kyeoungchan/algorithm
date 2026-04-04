import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        int MOD = 1000000;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String encryptedCode = br.readLine();
        int len = encryptedCode.length();

        int[] dp = new int[len + 1];
        dp[0] = 1;

        for (int i = 1; i <= len; i++) {
            char cur =  encryptedCode.charAt(i - 1);
            if (cur != '0') {
                dp[i] += dp[i - 1];
                dp[i] %= MOD;
            }

            if (i == 1) continue;
            int value = Integer.parseInt(encryptedCode.substring(i - 2, i));
            if (value >= 10 && value <= 26) {
                dp[i] += dp[i - 2];
                dp[i] %= MOD;
            }
        }

        System.out.println(dp[len]);
        br.close();
    }
}