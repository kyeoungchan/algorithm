package baekjoon.녹색옷입은애가젤다지;

import java.io.*;
import java.util.*;

public class Solution_bj_4485_녹색옷입은애가젤다지 {
    static class Node implements Comparable<Node>{
        int r, c, cost;

        public Node(int r, int c, int cost) {
            this.r = r;
            this.c = c;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.cost, o.cost);
        }
    }

    static int[] dr = {-1, 0, 1, 0}, dc = {0, 1, 0, -1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int tc = 0;
        while(true) {
            tc++;
            int N = Integer.parseInt(br.readLine());
            if (N == 0) break;
            int[][] map = new int[N][N];
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            int[][] dist = new int[N][N];
            for (int i = 0; i < N; i++) {
                Arrays.fill(dist[i], Integer.MAX_VALUE);
            }

            PriorityQueue<Node> pq = new PriorityQueue<>();
            dist[0][0] = map[0][0];
            pq.offer(new Node(0, 0, dist[0][0]));
            while (!pq.isEmpty()) {
                Node cur = pq.poll();
                int cr = cur.r;
                int cc = cur.c;
                int cost = cur.cost;
                if (dist[cr][cc] < cost) continue;
                if (cr == N - 1 && cc == N - 1) {
                    break;
                }

                for (int d = 0; d < 4; d++) {
                    int nr = cr + dr[d];
                    int nc = cc + dc[d];
                    if (nr < 0 || nr > N - 1 || nc < 0 || nc > N - 1) continue;
                    if (dist[nr][nc] > cost + map[nr][nc]) {
                        dist[nr][nc] = cost + map[nr][nc];
                        pq.offer(new Node(nr, nc, dist[nr][nc]));
                    }
                }
            }

            sb.append("Problem ").append(tc).append(": ").append(dist[N - 1][N - 1]).append("\n");
        }

        System.out.println(sb.toString());
        br.close();
    }
}
