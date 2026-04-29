package programmers.네트워크;

import java.util.*;

public class Solution_pg_네트워크_2 {

    public static void main(String[] args) {
        Solution_pg_네트워크_2 solution = new Solution_pg_네트워크_2();
        System.out.println(solution.solution(3, new int[][]{{1,1,0},{1,1,0},{0,0,1}}));
        System.out.println(solution.solution(3, new int[][]{{1,1,0},{1,1,1},{0,1,1}}));
    }

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
