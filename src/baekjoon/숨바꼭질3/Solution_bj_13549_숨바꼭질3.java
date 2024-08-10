package baekjoon.숨바꼭질3;

import java.io.*;
import java.util.*;

public class Solution_bj_13549_숨바꼭질3 {

    static class Node implements Comparable<Node> {
        int pos, time;

        public Node(int pos, int time) {
            this.pos = pos;
            this.time = time;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(time, o.time);
        }

        @Override
        public String toString() {
            return "Node{" +
                    "pos=" + pos +
                    ", time=" + time +
                    '}';
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        PriorityQueue<Node> pq = new PriorityQueue<>();
        int[] dist = new int[400_001];
        Arrays.fill(dist, Integer.MAX_VALUE);
        pq.offer(new Node(N, 0));
        dist[N] = 0;

        while (!pq.isEmpty()) {
            Node node = pq.poll();
            System.out.println("node = " + node);
            System.out.println("dist[node.pos] = " + dist[node.pos]);
            if (dist[node.pos] < node.time) continue;
            if (node.pos == K) break;

            int newPos = node.pos + 1;
            int newTime = node.time + 1;
            if (dist[newPos] > newTime ) {
                dist[newPos] = newTime;
                pq.offer(new Node(newPos, node.time + 1));
            }
            newPos = node.pos - 1;
            if (dist[newPos] > newTime ) {
                dist[newPos] = newTime;
                pq.offer(new Node(newPos, node.time + 1));
            }
            newPos = node.pos * 2;
            newTime--;
            if (dist[newPos] > newTime ) {
                dist[newPos] = newTime;
                pq.offer(new Node(newPos, node.time + 1));
            }
        }
        System.out.println(dist[K]);
        br.close();
    }
}
