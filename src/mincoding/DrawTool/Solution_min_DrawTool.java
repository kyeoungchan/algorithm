package mincoding.DrawTool;

import java.io.*;
import java.util.*;

public class Solution_min_DrawTool {

    static class DrawInfo implements Comparable<DrawInfo> {
        int color, r1,c1,r2, c2;

        public DrawInfo(int color, int r1, int c1, int r2, int c2) {
            this.color = color;
            this.r1 = Math.min(r1, r2);
            this.c1 = Math.min(c1, c2);
            this.r2 = Math.max(r1, r2);
            this.c2 = Math.max(c1, c2);
        }

        @Override
        public int compareTo(DrawInfo o) {
            return Integer.compare(o.color, color);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int N = Integer.parseInt(br.readLine());
        int[][] grid = new int[N][N];
        PriorityQueue<DrawInfo> pq = new PriorityQueue<>();

        int Q = Integer.parseInt(br.readLine());
        for (int i = 0; i < Q; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            pq.offer(new DrawInfo(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
        }

        while (!pq.isEmpty()) {
            DrawInfo d = pq.poll();
            for (int r = d.r1; r <= d.r2; r++) {
                for (int c = d.c1; c <= d.c2; c++) {
                    if (grid[r][c] >= d.color) continue;
                    grid[r][c] = d.color;
                }
            }
        }
        int maxSide = 0;
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                if (grid[r][c] == 0) continue;
                int color = grid[r][c];
                int limitSide = Math.min(N - r, N - c);

                notDone: for (int s = limitSide; s > 1; s--) {
                    for (int nr = r; nr < r + s; nr++) {
                        for (int nc = c; nc < c + s; nc++) {
                            if (grid[nr][nc] != color) continue notDone;
                        }
                    }
                    maxSide = Math.max(maxSide, s);
                    break;
                }
            }
        }
        if (maxSide == 0) maxSide = 1;
        System.out.println(maxSide * maxSide);
        br.close();
    }
}
