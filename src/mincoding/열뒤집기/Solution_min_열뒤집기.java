package mincoding.열뒤집기;

import java.io.*;
import java.util.*;

public class Solution_min_열뒤집기 {

    static int n, m, k, answer;
    static int[][] grid;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T  = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= T; tc++) {
//            System.out.println(tc + " start!!");
            answer = 0;
            st = new StringTokenizer(br.readLine(), " ");
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());
            k = Integer.parseInt(st.nextToken());
            grid = new int[n][m];

            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                for (int j = 0; j < m; j++) {
                    int val = Integer.parseInt(st.nextToken());
                    grid[i][j] = val;
                }
            }
            search(0, false);
            search(0, true);

            sb.append("#").append(tc).append(" ").append(answer).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }

    private static void search(int col, boolean toggle) {
        if (k < 0) {
            return;
        }

        if (col == m) {
//            printGrid();
            if (k > 0 && k % 2 != 0) {
                // k가 최소 한번은 더 토글링을 시켜야하므로 정답 도출에 적절치않다.
                return;
            }
            int candidate = 0;
            boolean allOneInRow;
            for (int i = 0; i < n; i++) {
                allOneInRow = true;
                for (int j = 0; j < m; j++) {
                    if (grid[i][j] == 0) {
                        allOneInRow = false;
                        break;
                    }
                }
                if (allOneInRow) candidate++;
            }
            answer = Math.max(candidate, answer);
//            System.out.println("answer = " + answer);
//            System.out.println();
            return;
        }

        if (toggle) {
            k--;
            for (int i = 0; i < n; i++) {
                grid[i][col]--;
                grid[i][col] *= -1;
            }
        }
        search(col + 1, false);
        search(col + 1, true);

        if (toggle) {
            k++;
            for (int i = 0; i < n; i++) {
                grid[i][col]--;
                grid[i][col] *= -1;
            }
        }
    }

    private static void printGrid() {
        System.out.println("k = " + k);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
