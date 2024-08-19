package mincoding.삼선빙고;

import java.io.*;
import java.util.*;

public class Solution_min_3선빙고 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        int[][] grid = new int[5][5];
        int[] positions = new int[26];
        StringTokenizer st;
        for (int i = 0; i < 5; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < 5; j++) {
//                grid[i][j] = Integer.parseInt(st.nextToken());
                int value = Integer.parseInt(st.nextToken());
                positions[value] = i * 5 + j;
            }
        }

        int[] rows = new int[5];
        int[] cols = new int[5];
        int[] diags = new int[2]; // 왼위, 오위 순
        int bingGo = 0;
        end: for (int i = 0; i < 5; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < 5; j++) {
                int value = Integer.parseInt(st.nextToken());
                int r = positions[value] / 5;
                int c = positions[value] % 5;
                rows[r]++;
                if (rows[r] == 5) {
                    bingGo++;
                    if (bingGo == 3) {
                        System.out.println(value);
                        break end;
                    }
                }
                cols[c]++;
                if (cols[c] == 5) {
                    bingGo++;
                    if (bingGo == 3) {
                        System.out.println(value);
                        break end;
                    }
                }
                /* 0,0 1,1, 2,2, 3,3, 4,4 => diag[0]
                * 0,4 1,3, 2,2, 3,1, 4,0 => diag[1]*/
                if (r == 2 && c == 2) {
                    diags[0]++;
                    diags[1]++;
                    if (diags[0] == 5 || diags[1] == 5) {
                        bingGo++;
                        if (bingGo == 3) {
                            System.out.println(value);
                            break end;
                        }
                    }
                } else if (r == c) {
                    diags[0]++;
                    if (diags[0] == 5) {
                        bingGo++;
                        if (bingGo == 3) {
                            System.out.println(value);
                            break end;
                        }
                    }
                } else if (r + c == 4) {
                    diags[1]++;
                    if (diags[1] == 5) {
                        bingGo++;
                        if (bingGo == 3) {
                            System.out.println(value);
                            break end;
                        }
                    }
                }
            }
        }
        br.close();
    }
}
