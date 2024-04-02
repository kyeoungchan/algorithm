package swexpertacademy.swimmingleague;

import java.util.*;
import java.io.*;

public class Solution_d4_4193_수영대회결승전 {

    public static void main(String[] args) throws Exception {
        int[] di = {-1, 0, 1, 0}, dj = {0, 1, 0, -1};
        //System.setIn(new FileInputStream("res/input_d4_4193.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            int N = Integer.parseInt(br.readLine());
            int[][] map = new int[N][N];
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            st = new StringTokenizer(br.readLine(), " ");
            int startI = Integer.parseInt(st.nextToken());
            int startJ = Integer.parseInt(st.nextToken());
            st = new StringTokenizer(br.readLine(), " ");
            int endI = Integer.parseInt(st.nextToken());
            int endJ = Integer.parseInt(st.nextToken());

            ArrayDeque<int[]> q = new ArrayDeque<>();
            q.offer(new int[]{startI, startJ, 0});
            boolean[][] v = new boolean[N][N];
            v[startI][startJ] = true;
            int ANS = -1;
            end:
            while (!q.isEmpty()) {
                int[] cur = q.poll();
                int ci = cur[0];
                int cj = cur[1];
                int time = cur[2];
                boolean hasStayed = false;
                for (int d = 0; d < 4; d++) {
                    int ni = ci + di[d];
                    int nj = cj + dj[d];
                    if (ni < 0 || ni > N - 1 || nj < 0 || nj > N - 1 || v[ni][nj] || map[ni][nj] == 1) continue;
                    if (time % 3 != 2 && map[ni][nj] == 2) {
                        if (!hasStayed) {
                            q.offer(new int[]{ci, cj, time + 1});
                            hasStayed = true;
                        }
                        continue;
                    }

                    if (ni == endI && nj == endJ) {
                        ANS = time + 1;
                        break end;
                    }
                    v[ni][nj] = true;
                    q.offer(new int[]{ni, nj, time + 1});
                }
            }
            sb.append("#").append(tc).append(" ").append(ANS).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }

}