import java.io.*;
import java.util.*;

public class Main {

    static class Status {
        int id, r, c, moveCnt;

        public Status(int id, int r, int c, int moveCnt) {
            this.id = id;
            this.r = r;
            this.c = c;
            this.moveCnt = moveCnt;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        StringBuilder sb = new StringBuilder();

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int[][] map = new int[N][M];
        Status[][] memo = new Status[N][M];
        int id = 0;

        ArrayDeque<Status> readyQ = new ArrayDeque<>();

        for (int i = 0; i < N; i++) {
            String s = br.readLine();
            for (int j = 0; j < M; j++) {
                map[i][j] = s.charAt(j) - '0';
                if (map[i][j] == 0) {
                    readyQ.offer(new Status(id++, i, j, 0));
                }
            }
        }

        int[] dr = new int[]{-1, 0, 1, 0}, dc = new int[]{0, 1, 0, -1};
        while (!readyQ.isEmpty()) {
            Status curReady = readyQ.poll();
            if (memo[curReady.r][curReady.c] != null) continue;
            memo[curReady.r][curReady.c] = curReady;
            ArrayDeque<int[]> q = new ArrayDeque<>();
            q.offer(new int[] {curReady.r, curReady.c});
            while (!q.isEmpty()) {
                curReady.moveCnt++;
                curReady.moveCnt %= 10;
                int[] cur = q.poll();
                for (int d = 0; d < 4; d++) {
                    int nr = cur[0] + dr[d];
                    int nc = cur[1] + dc[d];
                    if (nr < 0 || nr > N - 1 || nc < 0 || nc > M - 1 || map[nr][nc] == 1 || memo[nr][nc] != null) continue;
                    memo[nr][nc] = curReady;
                    q.offer(new int[] {nr, nc});
                }
            }
        }


        List<Integer> idCheck = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] == 0) {
                    sb.append(0);
                    continue;
                }
                idCheck.clear();
                int value = 1;
                for (int d = 0; d < 4; d++) {
                    int r = i + dr[d];
                    int c = j + dc[d];
                    if (r < 0 || r > N - 1 || c < 0 || c > M - 1 || map[r][c] == 1 || idCheck.contains(memo[r][c].id)) continue;
                    idCheck.add(memo[r][c].id);
                    value += memo[r][c].moveCnt;
                    value %= 10;
                }
                sb.append(value);
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }
}