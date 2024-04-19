package baekjoon.민준이와마산그리고건우;

import java.util.*;
import java.io.*;

public class Solution_bj_18223_민준이와마산그리고건우 {

    static class Node implements Comparable<Node> {
        int v;
        int dist;

        public Node(int v, int dist) {
            this.v = v;
            this.dist = dist;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(dist, o.dist);
        }

    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int V = Integer.parseInt(st.nextToken()); // 정점의 수
        int E = Integer.parseInt(st.nextToken()); // 간선의 수
        int P = Integer.parseInt(st.nextToken()); // 건우가 위치한 정점
        int[] dist = new int[V + 1];
        List<int[]>[] edges = new List[V + 1];

        for (int i = 1; i < V + 1; i++ ) {
            dist[i] = Integer.MAX_VALUE;
            edges[i] = new ArrayList<>();
        }
        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            edges[a].add(new int[] {b, c});
            edges[b].add(new int[] {a, c});
        }

        PriorityQueue<Node> pq = new PriorityQueue<>();
        dist[1] = 0;
        pq.offer(new Node(1, 0));

        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            if (dist[cur.v] < cur.dist) continue;
            if (cur.v == V) break;

            for (int[] edge: edges[cur.v]) {
                int nextV = edge[0];
                int cost = edge[1];
                if (dist[nextV] > cur.dist + cost) {
                    dist[nextV] = cur.dist + cost;
                    pq.offer(new Node(nextV, dist[nextV]));
                }
            }
        }
        int pDist = dist[P];
        int gDist = dist[V];
        Arrays.fill(dist, Integer.MAX_VALUE);

        dist[P] = 0;
        pq.offer(new Node(P, 0));
        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            if (dist[cur.v] < cur.dist) continue;
            if (cur.v == V) break;

            for (int[] edge: edges[cur.v]) {
                int nextV = edge[0];
                int cost = edge[1];
                if (dist[nextV] > cur.dist + cost) {
                    dist[nextV] = cur.dist + cost;
                    pq.offer(new Node(nextV, dist[nextV]));
                }
            }
        }

        if (pDist + dist[V] == gDist) System.out.println("SAVE HIM");
        else System.out.println("GOOD BYE");
        br.close();
    }

}