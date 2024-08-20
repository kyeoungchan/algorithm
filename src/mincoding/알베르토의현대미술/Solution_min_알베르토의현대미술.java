package mincoding.알베르토의현대미술;

import java.io.*;
import java.util.*;

public class Solution_min_알베르토의현대미술 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int[][] board = new int[6][6];
        int N = Integer.parseInt(br.readLine());
        int[] dr = new int[]{-1, -1, 1, 1}, dcr = new int[]{1, -1, -1, 1}, dcl = new int[]{-1, 1, 1, -1};

        for (int i = 0; i < N; i++) {
            int r = 5;
            st = new StringTokenizer(br.readLine(), " ");
            int c = Integer.parseInt(st.nextToken());

            int paint = 1;
            board[r][c] = paint;
            if (st.nextToken().charAt(0) == 'R') {
                for (int d = 0; d < 4; d++) {
                    int nr = r + dr[d];
                    int nc = c + dcr[d];
                    while (nr >= 0 && nr < 6 && nc >= 0 && nc < 6) {
                        paint++;
                        r = nr;
                        c = nc;

                        if (board[r][c] == 0 || board[r][c] > paint) board[r][c] = paint;
                        nr = r + dr[d];
                        nc = c + dcr[d];
                    }
                }
            } else {
                for (int d = 0; d < 4; d++) {
                    int nr = r + dr[d];
                    int nc = c + dcl[d];
                    while (nr >= 0 && nr < 6 && nc >= 0 && nc < 6) {
                        paint++;
                        r = nr;
                        c = nc;

                        if (board[r][c] == 0 || board[r][c] > paint) board[r][c] = paint;
                        nr = r + dr[d];
                        nc = c + dcl[d];
                    }
                }

            }
        }

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (board[i][j] == 0) System.out.print("_");
                else System.out.print(board[i][j]);
            }
            System.out.println();
        }
        br.close();
    }
}
