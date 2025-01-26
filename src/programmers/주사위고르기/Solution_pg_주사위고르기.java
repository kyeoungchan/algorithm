package programmers.주사위고르기;

import java.util.*;

public class Solution_pg_주사위고르기 {
    public static void main(String[] args) {
        Solution_pg_주사위고르기 sol = new Solution_pg_주사위고르기();
        int[] solution = sol.solution(new int[][]{{1, 2, 3, 4, 5, 6}, {3, 3, 3, 3, 4, 4}, {1, 3, 3, 4, 4, 4}, {1, 1, 4, 4, 5, 5}});
        System.out.println("Arrays.toString(solution) = " + Arrays.toString(solution));
    }

    private int n, winCnt, loseCnt, max;
    private int[] answer;
    private int[][] dice;
    private boolean[] selectedByA;


    public int[] solution(int[][] dice) {
        this.dice = dice;
        n = dice.length;
        selectedByA = new boolean[n];
        int[] dicesOfA = new int[n / 2];
        answer = new int[n / 2];
        select(0, 0, dicesOfA);
        return answer;
    }

    private void select(int diceNum, int cnt, int[] dicesOfA) {
        // 만약 n이 4고, 현재 3번 다이스, 즉 diceNum=2인 상태인데 cnt가 0이하라면 무효
        // 만약 n이 4고, 현재 4번 다이스, 즉 diceNum=3인 상태인데 cnt가 1이하라면 무효
        // 만약 n이 6이고, 현재 4번 다이스, 즉 diceNum=3인 상태인데 cnt가 0이하라면 무효
        // 만약 n이 6이고, 현재 5번 다이스, 즉 diceNum=4인 상태인데 cnt가 1이하라면 무효
        // 만약 n이 6이고, 현재 6번 다이스, 즉 diceNum=5인 상태인데 cnt가 2이하라면 무효
        // 만약 n이 8이고, 현재 5번 다이스, 즉 diceNum=4인 상태인데 cnt가 0이하라면 무효
        // 만약 n이 8이고, 현재 6번 다이스, 즉 diceNum=5인 상태인데 cnt가 1이하라면 무효

        if (diceNum - cnt >= n / 2) {
            return;
        }
        if (cnt == n / 2) {
            winCnt = 0;
            loseCnt = 0;
            throwDiceAndCalcWin(0, 0, 0);
            if (winCnt > loseCnt) {
                if (winCnt > max) {
                    max = winCnt;
                    for (int i = 0; i < n / 2; i++) {
                        answer[i] = dicesOfA[i] + 1;
                    }
                }
            } else {
                if (loseCnt > max) {
                    max = loseCnt;
                    int idx = 0;
                    for (int i = 0; i < n; i++) {
                        if (selectedByA[i]) continue;
                        answer[idx++] = i + 1;
                    }
                }
            }
            return;
        }

        selectedByA[diceNum] = true;
        dicesOfA[cnt] = diceNum;
        select(diceNum + 1, cnt + 1, dicesOfA);
        selectedByA[diceNum] = false;
        select(diceNum + 1, cnt, dicesOfA);
    }

    private void throwDiceAndCalcWin(int diceNum, int aTotal, int bTotal) {
        if (diceNum == n) {
            if (aTotal > bTotal) winCnt++;
            else if (bTotal > aTotal) loseCnt++;
            return;
        }

        for (int i = 0; i < 6; i++) {
            int value = dice[diceNum][i];
            if (selectedByA[diceNum]) {
                throwDiceAndCalcWin(diceNum + 1, aTotal + value, bTotal);
            } else {
                throwDiceAndCalcWin(diceNum + 1, aTotal, bTotal + value);
            }
        }
    }
}
