package baekjoon.사다리조작;

import java.io.*;
import java.util.*;

public class Solution_bj_15684_사다리조작2 {
    static int N, M, H;
    static int[][] ladder;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());
        ladder = new int[H + 1][N + 1];
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            ladder[a][b] = 1;
            ladder[a][b + 1] = -1;
        }
        if (!possible()) {
            System.out.println(-1);
        } else {
            boolean succeeded = false;
            for (int add = 0; add < 4; add++) {
                if (addLadder(0, 1, 1, add)) {
                    System.out.println(add);
                    succeeded = true;
                    break;
                }
            }
            if (!succeeded) System.out.println(-1);
        }
        br.close();
    }

    static boolean addLadder(int cnt, int startI, int startJ, int totalRow) {
        if (cnt == totalRow) {
            if (allNumberAll()) return true;
            return false;
        }

        for (int i = startI; i < H + 1; i++) {
            int j = 1;
            if (i == startI) j = startJ;
            for (; j < N; j++) {
                if (ladder[i][j] == 0 && ladder[i][j + 1] == 0) {
                    ladder[i][j] = 1;
                    ladder[i][j + 1] = -1;
                    boolean result = addLadder(cnt + 1, i, j + 1, totalRow);
                    ladder[i][j] = 0;
                    ladder[i][j + 1] = 0;
                    if (result) return true;
                }
            }
        }
        return false;
    }

    static boolean allNumberAll() {
        for (int c = 1; c < N + 1; c++) {
            int movingC = c;
            for (int r = 1; r < H + 1; r++) {
                // 아래로 내려오면서 1을 만나면 오른쪽으로 이동, -1을 만나면 왼쪽으로 이동한다.
                movingC += ladder[r][movingC];
            }
            if (movingC != c) return false;
        }
        return true;
    }

    static boolean possible() {
        // 두 세로선 사이에 가로선이 짝수여야 돌아갈 수 있으므로, 홀수인 세로선이 4개 이상이면 불가능하다.
        int cnt = 0;
        for (int c = 1; c < N; c++) {
            int singleCnt = 0;
            for (int r = 1; r < H + 1; r++) {
                if (ladder[r][c] == 1) singleCnt++;
            }
            if ((singleCnt % 2) == 1) cnt++;
        }
        return cnt < 4;
    }
}
