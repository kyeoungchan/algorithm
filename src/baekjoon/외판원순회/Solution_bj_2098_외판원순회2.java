package baekjoon.외판원순회;

import java.io.*;
import java.util.*;

public class Solution_bj_2098_외판원순회2 {

    static int N, INF = 1_000_000 * 16 + 1;
    static int[][] graph, dp;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        graph = new int[N][N];
        dp = new int[N][1 << N];

        StringTokenizer st;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                graph[i][j] = Integer.parseInt(st.nextToken());
            }
            Arrays.fill(dp[i], -1);
        }

        travel(0, 1);

        System.out.println(dp[0][1]);
        br.close();
    }

    static void travel(int num, int status) {
        if (status == (1 << N) - 1) {
            if (graph[num][0] == 0) {
                dp[num][status] = INF;
            } else {
                dp[num][status] = graph[num][0];
            }
            return;
        }

        if (dp[num][status] != -1) return;

        dp[num][status] = INF;

        for (int next = 0; next < N; next++) {
            if (graph[num][next] == 0 || ((1 << next) & status) > 0) {
                continue;
            }
            int nStatus = (1 << next) | status;
            travel(next, nStatus);
            dp[num][status] = Math.min(dp[num][status], graph[num][next] + dp[next][nStatus]);
        }
    }
}
