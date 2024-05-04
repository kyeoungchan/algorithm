package swexpertacademy.보급로;

import java.util.*;
import java.io.*;

/**
 * 이번엔 다시 bfs로 풀어보자.
 * S에서 G까지 도로 복구 작업을 빠른 시간 내에 수행하고자 한다.
 * 깊이가 1이면 복구시간 1
 * 거리는 신경쓰지 않아도 된다.
 */
public class Solution_d4_1249_보급로 {

    static int[] di = {-1, 0, 1, 0}, dj = {0, 1, 0, -1};

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d4_1249.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            int N = Integer.parseInt(br.readLine());
            int[][] map = new int[N][N];
            int[][] memo = new int[N][N];
            String s;
            for (int i = 0; i < N; i++) {
                s = br.readLine();
                for (int j = 0; j < N; j++) {
                    // 가끔 까먹을 때도 있다. '0'을 해주어야한다!
                    map[i][j] = s.charAt(j) - '0';
                }
                Arrays.fill(memo[i], Integer.MAX_VALUE);
            }
            memo[0][0] = 0;
            ArrayDeque<int[]> q = new ArrayDeque<>();
            q.offer(new int[]{0, 0}); // 좌표
            while (!q.isEmpty()) {
                int[] cur = q.poll();
                for (int d = 0; d < 4; d++) {
                    int ni = cur[0] + di[d];
                    int nj = cur[1] + dj[d];
                    if (ni < 0 || ni > N - 1 || nj < 0 || nj > N - 1) continue;
                    int newTime = memo[cur[0]][cur[1]] + map[ni][nj];
                    if (memo[ni][nj] > newTime) {
                        memo[ni][nj] = newTime;
                        q.offer(new int[]{ni, nj});
                    }
                }
            }
            sb.append("#").append(tc).append(" ").append(memo[N - 1][N - 1]).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }
}
