package mincoding.이사;

import java.io.*;
import java.util.*;

/**
 * 출근하기 편한 지역으로 이사를 가려고 한다.
 * K번 이하의 버스 탑승으로 출근할 수 있는 지역
 *
 */
public class Solution_min_이사 {

    static class BusStop {
        int number, cnt;

        public BusStop(int number, int cnt) {
            this.number = number;
            this.cnt = cnt;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        List<Integer>[] graph = new List[N + 1];
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            if (graph[a] == null) graph[a] = new ArrayList<>();
            graph[a].add(b);
            if (graph[b] == null) graph[b] = new ArrayList<>();
            graph[b].add(a);
        }
        st = new StringTokenizer(br.readLine(), " ");
        int R = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        if (graph[R] == null) graph[R] = new ArrayList<>();
        ArrayDeque<BusStop> queue = new ArrayDeque<>();
        boolean[] visited = new boolean[N + 1];
        queue.offer(new BusStop(R, 0));
        visited[R] = true;
        int cnt = 0;
        while (!queue.isEmpty()) {
            BusStop cur = queue.poll();
            cnt++;
            if (cur.cnt == K) continue;
            for(int next : graph[cur.number]) {
                if (visited[next]) continue;
                visited[next] = true;
                queue.offer(new BusStop(next, cur.cnt + 1));
            }
        }
        System.out.println(cnt);
        br.close();
    }
}
