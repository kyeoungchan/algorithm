package programmers.바이러스파이프;

import java.util.*;

/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/468373
 */
public class Solution {

    static class Info {
        int num, type;

        public Info(int num, int type) {
            this.num = num;
            this.type = type;
        }
    }

    int answer, n, k;
    List<Info>[] graph;
    boolean[] infected;

    public int solution(int n, int infection, int[][] edges, int k) {
        answer = 0;
        this.n = n;
        this.k = k;

        infected = new boolean[n+1];
        graph = new List[n + 1];

        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 0; i < n-1; i++) {
            int x = edges[i][0];
            int y = edges[i][1];
            int type = edges[i][2];
            graph[x].add(new Info(y, type));
            graph[y].add(new Info(x, type));
        }

        ArrayDeque<Integer> q = new ArrayDeque<>();
        q.offer(infection);
        infected[infection] = true;
        dfs(q, k, 1);
        dfs(q, k, 2);
        dfs(q, k, 3);

        return answer;
    }

    void dfs(ArrayDeque<Integer> q, int count, int type) {
        // 닫은 카운트 차감
        count--;

        // 큐 백업
        ArrayDeque<Integer> backupQ = new ArrayDeque<>();
        ArrayDeque<Integer> newQ = new ArrayDeque<>();
        List<Integer> newInfo = new ArrayList<>();
        int qSize = q.size();
        for (int i = 0; i < qSize; i++) {
            Integer cur = q.poll();
            backupQ.offer(cur);
            newQ.offer(cur);
            q.offer(cur);
        }


        while(!backupQ.isEmpty()) {
            int cur = backupQ.poll();
            for (Info info: graph[cur]) {
                if (info.type != type || infected[info.num]) continue;
                infected[info.num] = true;
                // 다음 재귀 함수에 쓰일 큐에 저장
                newQ.offer(info.num);
                // 이번 bfs에 쓰일 큐에 저장
                backupQ.offer(info.num);
                // 재귀호출 종료 후 infected를 false로 바꾸기 위한 리스트
                newInfo.add(info.num);
            }
        }
        if (count > 0) {
            for (int i = 1; i <= 3; i++) {
                if (i == type) continue;
                dfs(newQ, count, i);
            }
        } else {
            answer = Math.max(answer, newQ.size());
        }

        for (int n: newInfo) {
            infected[n] = false;
        }

    }

}
