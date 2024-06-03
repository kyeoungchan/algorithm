import java.io.*;
import java.util.*;

public class Main {

    static int n, answer;
    static int[] dr = new int[] {-1, 0, 1, 0}, dc = new int[]{0, 1, 0, -1};
    static int[][] map, memo;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        n = Integer.parseInt(br.readLine());
        map = new int[n][n];
        memo = new int[n][n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                memo[i][j] = -1;
            }
        }
        answer = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (memo[i][j] != -1) continue;
                memo[i][j] = move(i, j);
                answer = Math.max(answer, memo[i][j]);
            }
        }
        System.out.println(answer);
        br.close();
    }

    static int move(int r, int c) {
        if (memo[r][c] != -1) return memo[r][c];
        memo[r][c] = 0;
        for (int d = 0; d < 4; d++) {
            int nr = r + dr[d];
            int nc = c + dc[d];
            if (nr < 0 || nr > n - 1 || nc < 0 || nc  > n - 1 || map[r][c] >= map[nr][nc]) continue;
            memo[r][c] = Math.max(move(nr, nc), memo[r][c]);
        }
        memo[r][c]++;
        return memo[r][c];
    }
}