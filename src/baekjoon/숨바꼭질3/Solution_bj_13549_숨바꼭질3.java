package baekjoon.숨바꼭질3;

import java.io.*;
import java.util.*;

public class Solution_bj_13549_숨바꼭질3 {

    static class Node implements Comparable<Node> {
        int pos, time, index;

        public Node(int pos, int time, int index) {
            this.pos = pos;
            this.time = time;
            this.index = index;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(time, o.time);
        }
    }

    static int N, K, size;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        size = 4 * Math.abs(K - N) + 1;
        int[] dist = new int[size];

        PriorityQueue<Node> pq = new PriorityQueue<>();
        Arrays.fill(dist, Integer.MAX_VALUE);
        int firstIdx = generateIndex(N);
        pq.offer(new Node(N, 0, firstIdx));
        dist[firstIdx] = 0;

        while (!pq.isEmpty()) {
            Node node = pq.poll();
            if (dist[node.index] < node.time) continue;
            if (node.pos == K) break;

            offerPq(dist, node.pos + 1, node.time + 1, pq);
            offerPq(dist, node.pos - 1, node.time + 1, pq);
            offerPq(dist, node.pos * 2, node.time, pq);
        }
        System.out.println(dist[generateIndex(K)]);
        br.close();
    }

    static int generateIndex(int pos) {
        // N=5, K=17 => 시작 인덱스는 24
        // -3의 인덱스는 16
        // N= -21, K=-17 =>
        return 2 * Math.abs(N - K) + pos - Math.min(N, K);
    }

    static void offerPq(int[] dist, int newPos, int newTime, PriorityQueue<Node> pq) {
        int index = generateIndex(newPos);
        if (inRange(index) && dist[index] > newTime) {
            dist[index] = newTime;
            pq.offer(new Node(newPos, newTime, index));
        }
    }

    static boolean inRange(int index) {
        return index >= 0 && index < size;
    }
}
