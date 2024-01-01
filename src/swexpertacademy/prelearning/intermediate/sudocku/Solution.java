package swexpertacademy.prelearning.intermediate.sudocku;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for (int test_case = 1; test_case <= t; test_case++) {
            List<List<Integer>> data = new ArrayList<>(9);
            for (int i = 0; i < 9; i++) {
                ArrayList<Integer> row = new ArrayList<>(9);
                for (int j = 0; j < 9; j++) {
                    row.add(sc.nextInt());
                }
                data.add(row);
            }
            int answer = checkCorrect(data) ? 1 : 0;
            System.out.printf("#%d %d\n", test_case, answer);
        }
    }

    private static boolean checkCorrect(List<List<Integer>> data) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int target = data.get(i).get(j);
                List<Integer> rowNumbers = data.get(i);
                if (!validateNumbers(target, rowNumbers)) {
                    return false;
                }

                List<Integer> columnNumbers = generateColumnNumbers(data, j);
                if (!validateNumbers(target, columnNumbers)) {
                    return false;
                }

                int groupI = i / 3;
                int groupJ = j / 3;
                List<Integer> groupNumbers = generateGroupNumbers(data, groupI, groupJ);
                if (!validateNumbers(target, groupNumbers)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static List<Integer> generateGroupNumbers(List<List<Integer>> data, int groupI, int groupJ) {
        List<Integer> groupNumbers = new ArrayList<>(9);
        for (int i = groupI * 3; i < groupI * 3 + 3; i++) {
            for (int j = groupJ * 3; j < groupJ * 3 + 3; j++) {
                groupNumbers.add(data.get(i).get(j));
            }
        }
        return groupNumbers;
    }

    private static ArrayList<Integer> generateColumnNumbers(List<List<Integer>> data, int j) {
        ArrayList<Integer> columnNumbers = new ArrayList<>(9);
        for (int i = 0; i < 9; i++) {
            columnNumbers.add(data.get(i).get(j));
        }
        return columnNumbers;
    }

    private static boolean validateNumbers(int target, List<Integer> numbers) {
        int count = 0;
        for (Integer rowNumber : numbers) {
            if (target == rowNumber) {
                count++;
            }
        }
        return count == 1;
    }
}
