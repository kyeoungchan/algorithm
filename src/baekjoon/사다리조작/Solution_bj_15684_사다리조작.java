package baekjoon.사다리조작;

import java.io.*;
import java.util.*;

/**
 * N개의 세로선
 * M개의 가로선
 * 각각의 세로선마다 가로선을 놓을 수 있는 위치의 개수: H
 * 가로선이 연속하거나 서로 접하면 안 된다.
 * 사다리를 추가해서 i번 세로선의 결과가 i번이 나오도록 하려고 한다.
 * 추가해야하는 가로선의 개수의 최솟값
 * 입력
 * N: 세로선의 개수
 * M: 가로선의 개수
 * H: 세로선마다 놓을 수 있는 위치의 개수
 * b번 세로선과 b+1번 세로선을 a번 점선 위치에서 연결
 */
public class Solution_bj_15684_사다리조작 {

    static int N, M, H;
    static int[][] board;
    static boolean completed;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());
        board = new int[H + 1][N + 1];
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            board[a][b] = 1; // 오른쪽 선과 연결되었다는 뜻이다.
        }

        int answer = -1;
        completed = false;
        for (int add = 0; add < 4; add++) {
            getLadder(0, add);
            if (completed) {
                answer = add;
                break;
            }
        }
        System.out.println(answer);
        br.close();
    }

    static void getLadder(int cnt, int total) {
        if (cnt == total) {
            if (allNumberMatch()) {
                completed = true;
            }
            return;
        }

        for (int i = 1; i < H + 1; i++) {
            for (int j = 1; j < N; j++) {
                if (board[i][j - 1] == 1 || board[i][j] == 1 || board[i][j + 1] == 1) continue;
                board[i][j] = 1;
                getLadder(cnt + 1, total);
                board[i][j] = 0;
            }
        }
    }

    static boolean allNumberMatch() {
        for (int startJ = 1; startJ < N + 1; startJ++) {
            // startJ는 1~N 번까지다.
            int j = startJ;
            for (int i = 1; i < H + 1; i++) {
                if (board[i][j] == 1) {
                    // N번 인덱스도 1일 리가 없으므로 오른쪽으로 부담없이 갈 수 있다.
                    j++;
                } else if (board[i][j - 1] == 1) {
                    // 0번 인덱스는 1일 리가 없으므로 왼쪽으로는 부담없이 갈 수 있다.
                    j--;
                }
            }
            if (j != startJ) {
                return false;
            }
        }
        return true;
    }
}
