package swexpertacademy.벽돌깨기;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Solution_d9_5656_벽돌깨기_강사님코드 {

    static int[] dr = { 0, 1, 0, -1 };
    static int[] dc = { 1, 0, -1, 0 };
    static int N, W, H, answer;

    public static void printBoard(int[][] board) {
        for (int r = 0; r < H; r++) {
            System.out.println(Arrays.toString(board[r]));
        }
        System.out.println();
    }

    public static void fall(int[][] board) {
        for (int c = 0; c < W; c++) {
            Stack<Integer> stack = new Stack<>();
            for (int r = 0; r < H; r++) {
                if (board[r][c] != 0) {
                    stack.add(board[r][c]);
                    board[r][c] = 0;
                }
            }
            for (int r = H - 1; r >= 0 && !stack.isEmpty(); r--) {
                board[r][c] = stack.pop();
            }
        }
    }

    public static void breakBrick(int[][] board, int c) {
        int r = 0;
        while (r < H && board[r][c] == 0) {
            r++;
        }
        if (r == H)
            return;

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[] { board[r][c], r, c });
        board[r][c] = 0;

        while (!queue.isEmpty()) {
            int[] node = queue.poll();
            if (node[0] == 1)
                continue;

            r = node[1];
            c = node[2];

            for (int d = 0; d < 4; d++) {
                int count = node[0] - 1;
                int nr = r + dr[d];
                int nc = c + dc[d];
                while (0 <= nr && nr < H && 0 <= nc && nc < W && count != 0) {
                    if (board[nr][nc] != 0) {
                        queue.add(new int[] { board[nr][nc], nr, nc });
                        board[nr][nc] = 0;
                    }
                    nr += dr[d];
                    nc += dc[d];
                    count--;
                }
            }
        }

    }

    public static void recur(int[][] board, int count) {
        if (count == N) {
            int leftBricks = 0;
            for (int r = 0; r < H; r++) {
                for (int c = 0; c < W; c++) {
                    if (board[r][c] != 0) {
                        leftBricks++;
                    }
                }
            }
            answer = Math.min(answer, leftBricks);
            return;
        }

        for (int c = 0; c < W; c++) {
            int[][] nextBoard = new int[H][W];
            for (int i = 0; i < H; i++) {
                for (int j = 0; j < W; j++) {
                    nextBoard[i][j] = board[i][j];
                }
            }

            // 벽돌 파괴
            breakBrick(nextBoard, c);
            // 수직 낙하
            fall(nextBoard);

            recur(nextBoard, count + 1);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();

        for (int tc = 1; tc <= T; tc++) {
            N = sc.nextInt();
            W = sc.nextInt();
            H = sc.nextInt();

            int[][] board = new int[H][W];

            for (int r = 0; r < H; r++) {
                for (int c = 0; c < W; c++) {
                    board[r][c] = sc.nextInt();
                }
            }

            answer = H * W;
            recur(board, 0);

            System.out.println("#" + tc + " " + answer);
        }

    }

}

/*
 * 완전탐색 + 시뮬레이션
 *
 * 0부터 W - 1까지의 숫자중에 하나를 선택하는 과정을 N번반복 - 벽돌이 연쇄적으로 폭발하는 것을 구현 - 특정 열에 구슬을 쏴서
 * 연쇄적으로 폭발해서 사라지도록(BFS) - 열 우선순회 하며 Stack이용해서 수직낙하
 */
