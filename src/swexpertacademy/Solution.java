package swexpertacademy;

import java.io.*;
import java.util.*;

public class Solution {
    static int N, cnt;
    static double result;
    static double E;
    static int[][] island;
    static boolean[] visit; // prim
    static int[] parent; // kruskal

    static class Node implements Comparable<Node> {
        int start; // prim으로 풀 때는 Node에 start 필요없음
        int end;
        double dis;

        public Node(int start, int end, double dis) {
            this.start = start;
            this.end = end;
            this.dis = dis;
        }

        @Override
        public int compareTo(Node o) {
            return this.dis < o.dis ? -1 : 1;
        }
    }

    static int find(int x) {
        if (parent[x] == x) return x;
        return parent[x] = find(parent[x]);
    }

    static void union(int x, int y) {
        x = find(x);
        y = find(y);
        if (x < y) parent[y] = x;
        else parent[x] = y;
    }

    static void sumKru() {
        PriorityQueue<Node> que = new PriorityQueue<>();
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                que.offer(new Node(i, j, Math.pow(island[0][i] - island[0][j], 2)
                        + Math.pow(island[1][i] - island[1][j], 2)));
            }
        }

        while (!que.isEmpty()) {
            Node temp = que.poll();
            int x = temp.start;
            int y = temp.end;
            if (find(x) != find(y)) {
                union(x, y);
                result += temp.dis * E;
            }
        }
    }

    static void sumPrim() {
        PriorityQueue<Node> que = new PriorityQueue<>();
        for (int i = 1; i < N; i++)
            que.offer(new Node(0, i, Math.pow(island[0][i] - island[0][0], 2)
                    + Math.pow(island[1][i] - island[1][0], 2)));
        visit[0] = true;

        while (!que.isEmpty()) {
            Node temp = que.poll();
            int end = temp.end;

            if (visit[end]) continue;
            visit[end] = true;
            cnt++;

            double L = temp.dis;
            result += L * E;

            if (cnt == N) break;

            for (int i = 1; i < N; i++) {
                if (i == end || visit[i]) continue;
                que.offer(new Node(end, i, Math.pow(island[0][i] - island[0][end], 2)
                        + Math.pow(island[1][i] - island[1][end], 2)));
            }
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = null;
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            N = Integer.parseInt(br.readLine()); // 섬의 개수
            result = 0;
            cnt = 0;

            // parent = new int[N]; // kru
            // for (int i = 0; i < N; i++) parent[i] = i;

            visit = new boolean[N]; // prim

            island = new int[2][N];
            for (int i = 0; i < 2; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    island[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            E = Double.parseDouble(br.readLine()); // 환경 부담 세율 -> E * L(거리)^2 지불
            sumPrim();
            // sumKru();

            sb.append("#").append(tc).append(" ").append(Math.round(result)).append("\n");
        }

        System.out.println(sb);
        br.close();
    }
}