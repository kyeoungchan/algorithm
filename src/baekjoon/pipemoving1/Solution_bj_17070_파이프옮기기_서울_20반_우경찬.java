package baekjoon.pipemoving1;

import java.util.*;
import java.io.*;

/**
 * dfs로 풀어야할 것 같다.
 * 0. 밀기
 * 1. 아래 방향으로 회전하면서 밀기
 * 2. 윗 방향으로 회전하면서 밀기
 * ---
 * 현재 상태는 0, 1, 2로 표현한다. (0: 가로, 1: 세로, 2: 오른쪽 아래 대각선)
 * 0은 0, 1번만 가능하다.
 * 1은 0, 2번만 가능하다.
 * 2는 0, 1, 2이 가능하다.
 */
public class Solution_bj_17070_파이프옮기기_서울_20반_우경찬 {

    static int N, ANS, diPush[] = {0, 1, 1}, djPush[] = {1, 0, 1}, diRD[] = {1, 0, 1}, djRD[] = {1, 0, 0}, diRU[] = {0, 1, 0}, djRU[] = {0, 1, 1};
    static int[][] map;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());
        map = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        ANS = 0;
        dfs(0, 0, 0, 1, 0, 0);
        dfs(0, 0, 0, 1, 0, 1);
        System.out.println(ANS);
        br.close();
    }

    static void dfs(int startI, int startJ, int endI, int endJ, int status, int howMove) {

        int nStartI;
        int nStartJ;
        int nEndI;
        int nEndJ;
        int nStatus;
        if (howMove == 0) {
            nStartI = startI + diPush[status];
            nStartJ = startJ + djPush[status];
            nEndI = endI + diPush[status];
            nEndJ = endJ + djPush[status];
            nStatus = status;
        } else if (howMove == 1) {
            // 아래 방향으로 밀면서 회전시키는 경우
            nStartI = startI + diPush[status];
            nStartJ = startJ + djPush[status];
            nEndI = endI + diRD[status];
            nEndJ = endJ + djRD[status];
            nStatus = (status + 2) % 3; // 가로(0) -> 오른쪽 대각선(2), 오른쪽 대각선(2) -> 세로(1)
        } else {
            // 윗 방향으로 밀면서 회전시키는 경우
            nStartI = startI + diPush[status];
            nStartJ = startJ + djPush[status];
            nEndI = endI + diRU[status];
            nEndJ = endJ + djRU[status];
            nStatus = (status + 1) % 3; // 세로(1) -> 오른쪽 대각선(2), 오른쪽 대각선(2) -> 가로(0)
        }


        if (nEndI > N - 1 || nEndJ > N - 1 || map[nEndI][nEndJ] == 1)
            return;
        if (nEndI - endI == 1 && nEndJ - endJ == 1) {
            if (map[endI][endJ + 1] == 1 || map[endI + 1][endJ] == 1 || map[endI + 1][endJ + 1] == 1)
                // 끝점이 사선으로 이동할 때만 세 칸이 벽이 있는지를 확인해야 한다.
                return;
        }

        if (nEndI == N - 1 && nEndJ == N - 1) {
            ANS++;
            return;
        }

        dfs(nStartI, nStartJ, nEndI, nEndJ, nStatus, 0); // 어떤 상태건 밀기는 가능하다.
        if (nStatus == 2) { // 오른쪽 대각선 방향이라면
            dfs(nStartI, nStartJ, nEndI, nEndJ, nStatus, 1); // 아래 방향으로 회전시키면서 밀기
            dfs(nStartI, nStartJ, nEndI, nEndJ, nStatus, 2); // 윗 방향으로 회전시키면서 밀기
        } else {
            dfs(nStartI, nStartJ, nEndI, nEndJ, nStatus, nStatus + 1); // 가로(0)이면 아래 방향으로 회전시키면서 밀기(1번)이 가능하고, 세로(1)면 윗 방향으로 회전시키면서 밀기(2번)이 가능하다.
        }

    }
}
