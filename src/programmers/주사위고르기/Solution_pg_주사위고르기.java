package programmers.주사위고르기;

import java.util.*;

public class Solution_pg_주사위고르기 {
    public static void main(String[] args) {
        Solution_pg_주사위고르기 sol = new Solution_pg_주사위고르기();
        int[] solution = sol.solution(new int[][]{{1, 2, 3, 4, 5, 6}, {3, 3, 3, 3, 4, 4}, {1, 3, 3, 4, 4, 4}, {1, 1, 4, 4, 5, 5}});
        System.out.println("Arrays.toString(solution) = " + Arrays.toString(solution));
    }

    private int n, max;
    private int[] answer, answerA, answerB;
    private int[][] dice;
    private List<Integer> casesOfA, casesOfB;


    public int[] solution(int[][] dice) {
        this.dice = dice;
        n = dice.length;
        answer = new int[n / 2];
        answerA = new int[n / 2];
        answerB = new int[n / 2];
        casesOfA = new ArrayList<>();
        casesOfB = new ArrayList<>();
        select(0, 0, 0, 0);
        return answer;
    }

    private void select(int diceNum, int idxA, int idxB, int cnt) {
        // 만약 n이 4고, 현재 3번 다이스, 즉 diceNum=2인 상태인데 cnt가 0이하라면 무효
        // 만약 n이 4고, 현재 4번 다이스, 즉 diceNum=3인 상태인데 cnt가 1이하라면 무효
        // 만약 n이 6이고, 현재 4번 다이스, 즉 diceNum=3인 상태인데 cnt가 0이하라면 무효
        // 만약 n이 6이고, 현재 5번 다이스, 즉 diceNum=4인 상태인데 cnt가 1이하라면 무효
        // 만약 n이 6이고, 현재 6번 다이스, 즉 diceNum=5인 상태인데 cnt가 2이하라면 무효
        // 만약 n이 8이고, 현재 5번 다이스, 즉 diceNum=4인 상태인데 cnt가 0이하라면 무효
        // 만약 n이 8이고, 현재 6번 다이스, 즉 diceNum=5인 상태인데 cnt가 1이하라면 무효

        if (cnt == n) {
            casesOfA.clear();
            throwDices(0, answerA, 0, casesOfA);
            casesOfB.clear();
            throwDices(0, answerB, 0, casesOfB);

            int winCnt = calculateWin(casesOfA, casesOfB);
            int loseCnt = calculateWin(casesOfB, casesOfA);


            if (winCnt > loseCnt) {
                if (winCnt > max) {
                    max = winCnt;
                    for (int i = 0; i < n / 2; i++) {
                        answer[i] = answerA[i] + 1;
                    }
                }
            } else {
                if (loseCnt > max) {
                    max = loseCnt;
                    for (int i = 0; i < n / 2; i++) {
                        answer[i] = answerB[i] + 1;
                    }
                }
            }
            return;
        }

        if (idxA < n / 2) {
            answerA[idxA] = diceNum;
            select(diceNum + 1, idxA + 1, idxB, cnt + 1);
        }
        if (idxB < n / 2) {
            answerB[idxB] = diceNum;
            select(diceNum + 1, idxA, idxB + 1, cnt + 1);
        }
    }

    private void throwDices(int idx, int[] answerCandidate, int sum, List<Integer> cases) {
        if (idx == n / 2) {
            cases.add(sum);
            return;
        }

        int diceIdx = answerCandidate[idx];
        for (int i = 0; i < 6; i++) {
            int newSum = sum + dice[diceIdx][i];
            throwDices(idx + 1, answerCandidate, newSum, cases);
        }
    }

    private int calculateWin(List<Integer> wantToWin, List<Integer> notWantToWin) {
        int cnt = 0;
        Collections.sort(notWantToWin);
        for (int i = 0; i < wantToWin.size(); i++) {
            int caseI = wantToWin.get(i);
            int left = 0, right = notWantToWin.size() - 1;
            int idx = -1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (notWantToWin.get(mid) < caseI) {
                    left = mid + 1;
                    idx = mid;
                } else {
                    right = mid - 1;
                }
            }
            // 만약 notWantToWin의 값들이 caseI보다 죄다 크거나 같으면 idx = -1
            // 만약 notWantToWin의 값들이 caseI보다 작으면 idx = notWantToWin.size()-1
            // 그 외에는 처음으로 caseI보다 크거나 같은 인덱스 바로 전 인덱스의 값이 idx
            if (idx != -1) {
                cnt += idx + 1;
            }
        }
        return cnt;

    }

}
