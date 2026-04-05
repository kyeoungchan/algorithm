import java.io.*;
import java.util.*;

public class Main {

    static int INF = 1_000 * 1_000 + 1;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int N = Integer.parseInt(br.readLine());
        int[][] costs = new int[N][3];
        int[][] dp = new int[N][3];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            costs[i][0] = Integer.parseInt(st.nextToken());
            costs[i][1] = Integer.parseInt(st.nextToken());
            costs[i][2] = Integer.parseInt(st.nextToken());
        }
        int answer = INF;

        for (int firstColor = 0; firstColor < 3; firstColor++) {
            for (int color = 0; color < 3; color++) {
                if (color == firstColor) {
                    dp[0][color] = costs[0][color];
                } else  {
                    dp[0][color] = INF;
                }
            }

            for (int i = 1; i < N; i++) {
                dp[i][0] = Math.min(dp[i - 1][1], dp[i - 1][2]) + costs[i][0];
                dp[i][1] = Math.min(dp[i - 1][0], dp[i - 1][2]) + costs[i][1];
                dp[i][2] = Math.min(dp[i - 1][0], dp[i - 1][1]) + costs[i][2];
            }

            for (int color = 0; color < 3; color++) {
                if (color == firstColor) continue;
                answer = Math.min(answer, dp[N - 1][color]);
            }
        }

        System.out.println(answer);

        br.close();
    }
}