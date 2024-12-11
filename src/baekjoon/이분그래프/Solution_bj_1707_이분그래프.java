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

            // 입력으로 주어지는 그래프가 비연결 그래프일 수 있으므로 readyQ로 간선 정보로 주어지는 정점을 하나를 넣어준다.
            ArrayDeque<Integer> readyQ = new ArrayDeque<>();

            for (int i = 0; i < E; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                int u = Integer.parseInt(st.nextToken());
                int v = Integer.parseInt(st.nextToken());
                graph[u].add(v);
                graph[v].add(u);
                readyQ.offer(u);
            }

            ArrayDeque<Integer> q = new ArrayDeque<>();
            int[] status = new int[V + 1];

            int firstV = readyQ.poll();
            q.offer(firstV);
            status[firstV]++;

            boolean possible = true;
            while (!q.isEmpty()) {
                int cur = q.poll();

                for (int next : graph[cur]) {
                    if (status[cur] == status[next]) {
                        // 이미 같은 집합으로 다음 정점이 들어와있는 상황이라면 이분 그래프가 아니다.
                        possible = false;
                        break;
                    } else if (status[next] != 0) continue;

                    status[next] = status[cur] * -1;
                    q.offer(next);
                }
                if (!possible) break;
                while (q.isEmpty() && !readyQ.isEmpty()) {
                    int next = readyQ.poll();
                    if (status[next] != 0) continue;
                    status[next]++;
                    q.offer(next);
                }
            }

            if (possible) sb.append("YES\n");
            else sb.append("NO\n");
        }
        System.out.println(sb.toString());
        br.close();
    }
}
/*
* 반례
1
5 3
1 3
1 4
2 5
정답: YES*/