package baekjoon.escape;

import java.util.*;
import java.io.*;

public class Solution_bj_3055 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int R = Integer.parseInt(st.nextToken());
        int C = Integer.parseInt(st.nextToken());
        char[][] map = new char[R][C];
        int[] sPos = new int[3];
        ArrayDeque<int[]> q = new ArrayDeque<>();
        for (int i = 0; i < R; i++) {
            String s = br.readLine();
            for (int j = 0; j < C; j++) {
                map[i][j] = s.charAt(j);
                if (map[i][j] == 'S') {
                    sPos[0] = i;
                    sPos[1] = j;
                    sPos[2] = 1;
                } else if (map[i][j] == '*') {
                    q.offer(new int[]{i, j, 0});
                }
            }
        }

        int[] di = {-1, 0, 1, 0}, dj = {0, 1, 0, -1};
        q.offer(sPos); // 물 다음으로 마지막에 고슴도치의 현재 위치를 놔둔다.
        int time = 0;
        boolean[][] v = new boolean[R][C];
        boolean hasReached = false;
        end: while (!q.isEmpty()) {
            time++;
            int qSize = q.size();
            for (int i = 0; i < qSize; i++) {
                int[] cur = q.poll();
                int status = cur[2]; // 고슴도치면 1, 물이면 0이다.
                for (int d = 0; d < 4; d++) {
                    int ni = cur[0] + di[d];
                    int nj = cur[1] + dj[d];
                    if (ni < 0 || ni > R - 1 || nj < 0 || nj > C - 1 || map[ni][nj] == 'X' || map[ni][nj] == '*') continue;
                    if (status == 0) {
                        // 물인 경우
                        if (map[ni][nj] == 'D') continue;
                        map[ni][nj] = '*';
                        q.offer(new int[]{ni, nj, 0});
                    } else {
                        // 고슴도치인 경우
                        if (v[ni][nj]) continue;
                        v[ni][nj] = true;
                        if (map[ni][nj] == 'D') {
                            hasReached = true;
                            break end;
                        }
                        q.offer(new int[]{ni, nj, 1});
                    }
                }
            }
        }
        if (hasReached) {
            System.out.println(time);
        } else {
            System.out.println("KAKTUS");
        }

        br.close();
    }
}
