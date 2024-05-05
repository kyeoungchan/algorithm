package swexpertacademy.벌꿀채취;

import java.util.Scanner;

public class Solution_d9_2115_벌꿀채취_강사님코드 {

    /*
     *
     * N x (N - M + 1) 2차원 배열에서 2 칸을 선택한다 (조합)
     *
     * 100 칸 중에 2 칸을 선택하는 경우의 수 = 5000
     *
     *
     * -> 선택한 칸으로부터 M개의 연속한 칸에 대해 최대 이득을 구한다 M개 원소 각각 선택 or 선택하지않음 2 ^ M = 32
     *
     * 5000 x 32 = 150000
     */
    static int N, M, C, maxProfit;
    static int[][] board;
    static int[][] profitBoard;

    public static void recur(int r, int c, int i, int sum, int profit) {
        if (sum > C)
            return;
        if (i == M) {
            maxProfit = Math.max(profit, maxProfit);
            return;
        }

        // i 번째 벌통을 선택한 경우
        recur(r, c, i + 1, sum + board[r][c + i], profit + board[r][c + i] * board[r][c + i]);
        // i 번째 벌통을 선택하지 않은 경우
        recur(r, c, i + 1, sum, profit);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();

        for (int tc = 1; tc <= T; tc++) {
            N = sc.nextInt();
            M = sc.nextInt();
            C = sc.nextInt();

            board = new int[N][N];
            profitBoard = new int[N][N - M + 1];

            for (int r = 0; r < N; r++) {
                for (int c = 0; c < N; c++) {
                    board[r][c] = sc.nextInt();
                }
            }

            // M개의 연속된 벌통에 대해서 합이 C를 넘지 않도록 선택해서 얻을 수 있는 최대이득
            for (int r = 0; r < N; r++) {
                for (int c = 0; c < N - M + 1; c++) {
                    maxProfit = 0;
                    recur(r, c, 0, 0, 0);
                    profitBoard[r][c] = maxProfit;
                }
            }

            // N x (N - M + 1) 칸 중 2 칸을 선택하는 조합

            int answer = 0;

            for (int r = 0; r < N; r++) {
                for (int c = 0; c < N - M + 1; c++) {

                    for (int br = r; br < N; br++) {
                        int bc = 0;
                        if (br == r) {
                            bc = c + M;
                        }
                        for (; bc < N - M + 1; bc++) {
                            answer = Math.max(answer, profitBoard[r][c] + profitBoard[br][bc]);
                        }
                    }

                }
            }

            System.out.println("#" + tc + " " + answer);

        }
    }
}
