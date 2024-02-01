package baekjoon.makingbridge;

import java.util.*;
import java.io.*;

public class Solution_bj_17472_makingBridge {

    static int[][] map;
    static int[][] graph;
    static int[] dI = {-1, 0, 1, 0};
    static int[] dJ = {0, 1, 0, -1};
    static int N, M;

    static int findParent(int x, int[] parent) {
        if (parent[x] != x) {
            parent[x] = findParent(parent[x], parent);
        }
        return parent[x];
    }

    static void unionParent(int a, int b, int[] parent) {
        a = findParent(a, parent);
        b = findParent(b, parent);

        if (a < b) {
            parent[b] = a;
        } else {
            parent[a] = b;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        boolean[][] visited = new boolean[N][M];
        int islandNum = 1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] == 1 && !visited[i][j]) {
                    checkIsland(i, j, visited, islandNum);
                    islandNum++;
                }
            }
        }

        int[][] bridgeCheck = new int[islandNum + 1][islandNum + 1];
        for (int i = 0; i < islandNum; i++) {
            for (int j = 0; j < islandNum; j++) {

            }
        }

        br.close();
    }

    private static void checkIsland(int i, int j, boolean[][] visited, int islandNum) {
        Deque<int[]> queue = new ArrayDeque<>();
        queue.addLast(new int[]{i, j});
        map[i][j] = islandNum;
        visited[i][j] = true;
        while (!queue.isEmpty()) {
            int[] polled = queue.poll();
            int polledI = polled[0];
            int polledJ = polled[1];
            for (int d = 0; d < 4; d++) {
                int nI = polledI + dI[d];
                int nJ = polledJ + dJ[d];
                if (0 <= nI && nI < N && 0 <= nJ && nJ < N && map[nI][nJ] == 1 && !visited[nI][nJ]) {
                    visited[nI][nJ] = true;
                    map[nI][nJ] = islandNum;
                    queue.addLast(new int[]{nI, nI});
                }
            }
        }
    }

}
