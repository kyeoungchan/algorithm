package baekjoon.이분그래프;

import java.io.*;
import java.util.*;

public class Solution_bj_1707_이분그래프 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int K = Integer.parseInt(br.readLine()); // 테스트 케이스의 개수
        for (int tc = 0; tc < K; tc++) {
            st = new StringTokenizer(br.readLine(), " ");
            int V = Integer.parseInt(st.nextToken()); // 정점의 개수
            int E = Integer.parseInt(st.nextToken()); // 간선의 개수
            List<Integer>[] graph = new List[V + 1];
            for (int i = 1; i < V + 1; i++) graph[i] = new ArrayList<>();

            for (int i = 0; i < E; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                int u = Integer.parseInt(st.nextToken());
                int v = Integer.parseInt(st.nextToken());
                graph[u].add(v);
                graph[v].add(u);
            }

/*
            for (int i = 1; i < V + 1; i++) {
                System.out.println(graph[i]);
            }
*/

            ArrayDeque<Integer> q = new ArrayDeque<>();
            int[] status = new int[V + 1];
            q.offer(1);
            status[1]++;
            int cnt = 1;

            boolean possible = true;
            boolean otherSide = false;
            while (!q.isEmpty()) {
                int cur = q.poll();
                for (int next : graph[cur]) {
/*
                    System.out.println("cur = " + cur);
                    System.out.println("next = " + next);
                    System.out.println("status[cur] = " + status[cur]);
                    System.out.println("status[next] = " + status[next]);
                    System.out.println();
*/
                    if (status[cur] == status[next]) {
                        // 이미 같은 집합으로 다음 정점이 들어와있는 상황이라면 이분 그래프가 아니다.
                        possible = false;
                        break;
                    }
                    if (status[next] == 0) {
                        cnt++;
                        status[next] = status[cur] * -1;
                        q.offer(next);
                        otherSide = true;
                    }
                }
                if (!possible) break;
            }

//            System.out.println("Arrays.toString(status) = " + Arrays.toString(status));
            if (possible && otherSide && cnt == V) sb.append("YES\n");
            else sb.append("NO\n");
        }
        System.out.println(sb.toString());
        br.close();
    }
}
