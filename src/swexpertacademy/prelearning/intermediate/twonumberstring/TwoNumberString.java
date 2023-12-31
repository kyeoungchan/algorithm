package swexpertacademy.prelearning.intermediate.twonumberstring;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * 서로 마주보는 숫자들을 곱한 뒤 모두 더할 때의 최댓값을 구하기
 * 조건: 더 긴쪽의 양 끝을 벗어나서는 안 된다.
 * 완전 탐색으로 풀자.*/
public class TwoNumberString {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int testCase = sc.nextInt();
        for (int t = 1; t < testCase + 1; t++) {
            int n = sc.nextInt();
            int m = sc.nextInt();

            ArrayList<Integer> a = new ArrayList<>(n);
            for (int i = 0; i < n; i++) {
                a.add(sc.nextInt());
            }

            ArrayList<Integer> b = new ArrayList<>(m);
            for (int i = 0; i < m; i++) {
                b.add(sc.nextInt());
            }

            int result = 0;
            if (n > m) {
                result = getMaxMultiply(a, b, n - m + 1);
            } else {
                result = getMaxMultiply(b, a, m - n + 1);
            }
            System.out.printf("#%d %d\n", t, result);
        }
    }

    private static int getMaxMultiply(List<Integer> longerList, List<Integer> shorterList, int count) {
        int result = 0;
        for (int i = 0; i < count; i++) {
            int multiplyResult = calculateMultiply(longerList, shorterList, i);
            if (result < multiplyResult) {
                result = multiplyResult;
            }
        }
        return result;
    }

    private static int calculateMultiply(List<Integer> longerList, List<Integer> shorterList, int longerStartIdx) {
        int result = 0;
        for (int i = 0; i < shorterList.size(); i++) {
            result += shorterList.get(i) * longerList.get(longerStartIdx + i);
        }
        return result;
    }
}
