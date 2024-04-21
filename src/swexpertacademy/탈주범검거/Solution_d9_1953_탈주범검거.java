package swexpertacademy.탈주범검거;

import java.io.*;
import java.util.*;

public class Solution_d9_1953_탈주범검거 {

    static int[] di = {-1, 0, 1, 0}, dj = {0, 1, 0, -1};

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d9_1953.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            st = new StringTokenizer(br.readLine(), " ");
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int[][] map = new int[N][M];

            // 좌표는 0부터 주어진다.
            int R = Integer.parseInt(st.nextToken());
            int C = Integer.parseInt(st.nextToken());
            int L = Integer.parseInt(st.nextToken());

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                for (int j = 0; j < M; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            ArrayDeque<int[]> pq = new ArrayDeque<>();
            pq.offer(new int[]{R, C});
            boolean[][] v = new boolean[N][M];
            v[R][C] = true;

            // 탈주범은 시간당 1의 거리를 움직일 수 있다.
            // 한 시간 뒤면 범인은 맨홀뚜껑에서만 있을 수 있다.
            // 두 시간뒤부터 맨홀뚜껑에서 1만큼 더 움직일 수 있다.
            for (int time = 0; time < L - 1; time++) {
                int pqSize = pq.size();
                for (int i = 0; i < pqSize; i++) {
                    int[] cur = pq.poll();
                    int ci = cur[0];
                    int cj = cur[1];
                    for (int d = 0; d < 4; d++) {
                        if (!canGo(map[ci][cj], d)) continue;
                        int ni = ci + di[d];
                        int nj = cj + dj[d];
                        if (ni < 0 || ni > N - 1 || nj < 0 || nj > M - 1 || v[ni][nj]) continue;
                        int reverseD = (d + 2) % 4;
                        // 현재 가고자하는 위치가 갈 수 있는지 판별하려면 그 위치에서 반대방향으로 출발할 수 있는지 판별하면 된다.
                        if (!canGo(map[ni][nj], reverseD)) continue;
                        v[ni][nj] = true;
                        pq.offer(new int[]{ni, nj});
                    }
                    // 현재 위치도 있을 수 있으므로 큐에 다시 넣어준다.
                    // 만약 계속 움직이기만 하는 조건이라면 다시 안 넣어주는 게 맞다.
                    pq.offer(cur);
                }
            }
            // 탈주범이 있을 수 있는 위치의 개수를 계산해야한다
            int cnt = pq.size();
            sb.append("#").append(tc).append(" ").append(cnt).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }

    /**
     * 1은 상하좌우로 출발이 가능하다.
     * 2는 상하로 출발이 가능하다.
     * 3은 좌우로 출발이 가능하다.
     * 4는 상우로 출발이 가능하다.
     * 5는 우하로 출발이 가능하다.
     * 6은 하좌로 출발이 가능하다.
     * 7은 좌상으로 출발이 가능하다.
     */
    static boolean canGo(int type, int d) {
        if (type == 0) return false;
        if (type == 1) return true;
        if (type == 2 || type == 3) {
            if ((type + d) % 2 == 0) return true;
            else return false;
        }
        if (type - 4 == d || (type - 3) % 4 == d) {
            return true;
        }
        return false;
    }


}
