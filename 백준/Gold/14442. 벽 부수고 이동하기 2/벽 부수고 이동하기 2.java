import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

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
        int r, c, dist;

        public Status(int r, int c, int dist) {
            this.r = r;
            this.c = c;
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
        int[][] breakCnt = new int[N + 1][M + 1];

        for (int i = 1; i < N + 1; i++) {
            String s = br.readLine();
            for (int j = 1; j < M + 1; j++) {
                map[i][j] = s.charAt(j - 1) - '0';
                breakCnt[i][j] = Integer.MAX_VALUE;
            }
        }
        int answer = -1;

        ArrayDeque<Status> q = new ArrayDeque<>();
        q.offer(new Status(1, 1, 1));
        breakCnt[1][1] = 0;

        int[] dr = new int[]{-1, 0, 1, 0}, dc = new int[]{0, 1, 0, -1};

        while (!q.isEmpty()) {
            Status cur = q.poll();

            if (cur.r == N && cur.c == M) {
                answer = cur.dist;
                break;
            }

            for (int d = 0; d < 4; d++) {
                int nr = cur.r + dr[d];
                int nc = cur.c + dc[d];

                if (nr < 1 || nr > N || nc < 1 || nc > M) continue;
                int newBreak = breakCnt[cur.r][cur.c] + map[nr][nc];

                if (newBreak > K || newBreak >= breakCnt[nr][nc]) continue;
                breakCnt[nr][nc] = newBreak;
                q.offer(new Status(nr, nc, cur.dist + 1));
            }
        }

        System.out.println(answer);
        br.close();
    }
}