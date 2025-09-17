import java.util.*;

public class Solution {

    private int N, max = Integer.MIN_VALUE;
    private int[] answer;
    private int[][] dices;
    private List<Integer> choice, listA, listB;
    
    public int[] solution(int[][] dice) {
        N = dice.length;
        dices = dice;
        answer = new int[N / 2];
        choice = new ArrayList<>();
        choosedice(0, 0);
        return answer;
    }

    private void choosedice(int depth, int s) {
        if (depth == N / 2) {
            int winning = calculateWinningPercent();
            if (max < winning) {
                max = winning;
                for (int i = 0; i < choice.size(); i++) {
                    answer[i] = choice.get(i) + 1;
                }
            }
            return;
        }

        for (int i = s; i < N; i++) {
            choice.add(i);
            choosedice(depth + 1, i + 1);
            choice.remove(choice.size() - 1);
        }
    }

    private int calculateWinningPercent() {
        int count = 0;
        
        makeListAB();

        Collections.sort(listB);
        for (Integer aNumber : listA) {
            int left = 0, right = listB.size() - 1;

            int idx = -1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (listB.get(mid) < aNumber) {
                    idx = mid;
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            count += idx + 1;
        }
        return count;
    }
    
    private void makeListAB() {
        listA = new ArrayList<>();
        listB = new ArrayList<>();

        int[][] diceA = new int[N / 2][6];
        int[][] diceB = new int[N / 2][6];
        int a = 0, b = 0;
        for (int i = 0; i < N; i++) {
            if (choice.contains(i)) {
                diceA[a] = dices[i];
                a++;
            } else {
                diceB[b] = dices[i];
                b++;
            }
        }

        makeList(0, diceA, 0, listA);
        makeList(0, diceB, 0, listB);
    }

    private void makeList(int depth, int[][] dice, int sum, List<Integer> list) {
        if (depth == N / 2) {
            list.add(sum);
            return;
        }
        for (int i = 0; i < 6; i++) {
            int newSum = sum + dice[depth][i];
            makeList(depth + 1, dice, newSum, list);
        }
    }
}
