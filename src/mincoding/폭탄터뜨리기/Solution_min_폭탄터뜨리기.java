package mincoding.폭탄터뜨리기;

import java.util.*;
import java.io.*;

public class Solution_min_폭탄터뜨리기 {
    static int[] dr = new int[]{-1, 0, 1, 0}, dc = new int[]{0, 1, 0, -1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        char[][] grid = new char[N][M];

        int K = Integer.parseInt(br.readLine());
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < N; i++) {
            String s = br.readLine();
            for (int j = 0; j < M; j++) {
                grid[i][j] = s.charAt(j);
                if (grid[i][j] == '@') {
                    queue.offer(i * M + j);
                }
            }
        }
        while (!queue.isEmpty()) {
            int pos = queue.poll();
            int r = pos / M;
            int c = pos % M;
            grid[r][c] = '%';
            for (int d = 0; d < 4; d++) {
                for (int k = 1; k < K + 1; k++) {
                    int nr = r + dr[d] * k;
                    int nc = c + dc[d] * k;
                    if (nr < 0 || nr > N - 1 || nc < 0 || nc > M - 1 || grid[nr][nc] == '#') break;
                    grid[nr][nc] = '%';
                }
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
        br.close();
    }
}
