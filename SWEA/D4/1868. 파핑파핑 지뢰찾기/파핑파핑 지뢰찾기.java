import java.io.*;
import java.util.*;

public class Solution {

    static int N, answer;
    static int[] dr = {-1, -1, 0, 1, 1, 1, 0, -1}, dc = {0, 1, 1, 1, 0, -1, -1, -1};
    static char[][] board;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            N = Integer.parseInt(br.readLine());
            board = new char[N][N];
            for (int i = 0; i < N; i++) {
                String s = br.readLine();
                for (int j = 0; j < N; j++)
                    board[i][j] = s.charAt(j);
            }

            answer = 0;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (board[i][j] == '.' && isZero(i, j))
                        click(i, j);
                }
            }

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (board[i][j] == '.') click(i, j);
                }
            }
            sb.append("#").append(tc).append(" ").append(answer).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }

    static boolean isZero(int r, int c) {
        for (int d = 0; d < 8; d++) {
            int nr = r + dr[d];
            int nc = c + dc[d];
            if (nr < 0 || nr > N - 1 || nc < 0 || nc > N - 1) continue;
            if (board[nr][nc] == '*') return false;
        }
        return true;
    }

    static void click(int r, int c) {
        // 빈칸일 때에만 이 메소드를 호출한다.
        answer++;
        ArrayDeque<int[]> q = new ArrayDeque<>();
        q.offer(new int[]{r, c});
        board[r][c] = 'O';
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            if (isZero(cur[0], cur[1])) {
                for (int d = 0; d < 8; d++) {
                    int nr = cur[0] + dr[d];
                    int nc = cur[1] + dc[d];
                    if (nr < 0 || nr > N - 1 || nc < 0 || nc > N - 1) continue;
                    if (board[nr][nc] == 'O') continue;
                    board[nr][nc] = 'O';
                    q.offer(new int[]{nr, nc});
                }
            }
        }
    }
}