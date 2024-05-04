import java.util.*;
import java.io.*;

public class Solution {

    static int N, M, C, maxProfit;
    static int[][] honeyBoard, profit;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            st = new StringTokenizer(br.readLine(), " ");
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            C = Integer.parseInt(st.nextToken());
            honeyBoard = new int[N][N];
            profit = new int[N][N - M + 1];
            // N이 4, M이 2라면, j는 0~2 => N - M까지는 j가 도달 가능해야한다.
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                for (int j = 0; j < N; j++) {
                    honeyBoard[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N - M + 1; j++) {
                    maxProfit = 0;
                    calcProfit(i, j, 0, 0, 0);
                    profit[i][j] = maxProfit;
                }
            }

            int ans = 0;
            for (int r = 0; r < N; r++) {
                for (int c = 0; c < N - M + 1; c++) {
                    for (int r2 = r; r2 < N; r2++) {
                        int c2 = 0;
                        if (r2 == r) c2 = c + M; // M이 2인 상태에서 c가 0이라면 c2는 2부터 가능!
                        for (; c2 < N - M + 1; c2++) {
                            ans = Math.max(ans, profit[r][c] + profit[r2][c2]);
                        }
                    }
                }
            }
            sb.append("#").append(tc).append(" ").append(ans).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }

    static void calcProfit(int r, int c, int cnt, int sum, int profit) {
        if (sum > C) return;
        if (cnt == M) {
            maxProfit = Math.max(maxProfit, profit);
            return;
        }

        calcProfit(r, c + 1, cnt + 1, sum + honeyBoard[r][c], profit + honeyBoard[r][c] * honeyBoard[r][c]);
        calcProfit(r, c + 1, cnt + 1, sum, profit);
    }
}