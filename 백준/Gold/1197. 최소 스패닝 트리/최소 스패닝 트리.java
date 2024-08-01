import java.io.*;
import java.util.*;

public class Main {
    static class Node implements Comparable<Node> {
        int vertex, cost;

        public Node(int vertex, int cost) {
            this.vertex = vertex;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(cost, o.cost);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int V = Integer.parseInt(st.nextToken());
        int E = Integer.parseInt(st.nextToken());

        List<Node>[] edges = new List[V + 1];

        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            if (edges[a] == null) edges[a] = new ArrayList<>();
            edges[a].add(new Node(b, cost));
            if (edges[b] == null) edges[b] = new ArrayList<>();
            edges[b].add(new Node(a, cost));
        }

        int[] minEdges = new int[V + 1];
        Arrays.fill(minEdges, Integer.MAX_VALUE);

        boolean[] visited = new boolean[V + 1];
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(1, 0));
        minEdges[1] = 0;
        int result = 0;

        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            if (visited[cur.vertex]) continue;
            visited[cur.vertex] = true;
            result += cur.cost;

            for (Node edge : edges[cur.vertex]) {
                if (visited[edge.vertex]) continue;
                pq.offer(edge);
            }
        }
        System.out.println(result);
        br.close();
    }
}