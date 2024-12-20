package baekjoon.벽부수고이동하기3;

import java.io.*;
import java.util.*;

public class Solution_bj_16933_벽부수고이동하기3 {
    static class Status {
        int r, c, dist, brokeCnt;
        boolean day;

        public Status(int r, int c, int dist, int brokeCnt, boolean day) {
            this.r = r;
            this.c = c;
            this.dist = dist;
            this.brokeCnt = brokeCnt;
            this.day = day;
        }

        @Override
        public String toString() {
            return "Status{" +
                    "r=" + r +
                    ", c=" + c +
                    ", dist=" + dist +
                    ", brokeCnt=" + brokeCnt +
                    ", day=" + day +
                    '}';
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        int[][] map = new int[N + 1][M + 1];
        int[][] brokenWalls = new int[N + 1][M + 1];

        for (int i = 1; i < N + 1; i++) {
            String s = br.readLine();
            for (int j = 1; j < M + 1; j++) {
                map[i][j] = s.charAt(j - 1) - '0';
                brokenWalls[i][j] = Integer.MAX_VALUE;
            }
        }
        ArrayDeque<Status> q = new ArrayDeque<>();

        brokenWalls[1][1] = 0;
        q.offer(new Status(1, 1, 1, 0, true));
        int answer = -1;

        int[] dr = new int[]{-1, 0, 1, 0}, dc = new int[]{0, 1, 0, -1};

        while (!q.isEmpty()) {
            Status cur = q.poll();
//            System.out.println("cur = " + cur);
            if (cur.r == N && cur.c == M) {
                answer = cur.dist;
                break;
            }

            int newDist = cur.dist + 1;
/*
            if (!cur.day) {
                // 밤이라면 낮까지 기다려야한다. 거리 1증가
                q.offer(new Status(cur.r, cur.c, newDist, cur.brokeCnt, true));
                continue;
            }
*/

            for (int d = 0; d < 4; d++) {
                int nr = cur.r + dr[d];
                int nc = cur.c + dc[d];

                if (nr < 1 || nr > N || nc < 1 || nc > M) continue;
                int newBrokeCnt = cur.brokeCnt;
                if (map[nr][nc] == 1) {
                    // 벽을 부숴야하는 상황
                    if (cur.brokeCnt + 1 > K) continue; // 더이상 부술 수 없다.
                    if (!cur.day) {
                        // 만약 밤인 상황이라면
                        q.offer(new Status(cur.r, cur.c, newDist, newBrokeCnt, true));
                        continue;
                    }
                    newBrokeCnt++;
                }
                if (brokenWalls[nr][nc] <= newBrokeCnt) continue;
                brokenWalls[nr][nc] = newBrokeCnt;
                q.offer(new Status(nr, nc, newDist, newBrokeCnt, !cur.day));
            }
        }
        System.out.println(answer);
        br.close();
    }
}
