package baekjoon.외판원순회2;

import java.util.*;
import java.io.*;

public class Solution_bj_10971_외판원순회2_backtracking {

    static int N, minCost;
    static boolean[] visited;
    static int[][] W;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());
        W = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < N; j++) {
                W[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        visited = new boolean[N];
        minCost = Integer.MAX_VALUE;
        visited[0] = true;
        travel(0, 0, 1);
        System.out.println(minCost);
        br.close();
    }

    static void travel(int idx, int cost, int cnt) {
        if (cnt == N) {
            if (W[idx][0] != 0) {
                cost += W[idx][0];
                minCost = Math.min(minCost, cost);
            }
            return;
        }

        for (int i = 0; i < N; i++) {
            if (visited[i] || W[idx][i] == 0) continue;
            visited[i] = true;
            travel(i, cost + W[idx][i], cnt + 1);
            visited[i] = false;
        }
    }
}
