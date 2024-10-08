import java.io.*;
import java.util.*;

public class Main {

    static class BusInfo implements Comparable<BusInfo>{
        int destNum, cost;

        public BusInfo(int destNum, int cost) {
            this.destNum = destNum;
            this.cost = cost;
        }

        @Override
        public int compareTo(BusInfo o) {
            return Integer.compare(cost, o.cost);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int n = Integer.parseInt(br.readLine()); // 도시의 개수
        int m = Integer.parseInt(br.readLine()); // 버스의 개수
        List<BusInfo>[] buses = new List[n + 1];
        List<Integer>[] pathes = new List[n + 1];
        for (int i = 1; i < n + 1; i++) {
            buses[i] = new ArrayList<>();
            pathes[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            buses[from].add(new BusInfo(to, cost));
        }
        st = new StringTokenizer(br.readLine(), " ");
        int start = Integer.parseInt(st.nextToken());
        int end = Integer.parseInt(st.nextToken());

        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;
        pathes[start].add(start);

        PriorityQueue<BusInfo> pq = new PriorityQueue<>();
        pq.offer(new BusInfo(start, 0));


        while (!pq.isEmpty()) {
            BusInfo cur = pq.poll();
            if (cur.cost > dist[cur.destNum]) continue;
            if (cur.destNum == end) break;

            for (BusInfo next: buses[cur.destNum]) {
                if (dist[next.destNum] > cur.cost + next.cost) {
                    dist[next.destNum] = cur.cost + next.cost;
                    pq.offer(new BusInfo(next.destNum, dist[next.destNum]));
                    pathes[next.destNum].clear();
                    pathes[next.destNum].addAll(pathes[cur.destNum]);
                    pathes[next.destNum].add(next.destNum);
                }
            }
        }
        sb.append(dist[end]).append("\n");
        sb.append(pathes[end].size()).append("\n");
        for (int next : pathes[end]) {
            sb.append(next).append(" ");
        }
        System.out.println(sb);
        br.close();
    }
}