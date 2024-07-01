import java.io.*;
import java.util.*;

public class Main {

    static class City implements Comparable<City> {
        int number, cost;

        public City(int number, int cost) {
            this.number = number;
            this.cost = cost;
        }

        @Override
        public int compareTo(City o) {
            return Integer.compare(cost, o.cost);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        List<City>[] graph = new List[N + 1];
        int[] minEdges = new int[N + 1];
        boolean[] connected = new boolean[N + 1];
        for (int i = 1; i < N + 1; i++) {
            graph[i] = new ArrayList<>();
            minEdges[i] = Integer.MAX_VALUE;
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            graph[a].add(new City(b, c));
            graph[b].add(new City(a, c));
        }

        PriorityQueue<City> pq = new PriorityQueue<>();
        // 1번 집부터 연결 시작
        pq.offer(new City(1, 0));
        int total = 0, maxPath = -1; // prim을 통해 집들을 모두 연결시킨 후 그 중 가장 유지비가 높은 길만 제거한다.
        while (!pq.isEmpty()) {
            City city = pq.poll();

            if (connected[city.number]) continue;
            connected[city.number] = true;
            minEdges[city.number] = city.cost;
            total += city.cost;
            maxPath = Math.max(maxPath, city.cost);

            for (City next : graph[city.number]) {
                if (connected[next.number]) continue;
                if (next.cost < minEdges[next.number]) {
                    pq.offer(next);
                }
            }
        }

        System.out.println(total - maxPath);
        br.close();
    }
}