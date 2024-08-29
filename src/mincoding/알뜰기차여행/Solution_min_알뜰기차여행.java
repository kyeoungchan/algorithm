package mincoding.알뜰기차여행;

import java.io.*;
import java.util.*;

public class Solution_min_알뜰기차여행 {

    static class Node implements Comparable<Node> {
        int number, cost;

        public Node(int number, int cost) {
            this.number = number;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(cost, o.cost);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken());
        int[] dist = new int[N];
        List<Node>[] graph = new List[N];
        for (int i = 0; i < N; i++) {
            graph[i] = new ArrayList<>();
            dist[i] = Integer.MAX_VALUE;
        }

        int T = Integer.parseInt(st.nextToken());
        for (int i = 0; i < T; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            graph[a].add(new Node(b, w));
        }

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(0, 0));
        dist[0] = 0;

        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            if (dist[cur.number] < cur.cost) continue;
            for (Node next: graph[cur.number]) {
                if (dist[next.number] > cur.cost + next.cost) {
                    dist[next.number] = cur.cost + next.cost;
                    pq.offer(new Node(next.number, dist[next.number]));
                }
            }
        }
        if (dist[N - 1] != Integer.MAX_VALUE) System.out.println(dist[N - 1]);
        else System.out.println("impossible");
        br.close();
    }
}
