package mincoding.슬라임증식;

import java.io.*;
import java.util.*;

public class Solution_min_슬라임증식 {

    static class Pos implements Comparable<Pos> {
        int r, c, manhatonDist;

        public Pos(int r, int c) {
            this.r = r;
            this.c = c;
        }

        public Pos(int r, int c, int startR, int startC) {
            this.r = r;
            this.c = c;
            manhatonDist = Math.abs(startR - r) + Math.abs(startC - c);
        }

        @Override
        public int compareTo(Pos o) {
            return manhatonDist == o.manhatonDist ? r == o.r ? Integer.compare(c, o.c) : Integer.compare(r, o.r) : Integer.compare(manhatonDist, o.manhatonDist);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int[][] grid = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < M; j++) grid[i][j] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine(), " ");
        int startR = Integer.parseInt(st.nextToken());
        int startC = Integer.parseInt(st.nextToken());
        int energy = Integer.parseInt(st.nextToken());

        int[] dr = {-1, 0, 1, 0}, dc = {0, 1, 0, -1};
        boolean[][] visited = new boolean[N][M];
        PriorityQueue<Pos> pq = new PriorityQueue<>();
        pq.offer(new Pos(startR, startC));
        visited[startR][startC] = true;

        int r = -1, c = -1;
        while (!pq.isEmpty()) {
            Pos cur = pq.poll();
            energy--;
            if (energy == 0) {
                r = cur.r;
                c = cur.c;
                break;
            }

            for (int d = 0; d < 4; d++) {
                int nr = cur.r + dr[d];
                int nc = cur.c + dc[d];
                if (nr < 0 || nr > N - 1 || nc < 0 || nc > M - 1 || grid[nr][nc] == 1 || visited[nr][nc]) continue;
                visited[nr][nc] = true;
                pq.offer(new Pos(nr, nc, startR, startC));
            }
        }

        if (r != -1) System.out.println(r + " " + c);
        else System.out.println(-1);
        br.close();
    }
}
