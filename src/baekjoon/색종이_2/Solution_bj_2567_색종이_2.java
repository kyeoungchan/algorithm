package baekjoon.색종이_2;

import java.util.*;
import java.io.*;

/**
 * 원래 좌표로 돌아오면 그 전까지의 길이를 갖고 반복문을 끝낸다.
 */
public class Solution_bj_2567_색종이_2 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int[][] board = new int[100][100];
        int papers = Integer.parseInt(br.readLine());
        int startI = 100, startJ = 100;
        for (int p = 0; p < papers; p++) {
            st = new StringTokenizer(br.readLine(), " ");
            int c = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());
            // 색종이를 붙일 때마다 i가 가장 작은 값, j가 가장 작은 값을 업데이트해준다.
            startI = r;
            startJ = c;
            for (int i = r; i < r + 10; i++) {
                for (int j = c; j < c + 10; j++) {
                    board[i][j] = 1;
                }
            }
        }
//        System.out.println("startI = " + startI);
//        System.out.println("startJ = " + startJ);
        int[] di = {-1, 0, 1, 0}, dj = {0, 1, 0, -1};
        int i = startI;
        int j = startJ;
        int len = 0;

        ArrayDeque<int[]> q = new ArrayDeque<>();
        q.offer(new int[]{i, j});
        board[i][j]++;
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int ci = cur[0];
            int cj = cur[1];
//            debug(board, ci, cj, len);
            for (int d = 0; d < 4; d++) {
                int ni = ci + di[d];
                int nj = cj + dj[d];
                if (ni < 0 || ni > 99 || nj < 0 || nj > 99 || board[ni][nj] == 0) {
                    len++;
                    continue;
                } else if (board[ni][nj] == 2) continue;
                board[ni][nj]++; // 이제 1인 곳을 방문했을 경우밖에 안 남았으므로 2로 만들어줌으로써 방문처리를 해준다.
                q.offer(new int[]{ni, nj});
            }
        }
//        debug(board, 0, 0, len);

        System.out.println(len);
        br.close();
    }

    static void debug(int[][] board, int i, int j, int len) {
        System.out.println("len = " + len);
        System.out.println("i = " + i);
        System.out.println("j = " + j);
        for (int r = 0; r < 26; r++) {
            for (int c = 0; c < 26; c++) {
                if (board[r][c] == 2)
                    System.out.print("[" + board[r][c] + "] ");
                else
                System.out.print(" " + board[r][c] + "  ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
