import java.io.*;
import java.util.*;

/**
 * 지뢰가 없는 칸이라면 꼭지점이 맞닿아있는 최대 8칸에 대해 몇 개의 지뢰가 있는지가 0~8 사이의 숫자로 표시된다.
 * 지뢰: *
 * 지뢰가 없는 칸: .
 * 클릭
 */
public class Solution {

    static int N, totalDot;
    static char[][] board;
    static boolean[][] open;
    static int[] dr = new int[]{-1, -1, 0, 1, 1, 1, 0, -1}, dc = new int[]{0, 1, 1, 1, 0, -1, -1, -1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            N = Integer.parseInt(br.readLine());
            board = new char[N][N];
            open = new boolean[N][N];
            totalDot = 0;
            for (int i = 0; i < N; i++) {
                String s = br.readLine();
                for (int j = 0; j < N; j++) {
                    board[i][j] = s.charAt(j);
                    if (board[i][j] == '.')
                        totalDot++;
                }
            }

            int minClick = 0;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (unClicked(i, j) && isZero(i, j)) {
                        minClick++;
                        click(i, j);
                    }
                }
            }

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (unClicked(i, j)) {
                        minClick++;
                        click(i, j);
                    }
                }
            }
            sb.append("#").append(tc).append(" ").append(minClick).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }

    static boolean unClicked(int i, int j) {
        return board[i][j] == '.';
    }

    static boolean isZero(int i, int j) {
        if (board[i][j] == '*') return false;
        for (int d = 0; d < 8; d++) {
            int nr = i + dr[d];
            int nc = j + dc[d];
            if (nr < 0 || nr > N - 1 || nc < 0 || nc > N - 1) continue;
            if (board[nr][nc] == '*') return false;
        }
        return true;
    }

    static void click(int r, int c) {
        ArrayDeque<int[]> q = new ArrayDeque<>();
        q.offer(new int[]{r, c});
        board[r][c] = 'O';
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            if (isZero(cur[0], cur[1])) {
                for (int d = 0; d < 8; d++) {
                    int nr = cur[0] + dr[d];
                    int nc = cur[1] + dc[d];
                    if (nr < 0 || nr > N - 1 || nc < 0 || nc > N - 1 || board[nr][nc] == 'O') continue;
                    board[nr][nc] = 'O';
                    q.offer(new int[]{nr, nc});
                }
            }
        }
    }

}