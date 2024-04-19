package baekjoon.비밀모임;

import java.util.*;
import java.io.*;

public class Solution_bj_13424_비밀모임 {

    static final int INF = 987654321;

    static class Node implements Comparable<Node> {
        int num;
        int dist;

        public Node(int num, int dist) {
            this.num = num;
            this.dist = dist;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(dist, o.dist);
        }

    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            st = new StringTokenizer(br.readLine(), " ");
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            List<int[]>[] edges = new List[N+1];

            for (int i = 1; i < N + 1; i++) {
                edges[i] = new ArrayList<>();
            }

            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                edges[a].add(new int[] {b, c});
                edges[b].add(new int[] {a, c});
            }

            int K = Integer.parseInt(br.readLine());
            st = new StringTokenizer(br.readLine(), " ");
            // 각각 친구들마다의 최소 거리를 저장하기 위한 이차원배열
            int[][] dist = new int[K][N + 1];
            int[] friends = new int[K];
            for (int i = 0; i < K; i++) {
                friends[i] = Integer.parseInt(st.nextToken());
                Arrays.fill(dist[i], INF);
            }

            PriorityQueue<Node> pq = new PriorityQueue<>();
            for (int f = 0; f < K; f++) {
                // 각 친구마다 출발지를 기준으로 다익스트라 시작
                dist[f][friends[f]] = 0;
                pq.offer(new Node(friends[f], 0));
                while (!pq.isEmpty()) {
                    Node cur = pq.poll();
                    int minVertex = cur.num;
                    int min = cur.dist;
                    if (dist[f][minVertex] < min) continue;
                    for (int[] edge: edges[minVertex]) {
                        if (dist[f][edge[0]] > min + edge[1]) {
                            dist[f][edge[0]] = min + edge[1];
                            pq.offer(new Node(edge[0], dist[f][edge[0]]));
                        }
                    }
                }
            }
            int minSum = INF;
            int answer = N + 2;
            // 각 친구마다의 거리들 중 최소의 위치 구하기
            for (int i = 1; i < N + 1; i++) {
                int sum = 0;
                for (int f = 0; f < K; f++) {
                    sum += dist[f][i];
                }
                if (minSum > sum) {
                    minSum = sum;
                    answer = i;
                }
            }
            sb.append(answer).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }

}