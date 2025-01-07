package baekjoon.백조의호수;

import java.io.*;
import java.util.*;

/**
 * 매일 물공간과 접촉한 빙판 공간은 녹는다. 대각선은 고려하지 않는다.
 * 백조는 오직 물 공간에서 상하좌우로 움직일 수 있다.
 * 며칠이 지나야 백조들이 만날 수 있는지
 */
public class Solution_bj_3197_백조의호수 {

    static class State {
        int id;
        int r, c, meltCnt;

        public State(int id, int r, int c, int meltCnt) {
            this.id = id;
            this.r = r;
            this.c = c;
            this.meltCnt = meltCnt;
        }
    }

    static char[][] map;
    static int R, C, answer;
    static int[] dr = new int[] {-1, 0, 1, 0}, dc = new int[]{0, 1, 0, -1};
    static int[][][] visited;
    static ArrayDeque<State> q;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        map = new char[R][C];
        int startRow = -1, startCol = -1;
        int endRow = -1, endCol = -1;
        visited = new int[R][C][2];
        for (int i = 0; i < R; i++) {
            String s = br.readLine();
            for (int j = 0; j < C; j++) {
                char c = s.charAt(j);
                if (c == 'L') {
                    if (startRow == -1) {
                        startRow = i;
                        startCol = j;
                    } else {
                        endRow = i;
                        endCol = j;
                    }
                    c = '.';
                }
                map[i][j] = c;
                visited[i][j][0] = visited[i][j][1] = Integer.MAX_VALUE;
            }
        }

        q = new ArrayDeque<>();
        visited[startRow][startCol][0] = 0;
        q.offer(new State(0, startRow, startCol, 0));
        visited[endRow][endCol][1] = 0;
        q.offer(new State(1, endRow, endCol, 0));

        end: while (!q.isEmpty()) {
            State cur = q.poll();

            int opponent = ++cur.id % 2;
            for (int d = 0; d < 4; d++) {
                int nr = cur.r + dr[d];
                int nc = cur.c + dc[d];
                if (nr < 0 || nr > R - 1 || nc < 0 || nc > C - 1) continue;
                if (map[nr][nc] == '.') {
                    if (moveOnWater(cur)) break end;
                    continue;
                }
                int nMeltCnt = cur.meltCnt + 1;
                if (visited[nr][nc][cur.id] <= nMeltCnt) continue;
                if (visited[nr][nc][opponent] != Integer.MAX_VALUE) {
                    answer = visited[nr][nc][opponent] + cur.meltCnt;
                    break end;
                }
                visited[nr][nc] = nMeltCnt;
                q.offer(new State(cur.id, nr, nc, nMeltCnt));
            }
        }

/*
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                System.out.print(visited[i][j]);
            }
            System.out.println();
        }
*/
        int answer = visited[endRow][endCol] / 2;
        if (visited[endRow][endCol] % 2 != 0) answer++;
        System.out.println(answer);
        br.close();
    }

    static boolean moveOnWater(State cur) {
        int opponent = ++cur.meltCnt % 2;
        int nMeltCnt = cur.meltCnt + 1;
        ArrayDeque<int[]> intQ = new ArrayDeque<>();
        for (int d = 0; d < 4; d++) {
            int nr = cur.r + dr[d];
            int nc = cur.c + dc[d];
            if (nr < 0 || nr > R - 1 || nc < 0 || nc > C - 1) continue;
            if (map[nr][nc] == '.') {
                intQ.offer(new int[]{nr, nc});
                visited[nr][nc][cur.id] = cur.meltCnt;
            } else {
                if (visited[nr][nc][opponent] != Integer.MAX_VALUE) {
                    answer = cur.meltCnt + visited[nr][nc][opponent];
                    return true;
                }
                q.offer(new State(cur.id, nr, nc, nMeltCnt));
                visited[nr][nc][cur.id] = nMeltCnt;
            }
        }


    }
}
