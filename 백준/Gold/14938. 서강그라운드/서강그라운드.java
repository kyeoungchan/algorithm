import java.io.*;
import java.util.*;

public class Main {

    static class Info implements Comparable<Info> {

        int num;
        int dist;

        Info(int num, int dist) {
            this.num = num;
            this.dist = dist;
        }

        @Override
        public int compareTo(Info o) {
            return Integer.compare(dist, o.dist);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());

        List<Info>[] graph = new List[n + 1];
        for (int i = 1; i <= n; i++) graph[i] = new ArrayList<>();

        int[] items = new int[n + 1];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            items[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < r; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int l  = Integer.parseInt(st.nextToken());
            graph[a].add(new Info(b, l));
            graph[b].add(new Info(a, l));
        }

        PriorityQueue<Info> pq = new PriorityQueue<>();
        int answer = 0;

        for (int i = 1; i <= n; i++) {
            int candidate = 0;
            int[] dist = new int[n + 1];
            boolean[] inserted = new boolean[n + 1];
            Arrays.fill(dist, Integer.MAX_VALUE);
            dist[i] = 0;
            pq.offer(new Info(i, 0));
            while (!pq.isEmpty()) {
                Info cur = pq.poll();
                if (cur.dist > m || inserted[cur.num]) continue;

                candidate += items[cur.num];
                inserted[cur.num] = true;

                for (Info next: graph[cur.num]) {
                    if (dist[next.num] <= next.dist + cur.dist) continue;
                    dist[next.num] = next.dist + cur.dist;
                    pq.offer(new Info(next.num, dist[next.num]));
                }
            }
            answer = Math.max(answer, candidate);
        }

        System.out.println(answer);
        br.close();
    }
}