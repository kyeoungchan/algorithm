package programmers.힌트스테이지;

import java.util.*;

class Solution {

    int answer, n;
    int[] havingHints;
    int[][] cost, hint;

    public int solution(int[][] cost, int[][] hint) {
        answer = Integer.MAX_VALUE;
        // 스테이지 개수
        n = cost.length;
        this.cost = cost;
        this.hint = hint;

        havingHints = new int[n];

        solve(0, 0);

        return answer;
    }

    private void solve(int idx, int costVal) {
        if (idx == n) {
            answer = Math.min(answer, costVal);
            return;
        }

        // 힌트 번들을 사지 않은 경우
        solve(idx + 1, costVal + cost[idx][Math.min(havingHints[idx], n-1)]);

        if (idx != n - 1) {
            // 힌트 번들을 산 경우
            costVal += hint[idx][0];
            for (int i = 1; i < hint[idx].length; i++) {
                havingHints[hint[idx][i] - 1]++;
            }

            solve(idx + 1, costVal + cost[idx][Math.min(havingHints[idx], n-1)]);

            // 원복
            for (int i = 1; i < hint[idx].length; i++) {
                havingHints[hint[idx][i] - 1]--;
            }
        }

    }
}
