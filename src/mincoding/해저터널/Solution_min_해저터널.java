package mincoding.해저터널;

import java.io.*;
import java.util.*;

public class Solution_min_해저터널 {

    static class Tunnel {
        int id, r, c, length;

        public Tunnel(int id, int r, int c, int length) {
            this.id = id;
            this.r = r;
            this.c = c;
            this.length = length;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        char[][] map = new char[N][M];
        boolean[][] visited = new boolean[N][M];
        int startR = -1, startC = -1;
        for (int i = 0; i < N; i++) {
            String s = br.readLine();
            for (int j = 0; j < M; j++) {
                map[i][j] = s.charAt(j);
                if (map[i][j] == 'X' && startR == -1) {
                    startR = i;
                    startC = j;
                }
            }
        }

        int[] dr = {-1, 0, 1, 0}, dc = {0, 1, 0, -1};

        ArrayDeque<int[]> q = new ArrayDeque<>();
        ArrayDeque<Tunnel> tunnelQ = new ArrayDeque<>();
        q.offer(new int[] {startR, startC});
        visited[startR][startC] = true;
        int cnt = 0;
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            for (int d = 0; d < 4; d++) {
                int nr = cur[0] + dr[d];
                int nc = cur[1] + dc[d];
                if (nr < 0 || nr > N - 1 || nc < 0 || nc > M - 1 || visited[nr][nc]) continue;
                if (map[nr][nc] == 'X') {
                    visited[nr][nc] = true;
                    q.offer(new int[]{nr, nc});
                } else {
                    tunnelQ.offer(new Tunnel(cnt, nr, nc, 1));
                    cnt++;
                }
            }
        }

        boolean[][][] v = new boolean[N][M][cnt];
        end: while (!tunnelQ.isEmpty()) {
            Tunnel cur = tunnelQ.poll();
            int id = cur.id;
            v[cur.r][cur.c][id] = true;
            for (int d = 0; d < 4; d++) {
                int nr = cur.r + dr[d];
                int nc = cur.c + dc[d];
                if (nr < 0 || nr > N - 1 || nc < 0 || nc > M - 1 || v[nr][nc][id]) continue;
                if (map[nr][nc] == 'X' && !visited[nr][nc]) {
                    System.out.println(cur.length);
                    break end;
                }
                v[nr][nc][id] = true;
                tunnelQ.offer(new Tunnel(id, nr, nc, cur.length + 1));
            }
        }
        br.close();
    }
}
