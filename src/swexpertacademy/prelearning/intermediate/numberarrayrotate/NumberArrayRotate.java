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
            List<List<Integer>> result = rotateData(data, n);
            printList(result);
            data.clear();
            result.clear();
        }
    }

    private static List<List<Integer>> rotateData(List<List<Integer>> data, int n) {
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            ArrayList<Integer> iRow = new ArrayList<>();
            iRow.add(rotate90(data, i, n));
            iRow.add(rotate180(data, i, n));
            iRow.add(rotate270(data, i, n));
            result.add(iRow);
        }
        return result;
    }

    private static int rotate90(List<List<Integer>> data, int row, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int rotatedNumber = data.get(n - 1 - i).get(row);
            sb.append(rotatedNumber);
        }
        return Integer.parseInt(sb.toString());
    }

    private static int rotate180(List<List<Integer>> data, int row, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int rotatedNumber = data.get(n - 1 - row).get(n - 1 - i);
            sb.append(rotatedNumber);
        }
        return Integer.parseInt(sb.toString());
    }

    private static int rotate270(List<List<Integer>> data, int row, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int rotatedNumber = data.get(i).get(n - 1 - row);
            sb.append(rotatedNumber);
        }
        return Integer.parseInt(sb.toString());
    }

    private static void printList(List<List<Integer>> data) {
        for (List<Integer> dataRow : data) {
            for (Integer number : dataRow) {
                System.out.print(number + " ");
            }
            System.out.println();
        }
    }
}