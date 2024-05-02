package swexpertacademy.벽돌깨기;

import java.util.*;
import java.io.*;

public class Solution_d9_5656_벽돌깨기_서울_20반_우경찬 {

    static int N, W, H, MIN;
    static int[][] board, tempBoard;
    static int[] order, di = { -1, 0, 1, 0 }, dj = { 0, 1, 0, -1 };
    static boolean[][] visited;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d9_5656.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        StringTokenizer st;
        for (int tc = 1; tc < T + 1; tc++) {
            st = new StringTokenizer(br.readLine(), " ");
            N = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());
            H = Integer.parseInt(st.nextToken());
            board = new int[H][W];
            tempBoard = new int[H][W];
            order = new int[N];
            MIN = Integer.MAX_VALUE;

            for (int i = 0; i < H; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                for (int j = 0; j < W; j++) {
                    board[i][j] = Integer.parseInt(st.nextToken());
//					tempBoard[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            getOrder(0);
            sb.append("#").append(tc).append(" ").append(MIN).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }

    // 중복순열
    static void getOrder(int cnt) {
        if (cnt == N) {
            for (int i = 0; i < H; i++) {
                for (int j = 0; j < W; j++) {
                    tempBoard[i][j] = board[i][j];
                }
            }
            play();
            MIN = Math.min(MIN, getRestBricks());
            return;
        }
        for (int i = 0; i < W; i++) {
            order[cnt] = i;
            getOrder(cnt + 1);
        }
    }

    static int getRestBricks() {
        int cnt = 0;
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (tempBoard[i][j] != 0) {
                    cnt++;
                }
            }
        }
        return cnt;
    }

    static void play() {
        for (int o = 0; o < N; o++) {
            int j = order[o];
            for (int i = 0; i < H; i++) {
                if (tempBoard[i][j] != 0) {
                    visited = new boolean[H][W]; // 파괴를 시작하기 전에 초기화
                    destroy(i, j);
                    drop();
                    break;
                }
            }
        }
    }

    static void destroy(int i, int j) {
        visited[i][j] = true;
        int range = tempBoard[i][j] - 1; // 숫자가 4라면, 상우하좌로 3칸씩 죽인다.
        tempBoard[i][j] = 0;
        for (int k = 1; k < range + 1; k++) {
            for (int d = 0; d < 4; d++) {
                int ni = i + di[d] * k;
                int nj = j + dj[d] * k;
                if (0 <= ni && ni < H && 0 <= nj && nj < W && !visited[ni][nj]) {
                    destroy(ni, nj);
                }
            }
        }
    }

    static void printBoard() {
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                System.out.print(tempBoard[i][j] + " ");
            }
            System.out.println();
        }
    }

    static void drop() {
        for (int j = 0; j < W; j++) {
            int startI = -1;
            int endI = -1;
            for (int i = H - 1; i >= 0; i--) {
                if (tempBoard[i][j] == 0 && startI == -1) {
                    startI = i;
                } else if (tempBoard[i][j] != 0 && startI != -1) {
                    endI = i;
                    for (int k = 0; k < startI - endI; k++) {
                        if (endI - k >= 0) {
                            tempBoard[startI - k][j] = tempBoard[endI - k][j];
                            tempBoard[endI - k][j] = 0;
                        } else {
                            break;
                        }
                    }
                    i = startI+1;
                    startI = -1;
                }
            }
        }
    }
}
