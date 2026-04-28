import java.util.*;

class Solution {
    public int solution(int n, int[][] computers) {
        boolean[] checked = new boolean[n];
        int answer = 0;

        ArrayDeque<Integer> q = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            if (checked[i]) continue;
            answer++;
            checked[i] = true;
            q.offer(i);
            while (!q.isEmpty()) {
                int cur = q.poll();
                for (int j = 0; j < n; j++) {
                    if (computers[cur][j] == 0 || cur == j || checked[j]) continue;
                    checked[j] = true;
                    q.offer(j);
                }
            }
        }

        return answer;
    }
}