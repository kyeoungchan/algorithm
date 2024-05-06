package swexpertacademy.줄기세포배양;

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
                    // 같은 시간에 번식된 다른 줄기세포에 경쟁에 밀린 노드는 poll한 상태로 바로 다음 poll을 향한다.
                    continue;
                }
                int nStatus = status - 1;
                int nTime = time + 1;

                if (status == 0 && time < K) { // status가 0이 되면 활성화가 된 시점이므로 번식을 시작한다. 그리고 time이 K랑 같은 경우도 큐에 삽입을 시켰기 때문에 K와 같은 경우는 번식을 시키면 안 되므로 조건 추가
                    for (int d = 0; d < 4; d++) {
                        int ni = i + di[d];
                        int nj = j + dj[d];
                        if (container[ni][nj][0] == 0) { // 현재 그 자리에 아무 줄기세포가 없다면
                            container[ni][nj][0] = level;
                            container[ni][nj][1] = nTime;
                            isAlive[ni][nj] = true;
                            if (nTime <= K) { // nTime이 K보다 작거나 같은 경우에만 큐에 넣어준다.(그래야 반복문 탈출 가능)
                                q.offer(new int[]{ni, nj, level, level, nTime});
                            }
                        } else if (container[ni][nj][1] == nTime) { // 같은 시간대에 번식한 줄기세포가 있다면
                            if (container[ni][nj][0] < level) { // 레벨이 원래 있던 친구가 더 작은 경우에만 수정한다.
                                container[ni][nj][0] = level;
                                if (nTime < K) { // nTime이 K보다 작거나 같은 경우에만 큐에 넣어준다.(그래야 반복문 탈출 가능)
                                    q.offer(new int[]{ni, nj, level, level, nTime});
                                }
                            }
                        }
                    }
                    if (nTime <= K) // nTime이 K보다 작거나 같은 경우에만 큐에 넣어준다.(그래야 반복문 탈출 가능)
                        q.offer(new int[]{i, j, level, nStatus, nTime});
                } else if (status == -level) { // 활성화된지 레벨만큼 지난 상태이므로 죽는다.
                    isAlive[i][j] = false;
                } else {
                    if (nTime <= K)
                        q.offer(new int[]{i, j, level, nStatus, nTime});
                }
            }

            int cnt = 0;
            // 마지막으로 살아있는 줄기세포의 수를 세는 로직
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

    static void printContainer(int[][][] container, int N, int M, int K, boolean[][] isAlive) {
        for (int i = 0; i < N + K * 2; i++) {
            for (int j = 0; j < M + K * 2; j++) {
                System.out.printf("%-8s", isAlive[i][j]);
            }
            System.out.println();
        }
        for (int i = 0; i < N + K * 2; i++) {
            for (int j = 0; j < M + K * 2; j++) {
                System.out.printf("%-8s", Arrays.toString(container[i][j]));
            }
            System.out.println();
        }
        System.out.println();
    }
}
