package softeer;

import java.io.*;
import java.util.*;

public class Solution_st_RecoveringTheRegion {

    static int N;
    static int[] dr = {-1, 0, 1, 0}, dc = {0, 1, 0, -1};
    static int[][] board, memo;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_st_RecoveringTheRegion.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());

        board = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

//        printBoard();
        boolean[] numberCheck = new boolean[N + 1];
        memo = new int[N][N];
        startMemo(0, 0, 1, 0, numberCheck);

        printMemo();
        br.close();
    }

    static boolean startMemo(int r, int c, int memoVal, int count, boolean[] numberCheck) {
        numberCheck[board[r][c]] = true;
        memo[r][c] = memoVal;
        count++;
        if (memoVal == N && count == N) return true; // 재귀호출 종료

        for (int d = 0; d < 4; d++) {
            int nr = r + dr[d];
            int nc = c + dc[d];
            if (nr < 0 || nr > N - 1 || nc < 0 || nc > N - 1 || memo[nr][nc] != 0) continue;
            if (count != N && numberCheck[board[nr][nc]]) continue;
            int nMemoVal = memoVal;
            int nCount = count;
            boolean[] nNumberCheck = numberCheck;
            if (count == N) {
                nMemoVal++;
                nCount = 0;
                nNumberCheck = new boolean[N + 1];
            }
/*
            printBoard();
            printMemo();
*/
            if (startMemo(nr, nc, nMemoVal, nCount, nNumberCheck)) return true;
        }
        numberCheck[board[r][c]] = false;
        memo[r][c] = 0;
        return false;
    }

    static void printBoard() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }


    static void printMemo() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(memo[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
