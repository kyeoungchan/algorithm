import java.io.*;
import java.util.*;

/**
 * s지점에서 예술가 한 쌍이 출발
 * 목적지까지 최단거리로 갈 것이라고 확신
 * 출력
 * 목직지 후보들 중에서 불가능한 경우들을 제외한 목적지들을 공백으로 분리, 오름차순으로 출력
 */
public class Main {

    static class CrossRoad implements Comparable<CrossRoad> {
        int num;
        int dist;

        CrossRoad(int num, int dist) {
            this.num = num;
            this.dist = dist;
        }

        @Override
        public int compareTo(CrossRoad o) {
            return Integer.compare(dist, o.dist);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine()); // 테스트케이스 T가 주어진다.
        for (int tc = 0; tc < T; tc++) {
            st = new StringTokenizer(br.readLine(), " ");
            // n(교차로의 개수), m(도로의 개수), t(목적지 후보의 개수)
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            int t = Integer.parseInt(st.nextToken());

            // s(예술가들의 출발지), 지나간 교차로(g, h)
            st = new StringTokenizer(br.readLine(), " ");
            int s = Integer.parseInt(st.nextToken());
            int g = Integer.parseInt(st.nextToken());
            int h = Integer.parseInt(st.nextToken());


            List<CrossRoad>[] edges = new List[n + 1];
            for (int i = 1; i < n + 1; i++) {
                edges[i] = new ArrayList<>();
            }

            int distGH = 0;
            // a, b, d -> 간선 정보
            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                int d = Integer.parseInt(st.nextToken());
                edges[a].add(new CrossRoad(b, d));
                edges[b].add(new CrossRoad(a, d));
                if ((a == g && b == h) || (a == h && b == g)) distGH = d;
            }

            // t 개의 x -> 목적지 후보
            List<Integer> goals = new ArrayList<>();
            for (int i = 0; i < t; i++) {
                goals.add(Integer.parseInt(br.readLine()));
            }
            int[] fromS = dijkstra(n, s, edges);
            int[] fromG = dijkstra(n, g, edges);
            int[] fromH = dijkstra(n, h, edges);


            Collections.sort(goals);
            for (int goal : goals) {
                if (hasSmell(goal, fromS, fromG, fromH, distGH, g, h)) sb.append(goal).append(" ");
            }
            sb.append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }

    static boolean hasSmell(int goal, int[] fromS, int[] fromG, int[] fromH, int distGH, int g, int h) {
        // 목적지까지 간 거리가 g를 지나치고 h를 지나치고 목적지까지 간 거리와 같거나 h를 지나치고 g를 지나치고 목적지까지 간 거리와 같으면 true
        return (fromS[goal] == fromS[g] + distGH + fromH[goal]) || (fromS[goal] == fromS[h] + distGH + fromG[goal]);
    }

    static int[] dijkstra(int n, int s, List<CrossRoad>[] edges) {
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);

        PriorityQueue<CrossRoad> pq = new PriorityQueue<>();
        pq.offer(new CrossRoad(s, 0));
        dist[s] = 0;

        while (!pq.isEmpty()) {
            CrossRoad cur = pq.poll();

            if (dist[cur.num] < cur.dist) continue;

            for (CrossRoad next : edges[cur.num]) {
                if (dist[next.num] > next.dist + cur.dist) {
                    dist[next.num] = next.dist + cur.dist;
                    pq.offer(new CrossRoad(next.num, dist[next.num]));
                }
            }
        }
        return dist;
    }
}