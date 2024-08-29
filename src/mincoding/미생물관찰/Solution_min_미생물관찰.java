package mincoding.미생물관찰;

import java.io.*;
import java.util.*;

public class Solution_min_미생물관찰 {

    static int[] dr = {-1 ,0, 1, 0}, dc = {0, 1, 0, -1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        char[][] grid = new char[N][M];
        for (int i = 0; i < N; i++) {
            String s = br.readLine();
            for (int j = 0; j < M; j++) grid[i][j] = s.charAt(j);
        }

        boolean[][] visited = new boolean[N][M];
        int aCnt = 0, bCnt = 0, maxSize = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (grid[i][j] == '_' || visited[i][j]) continue;
                int size = getSize(grid, visited, i, j, N, M);
                if (grid[i][j] == 'A') aCnt++;
                else bCnt++;
                maxSize = Math.max(maxSize, size);
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(aCnt).append(" ").append(bCnt).append("\n").append(maxSize);
        System.out.println(sb.toString());
        br.close();
    }

    static int getSize(char[][] grid, boolean[][] visited, int r, int c, int N, int M) {
        ArrayDeque<int[]> q = new ArrayDeque<>();
        char kind = grid[r][c];
        int cnt = 0;
        q.offer(new int[]{r, c});
        visited[r][c] = true;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            cnt++;
            for (int d = 0; d < 4; d++) {
                int nr = cur[0] + dr[d];
                int nc = cur[1] + dc[d];
                if (nr < 0 || nr > N - 1 || nc < 0 || nc > M - 1 || visited[nr][nc] || grid[nr][nc] != kind) continue;
                visited[nr][nc] = true;
                q.offer(new int[]{nr, nc});
            }
        }
        return cnt;
    }
}
