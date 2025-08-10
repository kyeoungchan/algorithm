package baekjoon.경찰차;

import java.io.*;
import java.util.*;

public class Solution_bj_2618_경찰차2 {
    private static int N, W;
    private static int[][] events, dp;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        N = Integer.parseInt(br.readLine());
        W = Integer.parseInt(br.readLine());
        events = new int[W + 1][2];
        dp = new int[W + 1][W + 1];
        for (int i = 1; i < W + 1; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            events[i][0] = Integer.parseInt(st.nextToken());
            events[i][1] = Integer.parseInt(st.nextToken());
        }

        sb.append(dfs(1, 0, 0)).append("\n");

        int a = 0;
        int b = 0;
        for (int i = 1; i < W + 1; i++) {
            if (dp[a][b] == getDistance(1, a, i) + dp[i][b]) {
                sb.append(1).append("\n");
                a = i;
            } else {
                sb.append(2).append("\n");
                b = i;
            }
        }
        System.out.println(sb);
        br.close();
    }

    private static int dfs(int eventNum, int a, int b) {
        if (eventNum > W) {
            return 0;
        }

        if (dp[a][b] != 0) {
            return dp[a][b];
        }

        int aMoved = getDistance(1, a, eventNum) + dfs(eventNum + 1, eventNum, b);
        int bMoved = getDistance(2, b, eventNum) + dfs(eventNum + 1, a, eventNum);

        return dp[a][b] = Math.min(aMoved, bMoved);
    }

    private static int getDistance(int type, int start, int end) {
        // type=1 -> a / type=2 -> b
        int[] startPos = events[start];
        int[] endPos = events[end];

        if (start == 0) {
            if (type == 1) {
                startPos = new int[]{1, 1};
            } else {
                startPos = new int[]{N, N};
            }
        }

        return Math.abs(endPos[0] - startPos[0]) + Math.abs(endPos[1] - startPos[1]);
    }
}
