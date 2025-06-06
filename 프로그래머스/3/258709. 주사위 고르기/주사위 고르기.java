import java.util.*;

public class Solution {
    private int n, max;
    private boolean[] selectedByA;
    private int[] answer;
    private int[][] dice;
    private List<Integer> casesOfA, casesOfB;


    public int[] solution(int[][] dice) {
        this.dice = dice;
        n = dice.length;
        answer = new int[n / 2];
        selectedByA = new boolean[n];
        int[] dicesOfA = new int[n / 2];
        casesOfA = new ArrayList<>();
        casesOfB = new ArrayList<>();
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
        if (n - diceNum < n / 2 - cnt) {
            return;
        }

        if (cnt == n / 2) {
            casesOfA.clear();
            casesOfB.clear();
            throwDices(0, dicesOfA, 0, casesOfA);

            int[] dicesOfB = new int[n / 2];
            int idx = 0;
            for (int i = 0; i < n; i++) {
                if (selectedByA[i]) continue;
                dicesOfB[idx++] = i;
            }

            throwDices(0, dicesOfB, 0, casesOfB);
            int winCnt = calculateWin(casesOfA, casesOfB);

            if (max < winCnt) {
                max = winCnt;
                for (int i = 0; i < n / 2; i++) {
                    answer[i] = dicesOfA[i] + 1;
                }
            }
            return;
        }

        dicesOfA[cnt] = diceNum;
        selectedByA[diceNum] = true;
        select(diceNum + 1, cnt + 1, dicesOfA);
        selectedByA[diceNum] = false;
        select(diceNum + 1, cnt, dicesOfA);
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
//            if (idx != -1) {
                cnt += idx + 1;
//            }
        }
        return cnt;

    }

}
