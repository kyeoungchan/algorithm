package baekjoon.가장긴증가하는부분수열4;

import java.io.*;
import java.util.*;

public class Solution_bj_14002_가장긴증가하는부분수열4 {
    public static void main(String[] args) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            int N = Integer.parseInt(br.readLine());
            int[] A  = new int[N];
            List<Integer>[] dp = new List[N];
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < N; i++) {
                A[i] = Integer.parseInt(st.nextToken());
                dp[i] = new ArrayList<>();
            }

            int answerLen = 1;
            int answerIdx = 0;
            for (int i = 0; i < N; i++) {
                int dpILen = 0;
                int targetIdx = -1;
                for (int j = 0; j < i; j++) {
                    if (A[i] > A[j]) {
                        if (dpILen < dp[j].size()) {
                            dpILen = dp[j].size();
                            targetIdx = j;
                        }
                    }
                }
                if (targetIdx != -1) {
                    if (dpILen + 1 > answerLen) {
                        answerIdx = i;
                        answerLen = dpILen + 1;
                    }
                    dp[i].addAll(dp[targetIdx]);
                }
                dp[i].add(A[i]);
            }
            sb.append(dp[answerIdx].size()).append("\n");
            for (int i = 0; i < dp[answerIdx].size(); i++) {
                sb.append(dp[answerIdx].get(i)).append(" ");
            }
            System.out.println(sb.toString());
        }
    }
}
