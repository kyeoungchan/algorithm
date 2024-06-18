import java.util.*;

class Solution {
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
                if (cnt == n){
                    answer = true;
                    break end;
                }
            }
        }
        return answer;

    }
}