package mincoding.무서운시어머니;

import java.io.*;
import java.util.*;

public class Solution_min_무서운시어머니 {

    static class Node implements Comparable<Node> {
        int r, c, fatigue;

        public Node(int r, int c, int fatigue) {
            this.r = r;
            this.c = c;
            this.fatigue = fatigue;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(fatigue, o.fatigue);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int Y = Integer.parseInt(st.nextToken());
        int X = Integer.parseInt(st.nextToken());

        int N = Integer.parseInt(br.readLine());
        int[][] map = new int[N][N];
        int[][] dist = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                dist[i][j] = Integer.MAX_VALUE;
            }
        }

        int[] dr = {-1, 0, 1, 0}, dc = {0, 1, 0, -1};
        PriorityQueue<Node> pq = new PriorityQueue<>();
        dist[Y][X] = map[Y][X];
        pq.offer(new Node(Y, X, map[Y][X]));
        int max = 0;
        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            if (dist[cur.r][cur.c] < cur.fatigue) continue;
            max = Math.max(max, cur.fatigue);
            for (int d = 0; d < 4; d++) {
                int nr = cur.r + dr[d];
                int nc = cur.c + dc[d];
                if (nr < 0 || nr > N - 1 || nc < 0 || nc > N - 1 || map[nr][nc] == -1) continue;
                if (dist[nr][nc] > map[nr][nc] + cur.fatigue) {
                    dist[nr][nc] = map[nr][nc] + cur.fatigue;
                    pq.offer(new Node(nr, nc, dist[nr][nc]));
                }
            }
        }
        System.out.println(max);
        br.close();
    }
}
