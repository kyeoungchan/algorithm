package mincoding.플러드필;

import java.io.*;
import java.util.*;

public class Solution_min_플러드필 {

    public static void main(String[] args) throws Exception {

        int[] dr = new int[]{-1, 0, 1, 0}, dc = new int[]{0, 1, 0, -1};

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        int[][] grid = new int[5][5];
        ArrayDeque<int[]> q = new ArrayDeque<>();
        boolean[][] visited = new boolean[5][5];
        q.offer(new int[]{r, c, 1});
        visited[r][c] = true;
        grid[r][c] = 1;
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            for (int d = 0; d < 4; d++) {
                int nr = cur[0] + dr[d];
                int nc = cur[1] + dc[d];
                if (nr < 0 || nr > 4 || nc < 0 || nc > 4 || visited[nr][nc]) continue;
                int nValue = cur[2] + 1;
                visited[nr][nc] = true;
                grid[nr][nc] = nValue;
                q.offer(new int[]{nr, nc, nValue});
            }
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
        br.close();
    }
}
