package mincoding.파이프촬영;

import java.io.*;
import java.util.*;

public class Solution_min_파이프촬영 {

    static int[] dr = {-1, 0, 1, 0}, dc = {0, 1, 0, -1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            st = new StringTokenizer(br.readLine(), " ");
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int R = Integer.parseInt(st.nextToken());
            int C = Integer.parseInt(st.nextToken());
            int L = Integer.parseInt(st.nextToken());
            int[][] grid = new int[N][M];
            boolean[][] visited = new boolean[N][M];
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                for (int j = 0; j < M; j++) grid[i][j] = Integer.parseInt(st.nextToken());
            }
/*
            if (grid[R][C] == 0) {
                sb.append("#").append(tc).append(" ").append(0).append("\n");
                continue;
            }
*/
            ArrayDeque<int[]> q = new ArrayDeque<>();
            q.offer(new int[]{R, C, 1});
            visited[R][C] = true;
            int cnt = 1;
            while (!q.isEmpty()) {
                int[] cur = q.poll();
                if (cur[2] == L) break;
                for (int d = 0; d < 4; d++) {
                    int nr = cur[0] + dr[d];
                    int nc = cur[1] + dc[d];
                    if (nr < 0 || nr > N - 1 || nc < 0 || nc > M - 1 || visited[nr][nc]) continue;
                    if (!canGo(nr, nc, grid, d) || !canGo(cur[0], cur[1], grid, (d + 2) % 4)) continue;
                    visited[nr][nc] = true;
                    cnt++;
                    q.offer(new int[]{nr, nc, cur[2] + 1});
                }
            }
            sb.append("#").append(tc).append(" ").append(cnt).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }

    static boolean canGo(int r, int c, int[][] grid, int d) {
        // d는 r,c 쪽으로 들어오는 방향이다
        if (grid[r][c] == 0) return false;
        if (grid[r][c] == 1) return true;
        if (grid[r][c] / 4 == 0) return (grid[r][c] + d) % 2 == 0;
        int number = grid[r][c] - 4;
        // 0 => 2,3
        // 1 => 0,3
        // 2 => 0,1
        // 3 => 1,2
        return (d == (number + 2) % 4) || (d == (number + 3) % 4);
    }
}
