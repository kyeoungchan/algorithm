package mincoding.스캔로봇;

import java.io.*;
import java.util.*;

/**
 * N x M 크기의 방의 바닥에 파이프 스캔
 * 규칙
 * 1. 현재 칸이 스캔한 적이 없으면, 현재 칸을 스캔
 * 2. 현재 칸의 주변 4칸 중, 스캔이 되지 않은 빈 칸이 있을 경우
 *  1) 반시계 방향으로 90도 회전
 *  2) 바라보는 방향을 기준으로 앞쪽 칸이 스캔되지 않은 빈칸인 경우 한 칸 전진
 *  3) 1.로 돌아간다.
 * 3. 현재 칸의 주변 4칸 중 스캔되지 않은 빈칸이 없을 경우
 *  1) 바라보는 방향을 유지한 채로 한 칸 후진할 수 있다면 후진하고 1번으로 돌아간다.
 *  2) 바라보는 방향 뒷 칸이 벽이라 후진할 수 없다면 멈춘다.
 * 방의 가장 자리는 한 줄 이상의 벽으로 둘러쌓여있다.
 * 스캔 로봇의 첫 위치는 항상 빈 칸이다.
 */
public class Solution_min_스캔로봇 {
    static int N, M, curR, curC, curD;
    static int[] dr = new int[] {-1, 0, 1, 0}, dc = new int[]{0, 1, 0, -1};
    static int[][] board;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        board = new int[N][M];

        st = new StringTokenizer(br.readLine(), " ");
        curR = Integer.parseInt(st.nextToken());
        curC = Integer.parseInt(st.nextToken());
        curD = Integer.parseInt(st.nextToken());

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < M; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int answer = 0;
        boolean canGo;
        while (true) {
            if (board[curR][curC] == 0) {
                answer++;
                board[curR][curC] = -1;
            }
            canGo = false;
/*
            System.out.println("curR = " + curR);
            System.out.println("curC = " + curC);
            System.out.println("curD = " + curD);
*/

            int nd = curD;
            for (int d = 0; d < 4; d++) {
                nd--;
                if (nd < 0) nd += 4;
                int nr = curR + dr[nd];
                int nc = curC + dc[nd];
/*
                System.out.println("nr = " + nr);
                System.out.println("nc = " + nc);
                System.out.println("nd = " + nd);
                System.out.println("board[nr][nc] = " + board[nr][nc]);
*/
                if (board[nr][nc] == 0) {
                    curR = nr;
                    curC = nc;
                    curD = nd;
                    canGo = true;
                    break;
                }
            }
//            System.out.println();
            if (canGo) continue;
            int oppositeD = (curD + 2) % 4;
            int backR = curR + dr[oppositeD];
            int backC = curC + dc[oppositeD];
            if (board[backR][backC] == 1) {
                // 후진을 하려는데 벽이면 멈춘다.
                break;
            }
            // 후진에 성공하면 후진만 하고 방향은 그대로다.
            curR = backR;
            curC = backC;
        }
        System.out.println(answer);
        br.close();
    }
}
