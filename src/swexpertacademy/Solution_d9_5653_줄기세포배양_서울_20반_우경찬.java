package swexpertacademy;

import java.util.*;
import java.io.*;

public class Solution_d9_5653_줄기세포배양_서울_20반_우경찬 {

    static int I_IDX = 0, J_IDX = 1, LEVEL_IDX = 2, STATUS_IDX = 3, TIME_IDX = 4;
    static int[] di = {-1, 0, 1, 0}, dj = {0, 1, 0, -1};

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d9_5653.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc < T + 1; tc++) {
            st = new StringTokenizer(br.readLine(), " ");
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());

            int[][][] container = new int[N + 2 * K][M + 2 * K][2];
            boolean[][] isAlive = new boolean[N + 2 * K][M + 2 * K];
            ArrayDeque<int[]> q = new ArrayDeque<>();
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                for (int j = 0; j < M; j++) {
                    int level = Integer.parseInt(st.nextToken());
                    container[i + K][j + K][0] = level;
                    container[i + K][j + K][1] = 0; // time
                    if (level != 0) {
                        q.offer(new int[]{i + K, j + K, level, level, 0});
                        isAlive[i + K][j + K] = true;
                    }
                }
            }

            while (!q.isEmpty()) {
                int[] polled = q.poll();

                int i = polled[I_IDX];
                int j = polled[J_IDX];
                int level = polled[LEVEL_IDX];
                int status = polled[STATUS_IDX];
                int time = polled[TIME_IDX];

                if (container[i][j][0] != level) {
/*
                    if (tc == 1) {
                        System.out.println("i = " + i);
                        System.out.println("j = " + j);
                        System.out.println("level = " + level);
                        System.out.println("container[i][j][0] = " + container[i][j][0]);
                    }
*/
                    // 같은 시간에 번식된 다른 줄기세포에 경쟁에 밀린 노드는 poll한 상태로 바로 다음 poll을 향한다.
                    continue;
                }
                int nStatus = status - 1;
                int nTime = time + 1;

                if (status == 0 && time < K) {
                    for (int d = 0; d < 4; d++) {
                        int ni = i + di[d];
                        int nj = j + dj[d];
                        if (container[ni][nj][0] == 0) {
                            container[ni][nj][0] = level;
                            container[ni][nj][1] = nTime;
                            isAlive[ni][nj] = true;
                            if (nTime <= K) {
                                q.offer(new int[]{ni, nj, level, level, nTime});
                            }
                        } else if (container[ni][nj][1] == nTime) {
                            if (container[ni][nj][0] < level) {
                                container[ni][nj][0] = level;
                                if (nTime < K) {
                                    q.offer(new int[]{ni, nj, level, level, nTime});
                                }
                            }
                        }
                    }
                    if (nTime <= K)
                        q.offer(new int[]{i, j, level, nStatus, nTime});
                } else if (status == -level) {
                    isAlive[i][j] = false;
                } else {
                    if (nTime <= K)
                        q.offer(new int[]{i, j, level, nStatus, nTime});
                }
            }

            int cnt = 0;
            for (int i = 0; i < N + K * 2; i++) {
                for (int j = 0; j < M + K * 2; j++) {
                    if (container[i][j][0] != 0 && isAlive[i][j]) {
                        cnt++;
                    }
                }
            }

            sb.append("#").append(tc).append(" ").append(cnt).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }

    static void printContainer(int[][][] container, int N, int M, int K, boolean[][] isDead) {
        for (int i = 0; i < N + K * 2 ; i++) {
            for (int j = 0; j < M + K * 2; j++) {
//                System.out.printf("%-8s", Arrays.toString(container[i][j]));
//                System.out.printf("%-4s", container[i][j][0]);
                System.out.printf("%-8s", isDead[i][j]);
            }
            System.out.println();
        }
        for (int i = 0; i < N + K * 2 ; i++) {
            for (int j = 0; j < M + K * 2; j++) {
                System.out.printf("%-8s", Arrays.toString(container[i][j]));
//                System.out.printf("%-4s", container[i][j][0]);
//                System.out.printf("%-8s", isDead[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
}
