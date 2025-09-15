package programmers.도넛과막대그래프;

import java.util.*;

public class Solution_pg_도넛과막대그래프2 {
    public int[] solution(int[][] edges) {

        Map<Integer, Integer> in = new HashMap<>();
        Map<Integer, Integer> out = new HashMap<>();

        for (int[] edge: edges) {
            out.put(edge[0], out.getOrDefault(edge[0], 0) + 1);
            in.put(edge[1], out.getOrDefault(edge[1], 0) + 1);
        }

        int[] answer = new int[4];

        for (int node: out.keySet()) {
            if (out.get(node) > 1) {
                if (!in.containsKey(node)) {
                    answer[0] = node;
                } else {
                    answer[3]++;
                }
            }
        }

        for (int node: in.keySet()) {
            if (!out.containsKey(node)) {
                answer[2]++;
            }
        }

        answer[1] = out.get(answer[0]) - answer[2] - answer[3];

        return answer;
    }
}
