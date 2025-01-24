package baekjoon.백조의호수;

import java.io.*;
import java.util.*;

public class Solution_bj_3197_백조의호수_2 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int R = Integer.parseInt(st.nextToken());
        int C = Integer.parseInt(st.nextToken());
        char[][] map = new char[R][C];
        int[][] swans = new int[2][2];
        ArrayDeque<int[]> waterQ = new ArrayDeque<>();

        int swansIndex = 0;
        for (int i = 0; i < R; i++) {
            String s = br.readLine();
            for (int j = 0; j < C; j++) {
                char ch = s.charAt(j);
                if (ch == 'L') {
                    swans[swansIndex][0] = i;
                    swans[swansIndex][1] = j;
                    swansIndex++;
                }
                if (ch != 'X') {
                    // 물은 일단 다 담는다.
                    waterQ.offer(new int[]{i, j});
                }
                map[i][j] = ch;
            }
        }

        int[] dr = new int[]{-1, 0, 1, 0}, dc = new int[]{0, 1, 0, -1};

        boolean[][] visited = new boolean[R][C];
        ArrayDeque<int[]> q = new ArrayDeque<>();
        q.offer(new int[]{swans[0][0], swans[0][1]});
        visited[swans[0][0]][swans[0][1]] = true;

        int day = 0;
        while (true) {
            ArrayDeque<int[]> nextDayQ = new ArrayDeque<>();
            boolean met = false;
            while (!q.isEmpty()) {
                int[] cur = q.poll();
                if (cur[0] == swans[1][0] && cur[1] == swans[1][1]) {
                    met = true;
                    break;
                }

                for (int d = 0; d < 4; d++) {
                    int nr = cur[0] + dr[d];
                    int nc = cur[1] + dc[d];
                    if (nr < 0 || nr > R - 1 || nc < 0 || nc > C - 1 || visited[nr][nc]) continue;
                    visited[nr][nc] = true;

                    if (map[nr][nc] == 'X') {
                        // 빙판이면 내일 탐색할 큐에 저장한다.
                        nextDayQ.offer(new int[]{nr, nc});
                        continue;
                    }

                    q.offer(new int[]{nr, nc});
                }
            }
            if (met) break;

            int waterSize = waterQ.size();
            for (int i = 0; i < waterSize; i++) {
                int[] cur = waterQ.poll();
                for (int d = 0; d < 4; d++) {
                    int nr = cur[0] + dr[d];
                    int nc = cur[1] + dc[d];
                    if (nr < 0 || nr > R - 1 || nc < 0 || nc > C - 1) continue;
                    if (map[nr][nc] == 'X') {
                        // 빙판을 발견하면 빙판을 물로 바꿔주고 waterQ에 담는다.
                        map[nr][nc] = '.';
                        waterQ.offer(new int[]{nr, nc});
                    }
                }
            }

            // 하루가 지나간다..
            q = nextDayQ;
            day++;
        }
        System.out.println(day);
        br.close();
    }
}
