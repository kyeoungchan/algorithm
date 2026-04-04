package baekjoon.게임개발;

import java.io.*;
import java.util.*;

public class Solution_bj_1516_게임개발_위상정렬 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int N = Integer.parseInt(br.readLine());
        int[] times = new int[N + 1];
        int[] indegrees = new int[N + 1];
        List<Integer>[] nextList = new List[N + 1];

        for (int i = 1; i <= N; i++) {
            nextList[i] = new ArrayList<>();
        }

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            times[i] = Integer.parseInt(st.nextToken());

            while (true) {
                int val =  Integer.parseInt(st.nextToken());
                if (val == -1) {
                    break;
                }
                nextList[val].add(i);
                indegrees[i]++;
            }
        }

        ArrayDeque<Integer> q = new ArrayDeque<>();

        for (int i = 1; i <= N; i++) {
            if (indegrees[i] == 0) {
                q.offer(i);
            }
        }

        int[] results = new int[N + 1];

        while (!q.isEmpty()) {
            int cur = q.poll();
            for (int next: nextList[cur]) {
                indegrees[next]--;
                results[next] = Math.max(results[next], results[cur] + times[cur]);

                if (indegrees[next] == 0) {
                    q.offer(next);
                }
            }
        }

        for (int i = 1; i <= N; i++) {
            sb.append(times[i] + results[i]).append("\n");
        }
        System.out.println(sb);
        br.close();
    }
}
