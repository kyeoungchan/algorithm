package programmers.네트워크;

import java.util.*;

/**
 * 컴퓨터의 개수 n, 연결에 대한 정보가 담긴 2차원 배열 computers가 매개변수로 주어질 때, 네트워크의 개수를 return하도록 solution 함수를 작성하라.
 * 1 <= n <= 200
 * computers는 0과 1로 이루어진 2차원 배열 그래프
 * 각 컴퓨터는 0~n-1로 표현
 */
public class Solution_pg_네트워크 {

    private int n;

    public int solution(int n, int[][] computers) {
        this.n = n;
        int answer = 0;
        boolean[] visited = new boolean[n];

        for (int i = 0; i < n; i++) {
            if (visited[i]) continue;
//            dfs(i, computers, visited);
            bfs(i, computers, visited);
            answer++;
        }

        return answer;
    }

    private void dfs(int i, int[][] computers, boolean[] visited) {
        visited[i] = true;
        for (int j = 0; j < n; j++) {
            if (visited[j] || computers[i][j] == 0) continue;
            dfs(j, computers, visited);
        }
    }

    private void bfs(int i, int[][] computers, boolean[] visited) {
        ArrayDeque<Integer> q = new ArrayDeque<>();
        visited[i] = true;
        q.offer(i);

        while (!q.isEmpty()) {
            int cur = q.poll();
            for (int j = 0; j < n; j++) {
                if (visited[j] || computers[cur][j] == 0) continue;
                visited[j] = true;
                q.offer(j);
            }
        }
    }
}
