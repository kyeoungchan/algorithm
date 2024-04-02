import java.util.*;
import java.io.*;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int V = Integer.parseInt(st.nextToken());
        int E = Integer.parseInt(st.nextToken());
        List<int[]>[] edges = new List[V + 1];
        int[] dist = new int[V + 1];
        for (int i = 1; i < V + 1; i++) {
            edges[i] = new ArrayList<>();
            dist[i] = Integer.MAX_VALUE;
        }
        int start = Integer.parseInt(br.readLine());
        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());
            edges[from].add(new int[] {to, weight});
        }

        boolean[] v = new boolean[V + 1];
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1[1], o2[1]));
        dist[start] = 0;
        pq.offer(new int[] {start, 0});
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int minVertex = cur[0];

            if (v[minVertex]) continue;
            v[minVertex] = true;

            for (int[] next : edges[minVertex]) {
                int nextVertex = next[0];
                int cost = next[1];
                if (v[nextVertex]) continue;
                if (dist[nextVertex] > dist[minVertex] + cost) {
                    dist[nextVertex] = dist[minVertex] + cost;
                    pq.offer(new int[]{nextVertex, dist[nextVertex]});
                }
            }
        }
        for (int i = 1; i < V + 1; i++) {
            if (dist[i] == Integer.MAX_VALUE)
                sb.append("INF");
            else
                sb.append(dist[i]);
            sb.append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }

}