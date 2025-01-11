package baekjoon.불;

import java.io.*;
import java.util.*;

public class Solution_bj_4179_불 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int R = Integer.parseInt(st.nextToken());
        int C = Integer.parseInt(st.nextToken());
        char[][] map = new char[R][C];
        int jR = 0, jC = 0, fR = 0, fC = 0;
        for (int i = 0; i < R; i++) {
            String s = br.readLine();
            for (int j = 0; j < C; j++) {
                map[i][j] = s.charAt(j);
                if (map[i][j] == 'J') {
                    jR = i;
                    jC = j;
                } else if (map[i][j] == 'F') {
                    fR = i;
                    fC = j;
                }
            }
        }

        ArrayDeque<int[]> fireQ = new ArrayDeque<>();
        ArrayDeque<int[]> jihunQ = new ArrayDeque<>();
        boolean[][] visited = new boolean[R][C];
        visited[jR][jC] = true;
        fireQ.offer(new int[]{fR, fC});
        jihunQ.offer(new int[]{jR, jC});

        int[] dr = new int[]{-1, 0, 1, 0}, dc = new int[]{0, 1, 0, -1};
        while (true) {
            int fireRage = fireQ.size();
            for (int i = 0; i < fireRage; i++) {

            }
        }
        br.close();
    }
}
