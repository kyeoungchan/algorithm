package baekjoon.castledefense;

import java.util.*;
import java.io.*;

public class Baekjoon17135_CastleDefense {

    static int N;
    static int M;
    static int D;
    static int[][] map;
    static int[] dI = { 0, -1, 0 };
    static int[] dJ = { -1, 0, 1 };
    static int result;
    static int[] picked = new int[3];

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());
        map = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        pick(0, 0);

        System.out.println(result);
        br.close();
    }

    static void pick(int cnt, int start) {
        if (cnt == 3) {
            result = Math.max(result, calculateScore(picked));
            return;
        }
        for (int i = start; i < M; i++) {
            picked[cnt] = i;
            pick(cnt + 1, i + 1);
        }
    }

    static int calculateScore(int[] picked) {
        int score = 0;
        boolean[][] killed = new boolean[N][M];
        for (int round = 1; round <= N; round++) {
            int lowerBound = N + 1 - round;
            int[] killedPos1 = bfs(picked[0], lowerBound, killed);
            int[] killedPos2 = bfs(picked[1], lowerBound, killed);
            int[] killedPos3 = bfs(picked[2], lowerBound, killed);
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
        return score;
    }

    // 적을 죽이는 데 성공하면 적의 좌표를, 실패하면 null 반환
    static int[] bfs(int a, int lowerBound, boolean[][] killed) {
        Deque<int[]> queue = new ArrayDeque<>();
        boolean[][] visited = new boolean[N][M];
        int nI = lowerBound - 1;

        // d가 1인 것과 관계없이 바로 위에 죽이지 않은 1이 있으면 바로 좌표 반환
        if (map[nI][a] == 1 && !killed[nI][a]) {
            return new int[] { nI, a };
        }

        // d가 1이고 바로 위가 0이면 바로 null 반환
        if (D == 1 && map[nI][a] == 0) {
            return null;
        }

        queue.addLast(new int[] { nI, a, 1 });
        while (!queue.isEmpty()) {
            int[] polled = queue.poll();
            int polledI = polled[0];
            int polledJ = polled[1];
            int distance = polled[2];

            for (int i = 0; i < 3; i++) {
                int newI = polledI + dI[i];
                int newJ = polledJ + dJ[i];
                if (newI < 0 || newI >= N || newJ < 0 || newJ >= M) {
                    continue;
                }
                if (map[newI][newJ] == 1 && !killed[newI][newJ]) {
                    return new int[] { newI, newJ };
                } else if (!visited[newI][newJ] && distance + 1 < D) {
                    // 넣기 전에 체크를 하는 구조고, 이미 넣은 원소는 꺼내는 역할만 하기 때문에, distance + 1 < D여야 한다.
                    // 만약 distance < D인 상태라면, distance = 1 => 넣을 때는 distance + 1 = 2인 상태로 넣어지고,
                    // 그러면 newI와 newJ를 탐색하는 과정에서 distance가 3까지 올라가지만, 큐에 넣기도 전에 1에 해당하면 바로 리턴해버린다.
                    // 큐에 넣기 전에 한 번 더 탐색해도 괜찮은 지를 판단시키는 게 맞다.
                    // 만약 큐에서 꺼낸 값을 newI, newJ로 확장시키기 전에 kill 여부를 판단하는 로직 구조라면 distance < D 로직이 타당할 수 있다.
                    visited[newI][newJ] = true;
                    queue.addLast(new int[] { newI, newJ, distance + 1 });
                }
            }
        }
        return null;
    }
}
