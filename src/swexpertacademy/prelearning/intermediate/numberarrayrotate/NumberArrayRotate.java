package swexpertacademy.prelearning.intermediate.numberarrayrotate;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NumberArrayRotate {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for (int test_case = 1; test_case <= t; test_case++) {
            int n = sc.nextInt();
            List<List<Integer>> data = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                List<Integer> iRow = new ArrayList<>();
                for (int j = 0; j < n; j++) {
                    iRow.add(sc.nextInt());
                }
                data.add(iRow);
            }
            System.out.println("#" + test_case);
            List<String> result = rotateData(data, n);
            for (String answer : result) {
                System.out.println(answer);
            }
        }
    }

    private static List<String> rotateData(List<List<Integer>> data, int n) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            result.add(rotate90(data, i, n) + " " + rotate180(data, i, n) + " " + rotate270(data, i, n));
        }
        return result;
    }

    private static String rotate90(List<List<Integer>> data, int row, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int rotatedNumber = data.get(n - 1 - i).get(row);
            sb.append(rotatedNumber);
        }
        return sb.toString();
    }

    private static String rotate180(List<List<Integer>> data, int row, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int rotatedNumber = data.get(n - 1 - row).get(n - 1 - i);
            sb.append(rotatedNumber);
        }
        return sb.toString();
    }

    private static String rotate270(List<List<Integer>> data, int row, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int rotatedNumber = data.get(i).get(n - 1 - row);
            sb.append(rotatedNumber);
        }
        return sb.toString();
    }
}