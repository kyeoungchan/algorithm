package baekjoon.castledefense;

import java.util.*;
import java.io.*;
/*
* */
public class Baekjoon17135_CastleDefense {

    static int[] dI = {0, -1, 0};
    static int[] dJ = {-1, 0, 1};

    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());
        int[][] map = new int[n][m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        int result = 0;
        // 첫 번째 궁수가 있을 수 있는 인덱스는 0~m-3
        for (int a1 = 0; a1 < m - 2; a1++) {
            // 두 번째 궁수가 있을 수 있는 인덱스는 a1 + 1 ~ m - 2
            for (int a2 = a1 + 1; a2 < m - 1; a2++) {
                // 세 번째 궁수가 있을 수 있는 인덱스는 a2 + 1 ~ m - 1
                for (int a3 = a2 + 1; a3 < m; a3++) {
                    result = Math.max(result, calculateScore(map, a1, a2, a3, d, n, m));
                }
            }
        }
        System.out.println(result);
        br.close();
    }

    static int calculateScore(int[][] map, int a1, int a2, int a3, int d, int n, int m) {
        int score = 0;
        boolean[][] killed = new boolean[n][m];
        for (int round = 1; round <= n; round++) {
            int lowerBound = n + 1 - round;
            score += bfs(map, a1, d, lowerBound, killed, n, m);
            score += bfs(map, a2, d, lowerBound, killed, n, m);
            score += bfs(map, a3, d, lowerBound, killed, n, m);
        }
        return score;
    }

    static int bfs(int[][] map, int a, int d, int lowerBound, boolean[][] killed, int n, int m) {
        Deque<int[]> queue = new ArrayDeque<>();
        boolean[][] visited = new boolean[n][m];
        int nI = lowerBound - 1;
        int nJ = a;
        if (d == 1) {
            if (map[nI][nJ] == 1) {
                killed[nI][nJ] = true;
                return 1;
            }
            return 0;
        }
        queue.addLast(new int[]{nI, nJ, 1});
        while (!queue.isEmpty()) {
            int[] polled = queue.poll();
            int polledI = polled[0];
            int polledJ = polled[1];
            int distance = polled[2];

            for (int i = 0; i < 3; i++) {
                int newI = polledI + dI[i];
                if (newI < 0 || newI >= n) {
                    continue;
                }
                int newJ = polledJ + dJ[i];
                if (newJ < 0 || newJ >= m) {
                    continue;
                }
                if (map[newI][newJ] == 1) {
                    killed[newI][newJ] = true;
                    return 1;
                } else if (!visited[newI][newJ] && distance < d && newI > lowerBound){
                    visited[newI][newJ] = true;
                    queue.addLast(new int[] {newI, newJ, distance + 1});
                }
            }
        }
        return 0;
    }
}
