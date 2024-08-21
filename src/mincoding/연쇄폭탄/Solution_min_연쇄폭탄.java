package mincoding.연쇄폭탄;

import java.io.*;
import java.util.*;

public class Solution_min_연쇄폭탄 {

    static class Bomb implements Comparable<Bomb> {
        int order;
        int r, c;

        public Bomb(int order, int r, int c) {
            this.order = order;
            this.r = r;
            this.c = c;
        }

        @Override
        public int compareTo(Bomb o) {
            return Integer.compare(order, o.order);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int N = Integer.parseInt(br.readLine());
        int[][] grid = new int[N][N];
        PriorityQueue<Bomb> pq = new PriorityQueue<>();

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < N; j++) {
                grid[i][j] = Integer.parseInt(st.nextToken());
                pq.offer(new Bomb(grid[i][j], i, j));
            }
        }

        int[] dr = {-1, 0, 1, 0}, dc = {0, 1, 0, -1};
        int total = N * N;
        int time = 0;
        while (total != 0) {
            Bomb cur = pq.poll();
            if (grid[cur.r][cur.c] == 0) continue;
            grid[cur.r][cur.c] = 0;
            time = cur.order;
            total--;
            for (int d = 0; d < 4; d++) {
                int nr = cur.r + dr[d];
                int nc = cur.c + dc[d];
                if (nr < 0 || nr > N - 1 || nc < 0 || nc > N - 1 || grid[nr][nc] == 0) continue;
                grid[nr][nc] = 0;
                total--;
            }
        }
        System.out.println(time + "초");
        br.close();
    }
}
