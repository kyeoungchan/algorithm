package softeer;

import java.io.*;
import java.util.*;

/**
 * 5 <= n, m <= 100
 * 1 <= L1, L2, R1, R2 <= n
 */
public class Solution_st_나무공격 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int targetCount = 0;
        int[][] board = new int[n][m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < m; j++) {
                int value = Integer.parseInt(st.nextToken());
                board[i][j] = value;
                if (value == 1) targetCount++;
            }
        }

        st = new StringTokenizer(br.readLine(), " ");
        int L1 = Integer.parseInt(st.nextToken()) - 1;
        int R1 = Integer.parseInt(st.nextToken()) - 1;

        for (int r = L1; r <= R1; r++) {
            for (int c = 0; c < m; c++) {
                if (board[r][c] == 1) {
                    targetCount--;
                    board[r][c] = 0;
                    break;
                }
            }
        }
        st = new StringTokenizer(br.readLine(), " ");
        int L2 = Integer.parseInt(st.nextToken()) - 1;
        int R2 = Integer.parseInt(st.nextToken()) - 1;

        for (int r = L2; r <= R2; r++) {
            for (int c = 0; c < m; c++) {
                if (board[r][c] == 1) {
                    targetCount--;
                    board[r][c] = 0;
                    break;
                }
            }
        }

        System.out.println(targetCount);
        br.close();
    }
}
