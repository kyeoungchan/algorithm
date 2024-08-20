package mincoding.오목;

import java.io.*;
import java.util.*;

public class Solution_min_오목 {

    static int startR, startC, color;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int[][] grid = new int[20][20];
        int cnt = 0;
        startR = startC = 0;
        int color = 0;
        boolean complete = false;
        for (int i = 1; i < 20; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            cnt = 0;
            for (int j = 1; j < 20; j++) {
                grid[i][j] = Integer.parseInt(st.nextToken());
                if (grid[i][j] == 0) {
                    if (cnt == 5) break;
                    cnt = 0;
                    continue;
                }
                if (grid[i][j] == grid[i][j - 1]) {
                    cnt++;
                } else {
                    if (cnt == 5) break;
                    cnt = 1;
                    startR = i;
                    startC = j;
                    color = grid[i][j];
                }
            }
            if (cnt == 5) {
                complete = true;
                break;
            }
        }

        if (complete) {
            sb.append(color).append("\n").append(startR).append(" ").append(startC).append("\n");
        } else {
            color = startR = startC = cnt = 0;
            for (int j = 1; j < 20; j++) {
                for (int i = 1; i < 20; i++) {
                    if (grid[i][j] == 0) {
                        if (cnt == 5) break;
                        cnt = 0;
                        continue;
                    }
                    if (grid[i][j] == grid[i - 1][j]) {
                        cnt++;
                    } else {
                        if (cnt == 5) break;
                        cnt = 1;
                        startR = i;
                        startC = j;
                        color = grid[i][j];
                    }
                }
                if (cnt == 5) {
                    complete = true;
                    break;
                }
            }
            if (complete) {
                sb.append(color).append("\n").append(startR).append(" ").append(startC).append("\n");
            } else {
                if (diagnolCheck(grid)) {
                    sb.append(color).append("\n").append(startR).append(" ").append(startC).append("\n");
                } else {
                    sb.append(0);
                }
            }
        }
        System.out.print(sb.toString());
        br.close();
    }

    static boolean diagnolCheck(int[][] grid) {
        int cnt = 0;

        int i = 1;
        for (int j = 1; j < 16; j++) {
            cnt = startR = startC = color = 0;
            for (int k = 1; k < 20; k++) {
                if (i + k > 19 || j + k > 19) {
                    if (cnt == 5) return true;
                    break;
                }
                if (grid[i + k][j + k] == 0) {
                    if (cnt == 5) return true;
                    cnt = 0;
                    continue;
                }
                if (grid[i + k][j + k] == grid[i + k - 1][j + k - 1]) {
                    cnt++;
                } else {
                    if (cnt == 5) return true;
                    cnt = 1;
                    startR = i + k;
                    startC = j + k;
                    color = grid[i + k][j + k];
                }
            }
        }
        for (i = 2; i < 16; i++) {
            int j = 1;
            for (int k = 1; k < 20; k++) {
                if (i + k > 19 || j + k > 19) {
                    if (cnt == 5) return true;
                    break;
                }
                if (grid[i + k][j + k] == 0) {
                    if (cnt == 5) return true;
                    cnt = 0;
                    continue;
                }
                if (grid[i + k][j + k] == grid[i + k - 1][j + k - 1]) {
                    cnt++;
                } else {
                    if (cnt == 5) return true;
                    cnt = 1;
                    startR = i + k;
                    startC = j + k;
                    color = grid[i + k][j + k];
                }
            }

        }

        for (i = 5; i < 20; i++) {
            int j = 1;
            cnt = startR = startC = color = 0;
            for (int k = 1; k < 20; k++) {
                if (i - k < 1 || j + k > 19) {
                    if (cnt == 5) return true;
                    break;
                }
                if (grid[i - k][j + k] == 0) {
                    if (cnt == 5) return true;
                    cnt = 0;
                    continue;
                }
                if (grid[i - k][j + k] == grid[i - k + 1][j + k - 1]) {
                    cnt++;
                } else {
                    if (cnt == 5) return true;
                    cnt = 1;
                    startR = i - k;
                    startC = j + k;
                    color = grid[i - k][j + k];
                }
            }
        }
        i = 19;
        for (int j = 1; j < 16; j++) {
            cnt = startR = startC = color = 0;
            for (int k = 1; k < 20; k++) {
                if (i - k < 1 || j + k > 19) {
                    if (cnt == 5) return true;
                    break;
                }
                if (grid[i - k][j + k] == 0) {
                    if (cnt == 5) return true;
                    cnt = 0;
                    continue;
                }
                if (grid[i - k][j + k] == grid[i - k + 1][j + k - 1]) {
                    cnt++;
                } else {
                    if (cnt == 5) return true;
                    cnt = 1;
                    startR = i - k;
                    startC = j + k;
                    color = grid[i - k][j + k];
                }
            }
        }

        return false;
    }
}
