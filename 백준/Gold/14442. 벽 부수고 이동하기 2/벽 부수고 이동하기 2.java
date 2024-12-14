import java.io.*;
import java.util.*;

/**
 * NxM행렬로 맵 표헌
 * 0: 이동가능
 * 1: 벽
 * 1,1에서 N,M으로 최단경로로 이동하려고 한다.
 * 시작하는 칸과 끝나는 칸도 포함해서 센다.
 * K개의 벽을 부술 수 있다.
 */
public class Main {

    static class Status {
        int r, c, destroy, dist;

        public Status(int r, int c, int destroy, int dist) {
            this.r = r;
            this.c = c;
            this.destroy = destroy;
            this.dist = dist;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int[][] map = new int[N + 1][M + 1];
        for (int i = 1; i < N + 1; i++) {
            String s = br.readLine();
            for (int j = 1; j < M + 1; j++) {
                map[i][j] = s.charAt(j - 1) - '0';
            }
        }
        int answer = -1;

        ArrayDeque<Status> q = new ArrayDeque<>();
        boolean[][][] visited = new boolean[N + 1][M + 1][K + 1];
        q.offer(new Status(1, 1, 0, 1));
        visited[1][1][0] = true;

        int[] dr = new int[]{-1, 0, 1, 0}, dc = new int[]{0, 1, 0, -1};

        end: while (!q.isEmpty()) {
            Status cur = q.poll();
            again: for (int d = 0; d < 4; d++) {
                int nr = cur.r + dr[d];
                int nc = cur.c + dc[d];
                if (nr < 1 || nr > N || nc < 1 || nc > M || (map[nr][nc] == 1 && cur.destroy == K)) continue;
                if (nr == N && nc == M) {
                    answer = cur.dist + 1;
                    break end;
                }

                for (int k = 0; k < cur.destroy + 1; k++) {
                    if (visited[nr][nc][k]) continue again;
                }
                if (map[nr][nc] == 1) {
                    if (visited[nr][nc][cur.destroy + 1]) continue;
                    visited[nr][nc][cur.destroy + 1] = true;
                    q.offer(new Status(nr, nc, cur.destroy + 1, cur.dist + 1));
                } else {
                    visited[nr][nc][cur.destroy] = true;
                    q.offer(new Status(nr, nc, cur.destroy, cur.dist + 1));
                }

            }
        }

        if (N == 1 && M == 1) answer = 1;
        System.out.println(answer);
        br.close();
    }
}