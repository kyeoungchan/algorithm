import java.io.*;
import java.util.*;

public class Main {
    static class Node implements Comparable<Node> {
        int num, cost;

        public Node(int num, int cost) {
            this.num = num;
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
        int N = Integer.parseInt(st.nextToken()); // 정점의 개수
        int E = Integer.parseInt(st.nextToken()); // 간선의 개수
        List<Node>[] graph = new List[N + 1];
        for (int i = 1; i <= N; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            graph[a].add(new Node(b, c));
            graph[b].add(new Node(a, c));
        }

        st = new StringTokenizer(br.readLine(), " ");
        int v1 = Integer.parseInt(st.nextToken());
        int v2 = Integer.parseInt(st.nextToken());
        int answer = -1;
        int oneToV1 = dijkstra(1, v1, graph, N);
        if (oneToV1 != Integer.MAX_VALUE) {
            int v2ToN = dijkstra(v2, N, graph, N);
            if (v2ToN != Integer.MAX_VALUE) {
                int v1ToV2 = dijkstra(v1, v2, graph, N);
                if (v1ToV2 != Integer.MAX_VALUE) {
                    answer = oneToV1 + v2ToN + v1ToV2;
                }
            }
        }

        int oneToV2 = dijkstra(1, v2, graph, N);
        if (oneToV2 != Integer.MAX_VALUE) {
            int v1ToN = dijkstra(v1, N, graph, N);
            if (v1ToN != Integer.MAX_VALUE) {
                int v2ToV1 = dijkstra(v2, v1, graph, N);
                if (v2ToV1 != Integer.MAX_VALUE) {
                    if (answer == -1) answer = oneToV2 + v1ToN + v2ToV1;
                    else answer = Math.min(answer, oneToV2 + v2ToV1 + v1ToN);
                }
            }
        }
        System.out.println(answer);
        br.close();
    }

    static int dijkstra(int start, int end, List<Node>[] graph, int N) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        int[] dist = new int[N + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;
        pq.offer(new Node(start, 0));
        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            if (cur.cost > dist[cur.num]) continue;
            if (cur.num == end) break;
            for (Node next: graph[cur.num]) {
                if (dist[next.num] > cur.cost + next.cost) {
                    dist[next.num] = cur.cost + next.cost;
                    pq.offer(new Node(next.num, dist[next.num]));
                }
            }
        }
        return dist[end];
    }
}