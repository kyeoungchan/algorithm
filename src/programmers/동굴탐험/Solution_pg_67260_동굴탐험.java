package programmers.동굴탐험;

import java.util.*;

/**
 * n개의 방으로 이루어진 지하 동굴을 탐험
 * 모든 방에는 0~n-1까지 번호가 있다.
 * 유일한 입구: 0번
 * 서로 다른 두 방을 직접 연결하는 통로는 오직 하나
 * 임의의 서로 다른 두 방 사이의 최단 경로는 딱 한 가지만.
 * 모든 방은 연결되어있다.
 * 탐험계획
 * 1. 모든 방을 적어도 한 번은 방문
 * 2. 특정 방을 방문하기 전에 반드시 먼저 방문할 방이 정해져 있다.
 * 선행해서 방문해야하는 방과 방문할 방이 꼭 연속적일 필요는 없다.
 * n: 방 개수
 * path: 동굴의 각 통로들이 연결하는 두 방의 번호가 담긴 2차원 배열
 * order: 프로도가 정한 방문 순서가 담긴 2차원 배열
 * 프로도가 규칙에 맞게 모든 방을 탐험할 수 있을지 return
 * n: 2~200_000
 */
public class Solution_pg_67260_동굴탐험 {

    /**
     * 비트마스킹으로 하기에는 n의 범위가 너무 크다. order.lenth도 n/2까지 간다고 하니..
     * 내가 해볼 풀이는 말이야, 편의상 선행, 후행 노드라고 명칭하자면, 일반적인 bfs를 돌리다가 후행 노드를 선행 노드보다 먼저 만나면 일단 방문처리는 하고, 큐에 안 넣어줄거야.
     * 그런데 다른 노드를 통해서 선행 노드를 만났다는 소식이 들려오고, 후행 노드를 방문했다는 것을 발견하잖아? 바로 큐에 넣어줄거야.
     * 그러기 위해서는 선행 노드와 후행노드가 각각 누구인지 인덱스로 바로 알아줘야겠지?
     * 선행노드를 알려주는 배열: preNodes
     * 후행노드를 알려주는 배열: postNodes
     */
    public boolean solution(int n, int[][] path, int[][] order) {
        boolean answer = false;
        List<Integer>[] g = new List[n];
        int[] preNodes = new int[n];
        int[] postNodes = new int[n];

        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<>();
            preNodes[i] = -1;
            postNodes[i] = -1;
        }

        for (int i = 0; i < n - 1; i++) {
            int a = path[i][0];
            int b = path[i][1];
            g[a].add(b);
            g[b].add(a);
        }

        for (int i = 0; i < order.length; i++) {
            int pre = order[i][0];
            int post = order[i][1];
            preNodes[post] = pre;
            postNodes[pre] = post;
        }
        boolean[] visited = new boolean[n];
        int cnt = 0;
        ArrayDeque<Integer> q = new ArrayDeque<>();
        q.offer(0);
        visited[0] = true;
        cnt++;
        end: while (!q.isEmpty()) {
            int cur = q.poll();
            for (int next : g[cur]) {
                if (visited[next]) continue;
                visited[next] = true;
                // 일반적인 bfs를 돌리다가 후행 노드를 선행 노드보다 먼저 만나면 일단 방문처리는 하고, 큐에 안 넣어줄거야.
                if (preNodes[next] != -1 && !visited[preNodes[next]]) continue;
                q.offer(next);
                cnt++;
                // 그런데 다른 노드를 통해서 선행 노드를 만났다는 소식이 들려오고, 후행 노드를 방문했다는 것을 발견하잖아? 바로 큐에 넣어줄거야.
                if (postNodes[next] != -1 && visited[postNodes[next]]) {
                    q.offer(postNodes[next]);
                    cnt++;
                }
/*
                if (cnt == n){
                    answer = true;
                    break end;
                }
*/
            }
        }
        return cnt == n;
    }

}
