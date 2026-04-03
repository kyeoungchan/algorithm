import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[][] dp = new int[n + 1][m + 1];
        int[][] grid = new int[n + 1][m + 1];


        for (int i = 1; i <= n; i++) {
            String s = br.readLine();
            for (int j = 1; j <= m; j++) {
                grid[i][j] = s.charAt(j-1) - '0';
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1] - dp[i - 1][j - 1] + grid[i][j];
            }
        }

/*
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < m + 1; j++) {
                System.out.print(dp[i][j]);
            }
            System.out.println();
        }
        System.out.println();
*/

        int result = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (grid[i][j] == 0) continue;
                int temp = 1;
                int d = 1;
                while (i + d <= n && j + d <= m) {
                    int ni = i + d;
                    int nj = j + d;
                    d++;
                    int temp2 = d * d;
/*
                    System.out.println("ni = " + ni);
                    System.out.println("nj = " + nj);
                    System.out.println("d = " + d);
                    System.out.println("temp = " + temp);
                    System.out.println("temp2 = " + temp2);
                    System.out.println("cal = " + (dp[ni][nj] - dp[ni][j] - dp[i][nj] + dp[ni][nj]));
                    System.out.println("result = " + result);
                    System.out.println();
*/
                    if (grid[ni][nj] == 0 || dp[ni][nj] - dp[ni][j-1] - dp[i-1][nj] + dp[i-1][j-1] != temp2) {
//                        result = Math.max(result, temp);
                        break;
                    }
                    temp = temp2;
                }
                result = Math.max(result, temp);
            }
        }

        System.out.println(result);
        br.close();
    }
}