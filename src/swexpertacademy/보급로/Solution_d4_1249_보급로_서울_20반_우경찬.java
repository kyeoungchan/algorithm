package swexpertacademy.보급로;

import java.util.*;
import java.io.*;

public class Solution_d4_1249_보급로_서울_20반_우경찬 {

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d4_1249.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int[] di = {-1, 0, 1, 0}, dj = {0, 1, 0, -1};

        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            int N = Integer.parseInt(br.readLine());
            int[][] map = new int[N + 1][N + 1];
            for (int i = 1; i < N + 1; i++) {
                String s = br.readLine();
                for (int j = 1; j < N + 1; j++) {
                    map[i][j] = s.charAt(j - 1) - '0';
                }
            }
            int[][] dp = new int[N + 1][N + 1];
            dp[1][1] = map[1][1];

            ArrayDeque<int[]> q = new ArrayDeque<>();
            boolean[][] visited = new boolean[N + 1][N + 1];
            q.offer(new int[]{1, 1});
            visited[1][1] = true;
            while (!q.isEmpty()) {
                int[] polled = q.poll();
                int pi = polled[0];
                int pj = polled[1];
                for (int d = 0; d < 4; d++) {
                    int ni = pi + di[d];
                    int nj = pj + dj[d];
                    if (0 < ni && ni < N + 1 && 0 < nj && nj < N + 1) {
                        if (!visited[ni][nj] || dp[ni][nj] > dp[pi][pj] + map[ni][nj]) {
                            visited[ni][nj] = true;
                            dp[ni][nj] = dp[pi][pj] + map[ni][nj];
                            q.offer(new int[]{ni, nj});
                        }
                    }
                }
            }
            sb.append("#").append(tc).append(" ").append(dp[N][N]).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }

}
