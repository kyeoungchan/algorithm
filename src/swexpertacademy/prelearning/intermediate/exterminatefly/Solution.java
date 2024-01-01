package swexpertacademy.prelearning.intermediate.exterminatefly;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for (int test_case = 1; test_case <= t; test_case++) {
            int n = sc.nextInt();
            int m = sc.nextInt();
            List<List<Integer>> flies = new ArrayList<>(n);
            for (int i = 0; i < n; i++) {
                List<Integer> row = new ArrayList<>(n);
                for (int j = 0; j < n; j++) {
                    row.add(sc.nextInt());
                }
                flies.add(row);
            }
            int maxKilling = 0;
//            List<List<Integer>> plusDirections = Arrays.asList(Arrays.asList(1, 0), Arrays.asList(0, 1), Arrays.asList(-1, 0), Arrays.asList(0, -1));
//            List<List<Integer>> multiplyDirections = Arrays.asList(Arrays.asList(1, 1), Arrays.asList(1, -1), Arrays.asList(-1, 1), Arrays.asList(-1, -1));
            List<List<Integer>> plusDirections = Stream.of(
                    Stream.of(1, 0).collect(Collectors.toList()),
                    Stream.of(0, 1).collect(Collectors.toList()),
                    Stream.of(-1, 0).collect(Collectors.toList()),
                    Stream.of(0, -1).collect(Collectors.toList())
            ).collect(Collectors.toList());
            List<List<Integer>> multiplyDirections = Stream.of(
                    Stream.of(1, 1).collect(Collectors.toList()),
                    Stream.of(1, -1).collect(Collectors.toList()),
                    Stream.of(-1, 1).collect(Collectors.toList()),
                    Stream.of(-1, -1).collect(Collectors.toList())
            ).collect(Collectors.toList());

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    int plusKill = countKilling(n, m, flies, plusDirections, i, j);
                    int xKill = countKilling(n, m, flies, multiplyDirections, i, j);
                    maxKilling = Math.max(maxKilling, Math.max(plusKill, xKill));
                }
            }
            System.out.printf("#%d %d\n", test_case, maxKilling);
        }
    }

    private static int countKilling(int n, int m, List<List<Integer>> flies, List<List<Integer>> directions, int targetI, int targetJ) {
        int killingCount = flies.get(targetI).get(targetJ);
        for (int i = 1; i < m; i++) {
            for (List<Integer> direction : directions) {
                int checkRow = targetI + i * direction.get(0);
                int checkCol = targetJ + i * direction.get(1);
                if (isInbound(n, checkRow, checkCol)) {
                    killingCount += flies.get(checkRow).get(checkCol);
                }
            }
        }
        return killingCount;
    }

    private static boolean isInbound(int n, int checkRow, int checkCol) {
        return checkRow >= 0 && checkRow < n && checkCol >= 0 && checkCol < n;
    }
}
