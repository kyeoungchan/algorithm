package baekjoon.castledefense;

import java.util.*;
import java.io.*;

public class Baekjoon17135_CastleDefense {

    static int n;
    static int m;
    static int d;
    static int[][] map;
    static int[] dI = {0, -1, 0};
    static int[] dJ = {-1, 0, 1};
    static int result;
    static int[] picked = new int[3];

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        d = Integer.parseInt(st.nextToken());
        map = new int[n][m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < m; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
/*
        // 첫 번째 궁수가 있을 수 있는 인덱스는 0~m-3
        for (int a1 = 0; a1 < m - 2; a1++) {
            // 두 번째 궁수가 있을 수 있는 인덱스는 a1 + 1 ~ m - 2
            for (int a2 = a1 + 1; a2 < m - 1; a2++) {
                // 세 번째 궁수가 있을 수 있는 인덱스는 a2 + 1 ~ m - 1
                for (int a3 = a2 + 1; a3 < m; a3++) {
                    result = Math.max(result, calculateScore(a1, a2, a3));
                }
            }
        }
*/
        pick(0, 0);

        System.out.println(result);
        br.close();
    }

    static void pick(int cnt, int start) {
        if (cnt == 3) {
            result = Math.max(result, calculateScore(picked[0], picked[1], picked[2]));
            return;
        }
        for (int i = start; i < m; i++) {
            picked[cnt] = i;
            pick(cnt + 1, i + 1);
        }
    }

    static int calculateScore(int a1, int a2, int a3) {
        int score = 0;
        boolean[][] killed = new boolean[n][m];
        for (int round = 1; round <= n; round++) {
            int lowerBound = n + 1 - round;
            int[] killedPos1 = bfs(a1, lowerBound, killed);
            int[] killedPos2 = bfs(a2, lowerBound, killed);
            int[] killedPos3 = bfs(a3, lowerBound, killed);
            if (killedPos1 != null) {
                int aI1 = killedPos1[0];
                int aJ1 = killedPos1[1];
                if (!killed[aI1][aJ1]) {
                    killed[aI1][aJ1] = true;
                    score++;
                }
            }
            if (killedPos2 != null) {
                int aI2 = killedPos2[0];
                int aJ2 = killedPos2[1];
                if (!killed[aI2][aJ2]) {
                    killed[aI2][aJ2] = true;
                    score++;
                }
            }
            if (killedPos3 != null) {
                int aI3 = killedPos3[0];
                int aJ3 = killedPos3[1];
                if (!killed[aI3][aJ3]) {
                    killed[aI3][aJ3] = true;
                    score++;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            System.out.println(Arrays.toString(killed[i]));
        }
        System.out.println("a1 = " + a1);
        System.out.println("a2 = " + a2);
        System.out.println("a3 = " + a3);
        System.out.println("score = " + score);
        System.out.println();
        return score;
    }

    // 적을 죽이는 데 성공하면 적의 좌표를, 실패하면 null 반환
    static int[] bfs(int a, int lowerBound, boolean[][] killed) {
        Deque<int[]> queue = new ArrayDeque<>();
        boolean[][] visited = new boolean[n][m];
        int nI = lowerBound - 1;

        // d가 1인 것과 관계없이 바로 위에 죽이지 않은 1이 있으면 바로 좌표 반환
        if (map[nI][a] == 1 && !killed[nI][a]) {
            return new int[]{nI, a};
        }

        // d가 1이고 바로 위가 0이면 바로 null 반환
        if (d == 1 && map[nI][a] == 0) {
            return null;
        }

        queue.addLast(new int[]{nI, a, 1});
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
                if (map[newI][newJ] == 1 && !killed[newI][newJ]) {
                    return new int[]{newI, newJ};
                } else if (!visited[newI][newJ] && distance < d) {
                    visited[newI][newJ] = true;
                    queue.addLast(new int[]{newI, newJ, distance + 1});
                }
            }
        }
        return null;
    }
}
