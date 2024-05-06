import java.io.*;
import java.util.*;

public class Solution {

    static int N;
    static int[][] memo;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            N = Integer.parseInt(br.readLine());
            memo = new int[N + 1][N + 1];
            for (int i = 1; i < N + 1; i++) {
                memo[i][0] = -1;
                memo[0][i] = -1;
            }
            int M = Integer.parseInt(br.readLine());
            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());
                // a가 b보다 작다.
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                memo[a][b] = 1;
                /* 열 기준으로 보면 2보다 작은 애는 1, 4보다 작은 애는 1인 것을 알 수 있다.
                * 0 1 0 1 0 => 1보다 2, 4이 크다.
                * 0 0 0 0 0*/
            }

            for (int i = 1; i < N + 1; i++) {
                if (memo[i][0] == -1) {
                    update(i);
                }
            }

            for (int j = 1; j < N + 1; j++) {
                memo[0][j]++; // -1 -> 0
                for (int i = 1; i < N + 1; i++) memo[0][j] += memo[i][j];
            }
            int ans = 0;
            for (int i = 1; i < N + 1; i++) {
                if (memo[i][0] + memo[0][i] == N - 1) ans++;
            }
            sb.append("#").append(tc).append(" ").append(ans).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }

    static void update(int number) {
        for (int i = 1; i < N + 1; i++) {
            if (memo[number][i] == 0) continue;
            if (memo[i][0] == -1) update(i);
            for (int j = 1; j < N + 1; j++) {
                if (memo[i][j] == 1) memo[number][j] = 1;
            }
        }
        memo[number][0]++; // -1 -> 0
        for (int i = 1; i < N + 1; i++) {
            memo[number][0] += memo[number][i];
        }
    }
}