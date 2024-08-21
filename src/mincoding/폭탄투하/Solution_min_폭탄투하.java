package mincoding.폭탄투하;

import java.io.*;
import java.util.*;

public class Solution_min_폭탄투하 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int[] dr = {-1, -1, 0, 1, 1, 1, 0, -1}, dc = {0, 1, 1, 1, 0, -1, -1, -1};
        char[][] grid = new char[4][5];
        for (int i = 0; i < 4; i++)
            Arrays.fill(grid[i], '_');

        for (int i = 0; i < 2; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int Y = Integer.parseInt(st.nextToken());
            int X = Integer.parseInt(st.nextToken());
            for (int d = 0; d < 8; d++) {
                int nr = Y + dr[d];
                int nc = X + dc[d];
                if (nr < 0 || nr > 3 || nc < 0 || nc > 4) continue;
                grid[nr][nc] = '#';
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
        br.close();
    }
}
