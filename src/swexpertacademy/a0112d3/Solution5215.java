package swexpertacademy.a0112d3;

import java.io.*;
import java.util.*;

public class Solution5215 {
    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input5215.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= T; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            int ans = 0;
            int n = Integer.parseInt(st.nextToken()); // 재료의 수
            int l = Integer.parseInt(st.nextToken()); // 칼로리의 제한
            int[][] data = new int[n][2];
            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                data[i][0] = Integer.parseInt(st.nextToken()); // 재료 점수
                data[i][1] = Integer.parseInt(st.nextToken()); // 재료 칼로리
            }

            int[][] dp = new int[n][l + 1];
            // 각 재료를 하나씩 담을 때, 칼로리를 고려해서 최적의 상황을 담는 것을 보텀업하기 위한 배열
            // 인덱스로 무게를 비교할 생각이기 때문에 편하게 1~l로 인덱스를 표현할 것이다.
            for (int i = 0; i < n; i++) {
                int score = data[i][0];
                int calories = data[i][1];

                for (int j = 1; j <= l; j++) {
                    // 첫 번째 재료에 대한 조사라면 비교 대상 없이 해당 칼로리에 담을 수 있냐 없냐의 차이다.
                    if (i == 0) {
                        dp[i][j] = calories > j ? 0 : score;
                    } else {
                        if (calories > j) {
                            // 이번 재료가 칼로리 초과라면 이전에 담았던 재료들을 그냥 담는다.
                            dp[i][j] = dp[i - 1][j];
                        } else {
                            dp[i][j] = Math.max(dp[i - 1][j], score + dp[i - 1][j - calories]);
                        }
                    }
                }
            }
            ans = dp[n - 1][l];
            sb.append("#").append(tc).append(" ").append(ans).append("\n");
        }
        System.out.println(sb);
    }
}
