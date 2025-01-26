import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// 카카오: 주사위 고르기
public class Solution {

    static int N;
    static List<Integer> choice = new ArrayList<>();
    static int[][] dices;
    static List<Integer> arrA;
    static List<Integer> arrB;
    static int[] answer;
    static int max = Integer.MIN_VALUE;

    public static void makeArr(int depth, int[][] dice, int sum, List<Integer> arr) {
        if (depth == N / 2) {
            arr.add(sum);
            return;
        }
        for (int i = 0; i < 6; i++) {
            int newSum = sum + dice[depth][i];
            makeArr(depth + 1, dice, newSum, arr);
        }
    }

    public static void makeArrAB() {
        arrA = new ArrayList<>();
        arrB = new ArrayList<>();

        int[][] diceA = new int[N / 2][6];
        int[][] diceB = new int[N / 2][6];
        int a = 0, b = 0;
        for (int i = 0; i < N; i++) { // diceA, diceB 만듦
            if (choice.contains(i)) { // diceA
                diceA[a] = dices[i];
                a++;
            } else { // diceB
                diceB[b] = dices[i];
                b++;
            }
        }

        makeArr(0, diceA, 0, arrA);
        makeArr(0, diceB, 0, arrB);
    }

    public static int calculateWinningPercent() {
        int count = 0;

        makeArrAB(); // 이제 arrA, arrB에 모든 경우의 수 저장됨..!

        Collections.sort(arrB);
        for (int i = 0; i < arrA.size(); i++) {
            // 이분 탐색으로 arrB에 number 보다 작은 숫자가 몇개 있는지 구한다.
            int number = arrA.get(i);

            int left = 0, right = arrB.size() - 1;

            int index = Integer.MIN_VALUE;
            while (left <= right) {
                int middle = (left + right) / 2;

                if (arrB.get(middle) < number) { // 조건 만족: number 보다 작아야 함!! 같아도 안됨.
                    left = middle + 1;
                    index = Math.max(index, middle);
                } else {
                    right = middle - 1;
                }
            }
            if (index != Integer.MIN_VALUE) {
                count += index + 1;
            }
        }
        return count;
    }

    public static void choiceDice(int depth, int s) {
        if (depth == N / 2) {
            int winning = calculateWinningPercent();
//            System.out.println(choice.get(0) + ", " + choice.get(1) + " => " + winning);
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
            choiceDice(depth + 1, i + 1);
            choice.remove(choice.size() - 1);
        }
    }

    public static int[] solution(int[][] dice) {
        N = dice.length;
        dices = dice;
        answer = new int[N / 2];
        choiceDice(0, 0);
        return answer;
    }

    public static void main(String[] args) {
        int[][] dice = {{1, 2, 3, 4, 5, 6}, {3, 3, 3, 3, 4, 4}, {1, 3, 3, 4, 4, 4}, {1, 1, 4, 4, 5, 5}};
        int[] result = solution(dice);
        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i] + " ");
        }
    }
}