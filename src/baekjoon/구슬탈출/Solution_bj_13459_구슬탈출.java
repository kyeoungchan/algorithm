package baekjoon.구슬탈출;

import java.io.*;
import java.util.*;

public class Solution_bj_13459_구슬탈출 {

    static class State {
        int redR, redC, blueR, blueC, moveCnt;

        public State(int redR, int redC, int blueR, int blueC, int moveCnt) {
            this.redR = redR;
            this.redC = redC;
            this.blueR = blueR;
            this.blueC = blueC;
            this.moveCnt = moveCnt;
        }

        public State(int[] redPos, int[] bluePos, int moveCnt) {
            redR = redPos[0];
            redC = redPos[1];
            blueR = bluePos[0];
            blueC = bluePos[1];
            this.moveCnt = moveCnt;
        }
    }

    static char[][] board;
    static int holeR, holeC;
    static int[] dr = new int[] {-1, 0, 1, 0}, dc = new int[]{0, 1, 0, -1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        board = new char[N][M];
        boolean[][][][] visited = new boolean[N][M][N][M];

        int redR = 0, redC = 0, blueR = 0, blueC = 0;
        for (int i = 0; i < N; i++) {
            String s = br.readLine();
            for (int j = 0; j < M; j++) {
                char c = s.charAt(j);
                if (c == 'R') {
                    redR = i;
                    redC = j;
                } else if (c == 'B') {
                    blueR = i;
                    blueC = j;
                } else if (c == 'O') {
                    holeR = i;
                    holeC = j;
                }
                board[i][j] = c;
            }
        }

        ArrayDeque<State> q = new ArrayDeque<>();
        q.offer(new State(redR, redC, blueR, blueC, 0));
        visited[redR][redC][blueR][blueC] = true;

        int answer = 0;
        end: while (!q.isEmpty()) {
            State cur = q.poll();

            int newMoveCnt = cur.moveCnt + 1;
            if (newMoveCnt > 10) break;
            int[] newRedPos;
            int[] newBluePos;
            for (int d = 0; d < 4; d++) {
                if (isRedFirst(cur, d)) {
                    newRedPos = getPosAfterGoStraight(cur.redR, cur.redC, cur.blueR, cur.blueC, d);
                    newBluePos = getPosAfterGoStraight(cur.blueR, cur.blueC, newRedPos[0], newRedPos[1], d);
                    if (inHole(newBluePos)) continue;
                } else {
                    newBluePos = getPosAfterGoStraight(cur.blueR, cur.blueC, cur.redR, cur.redC, d);
                    if (inHole(newBluePos)) continue;
                    newRedPos = getPosAfterGoStraight(cur.redR, cur.redC, newBluePos[0], newBluePos[1], d);
                }
                if (inHole(newRedPos)) {
                    answer = 1;
                    break end;
                }
                if (visited[newRedPos[0]][newRedPos[1]][newBluePos[0]][newBluePos[1]]) continue;
                visited[newRedPos[0]][newRedPos[1]][newBluePos[0]][newBluePos[1]] = true;
                q.offer(new State(newRedPos, newBluePos, newMoveCnt));
            }
        }
        System.out.println(answer);
        br.close();
    }

    private static boolean isRedFirst(State cur, int d) {
        return (d == 0 && cur.redR <= cur.blueR) || (d == 1 && cur.redC >= cur.blueC) || (d == 2 && cur.redR >= cur.blueR) || (d == 3 && cur.redC <= cur.blueC);
    }

    private static int[] getPosAfterGoStraight(int r, int c, int opponentR, int opponentC, int d) {
        while (true) {
            int tempR = r + dr[d];
            int tempC = c + dc[d];
            if (board[tempR][tempC] == '#' || (tempR == opponentR && tempC == opponentC)) break;
            if (tempR == holeR && tempC == holeC) return new int[]{0, 0};
            r = tempR;
            c = tempC;
        }
        return new int[]{r, c};
    }

    private static boolean inHole(int[] pos) {
        return pos[0] == 0 && pos[1] == 0;
    }
}
