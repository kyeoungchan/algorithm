package baekjoon.감시;

import java.io.*;
import java.util.*;

/**
 * CCTV는 90도 방향으로 회전할 수 있다.
 * 0: 빈칸
 * 6: 벽
 * 사무실의 크기와 상태, CCTV의 정보가 주어졌을 때, CCTV의 방향을 적절히 정해서, 사각지대의 최소 크기를 구하라.
 * 1번은 4방, 2번은 2방, 3번은 4방, 4번은 4방, 5방은 1방으로 경우의 수를 구해서 사각지대의 개수를 카운트한다.
 */
public class Solution_bj_15683_감시 {

    static int N, M, K, answer;
    static int[] cctvTotal, cctvCnt, di = {-1, 0, 1, 0}, dj = {0, 1, 0, -1};
    static int[][] map;
    static List<int[]>[] cctvs;
    static ArrayDeque<int[]> q;


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        cctvs = new List[6];
        for (int i = 1; i < 6; i++) {
            cctvs[i] = new ArrayList<>();
        }
        // * NxM 크기의 직사각형 사무실
        map = new int[N][M];
        answer = 0;
        cctvTotal = new int[6];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == 0) answer++;
                else if (map[i][j] > 0 && map[i][j] < 6) {
                    cctvs[map[i][j]].add(new int[]{i, j});
                    cctvTotal[map[i][j]]++;
                    K++;
                }
            }
        }

        cctvCnt = new int[6];

        setDir(cctvCnt, 1, answer, 0);
        System.out.println(answer);
        br.close();
    }

    static void setDir(int[] cctvCnt, int cctvNum, int notViewed, int cnt) {
//        debug();
        if (cnt == K) {
            answer = Math.min(answer, notViewed);
            return;
        }

        // CCTV는 5종류가 있다.
        // * CCTV는 벽을 통과할 수 없다.
        for (int i = cctvNum; i < 6; i++) {
            if (cctvTotal[i] == cctvCnt[i]) continue;
            // 같은 것을 찾으면 1만큼 올려준다.
            // 하지만 인덱스로 접근하기 위해서는 파라미터로는 1을 빼서 줘야한다.
            cctvCnt[i]++;
            if (i == 1) {
                for (int d = 0; d < 4; d++) {
                    notViewed = monitor(notViewed, d, i, cctvCnt[i] - 1, -1);
                    setDir(cctvCnt, i, notViewed, cnt + 1);
                    notViewed = monitor(notViewed, d, i, cctvCnt[i] - 1, 1);
                }
            } else if (i == 2) {
                // 2번: 양방향
                for (int d = 0; d < 2; d++) {
                    notViewed = monitor(notViewed, d, i, cctvCnt[i] - 1, -1);
                    notViewed = monitor(notViewed, (d + 2) % 4, i, cctvCnt[i] - 1, -1);
                    setDir(cctvCnt, i, notViewed, cnt + 1);
                    notViewed = monitor(notViewed, d, i, cctvCnt[i] - 1, 1);
                    notViewed = monitor(notViewed, (d + 2) % 4, i, cctvCnt[i] - 1, 1);
                }
            } else if (i == 3) {
                // 3번: 직각의 두 방향
                for (int d = 0; d < 4; d++) {
                    notViewed = monitor(notViewed, d, i, cctvCnt[i] - 1, -1);
                    notViewed = monitor(notViewed, (d + 1) % 4, i, cctvCnt[i] - 1, -1);
                    setDir(cctvCnt, i, notViewed, cnt + 1);
                    notViewed = monitor(notViewed, d, i, cctvCnt[i] - 1, 1);
                    notViewed = monitor(notViewed, (d + 1) % 4, i, cctvCnt[i] - 1, 1);
                }
            } else if (i == 4) {
                // 4번: 세 방향
                for (int d = 0; d < 4; d++) {
                    notViewed = monitor(notViewed, d, i, cctvCnt[i] - 1, -1);
                    notViewed = monitor(notViewed, (d + 1) % 4, i, cctvCnt[i] - 1, -1);
                    notViewed = monitor(notViewed, (d + 3) % 4, i, cctvCnt[i] - 1, -1);
                    setDir(cctvCnt, i, notViewed, cnt + 1);
                    notViewed = monitor(notViewed, d, i, cctvCnt[i] - 1, 1);
                    notViewed = monitor(notViewed, (d + 1) % 4, i, cctvCnt[i] - 1, 1);
                    notViewed = monitor(notViewed, (d + 3) % 4, i, cctvCnt[i] - 1, 1);
                }
            } else if (i == 5) {
                // 5번: 4방향
                notViewed = monitor(notViewed, 0, i, cctvCnt[i] - 1, -1);
                notViewed = monitor(notViewed, 1, i, cctvCnt[i] - 1, -1);
                notViewed = monitor(notViewed, 2, i, cctvCnt[i] - 1, -1);
                notViewed = monitor(notViewed, 3, i, cctvCnt[i] - 1, -1);
                setDir(cctvCnt, i, notViewed, cnt + 1);
                notViewed = monitor(notViewed, 0, i, cctvCnt[i] - 1, 1);
                notViewed = monitor(notViewed, 1, i, cctvCnt[i] - 1, 1);
                notViewed = monitor(notViewed, 2, i, cctvCnt[i] - 1, 1);
                notViewed = monitor(notViewed, 3, i, cctvCnt[i] - 1, 1);
            }
            cctvCnt[i]--;
        }
    }

    static void debug() {
        System.out.println("answer = " + answer);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                System.out.printf("%2d ", map[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    static int monitor(int notViewed, int d, int cctvNum, int cctvIdx, int status) {
        int i = cctvs[cctvNum].get(cctvIdx)[0];
        int j = cctvs[cctvNum].get(cctvIdx)[1];
        while (true) {
            i += di[d];
            j += dj[d];
            if (i < 0 || i > N - 1 || j < 0 || j > M - 1 || map[i][j] == 6) break;
            if (map[i][j] < 1) {
                if ((map[i][j] == 0 && status == -1) || (map[i][j] == -1 && status == 1))
                    notViewed += status;
                map[i][j] += status;
            }
        }
        return notViewed;
    }
}
