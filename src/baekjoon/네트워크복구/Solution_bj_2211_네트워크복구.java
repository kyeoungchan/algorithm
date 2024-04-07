package baekjoon.네트워크복구;

import java.util.*;
import java.io.*;

/**
 * N개의 컴퓨터로 구성된 네트워크
 * 각 회선은 성능 차이가 난다. 다른 컴퓨터를 통해서 가는 것이 더 유리할 수도 있다.
 * 최소 개수의 회선만 복구해야한다.
 * 서로 다른 두 컴퓨터 간에 통신이 가능해야한다.
 * 슈퍼컴퓨터가 다른 컴퓨터들과 통신하는 데 최소시간이 원래의 네트워크에서 통신하는 데 걸리는 최소 시간보다 커져서는 안 된다.
 * <p>
 * 해결 프로세스
 * - 다익스트라 전략을 사용하자.
 * - PQ에 다만 어느 정점과 연결된 친구인지도 같이 담아두자.
 * - 그래야 dist가 업데이트될 때 정답 리스트에 연결된 친구도 같이 들어가야 한다.
 * - 회선의 개수: 정답리스트의 size()를 사용한다.
 */
public class Solution_bj_2211_네트워크복구 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        List<int[]>[] originEdges = new List[N + 1];
        int[] dist = new int[N + 1];
        for (int i = 1; i < N + 1; i++) {
            originEdges[i] = new ArrayList<>();
            dist[i] = Integer.MAX_VALUE;
        }
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            originEdges[a].add(new int[]{b, cost});
            originEdges[b].add(new int[]{a, cost});
        }

        List<int[]> resultEdges = new ArrayList<>();
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1[1], o2[1]));
        dist[1] = 0;
        pq.offer(new int[]{1, 0, 1});
        while (!pq.isEmpty()) {
//            System.out.println(Arrays.toString(dist));
            int[] cur = pq.poll();
            int minVertex = cur[0];
            int min = cur[1];
            int from = cur[2];

            if (min > dist[minVertex]) continue;
            if (minVertex != from) resultEdges.add(new int[]{from, minVertex});

            for (int[] next : originEdges[minVertex]) {
                int nextVertex = next[0];
                int cost = next[1];
                if (dist[nextVertex] > min + cost) {
                    dist[nextVertex] = min + cost;
                    pq.offer(new int[]{nextVertex, dist[nextVertex], minVertex});
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(resultEdges.size()).append("\n");
        for (int[] edge : resultEdges) {
            sb.append(edge[0]).append(" ").append(edge[1]).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }
}
