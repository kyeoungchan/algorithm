package softeer;

import java.io.*;
import java.util.*;

public class Solution_st_RecoveringTheRegion {

    static int N;
    static int[] dr = {-1, 0, 1, 0}, dc = {0, 1, 0, -1};
    static int[][] board, memo;

    public static void main(String[] args) throws Exception {
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

        memo = new int[N][N];
        makeGroup(1);

        printMemo(memo);
        br.close();
    }

    static boolean makeGroup(int memoVal) {
        if (memoVal == N + 1) {
            return true;
        }
        int r = -1, c = -1;
        start: for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (memo[i][j] == 0) {
                    r = i;
                    c = j;
                    break start;
                }
            }
        }

        List<Integer> groupPoses = new ArrayList<>();
        groupPoses.add(r * N + c);

        boolean[] numbersCheck = new boolean[N + 1];
        numbersCheck[board[r][c]] = true;

        boolean[][] visited = new boolean[N][N];
        visited[r][c] = true;

        memo[r][c] = memoVal;

        ArrayDeque<int[]> q = new ArrayDeque<>();
        q.offer(new int[]{r, c});
        while (!q.isEmpty()) {
            int[] cur = q.poll();

            for (int d = 0; d < 4; d++) {
                int nr = cur[0] + dr[d];
                int nc = cur[1] + dc[d];
                if (nr < 0 || nr > N - 1 || nc < 0 || nc > N - 1 || visited[nr][nc] || numbersCheck[board[nr][nc]] || memo[nr][nc] != 0) continue;
                groupPoses.add(nr * N + nc);
                numbersCheck[board[nr][nc]] = true;
                visited[nr][nc] = true;
                memo[nr][nc] = memoVal;
                if (groupPoses.size() == N) {
                    if (makeGroup(memoVal + 1)) return true;
                    groupPoses.remove(N - 1);
                    numbersCheck[board[nr][nc]] = false;
                    visited[nr][nc] = false;
                    memo[nr][nc] = 0;
                } else {
                    q.offer(new int[]{nr, nc});
                }
            }
        }
        for (int pos : groupPoses) {
            memo[pos / N][pos % N] = 0;
        }

        return false;
    }

    static void printMemo(int[][] memo) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(memo[i][j] + " ");
            }
            System.out.println();
        }
//        System.out.println();
    }
}
