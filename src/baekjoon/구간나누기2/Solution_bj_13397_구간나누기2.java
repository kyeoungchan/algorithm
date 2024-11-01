package baekjoon.구간나누기2;

import java.io.*;
import java.util.*;

public class Solution_bj_13397_구간나누기2 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken()); // 수의 개수
        int M = Integer.parseInt(st.nextToken()); // 구간의 개수

        int[] numbers = new int[N];
        int left = 0, right = 0;

        st = new StringTokenizer(br.readLine(), " ");
        for (int i = 0; i < N; i++) {
            numbers[i] = Integer.parseInt(st.nextToken());
            right = Math.max(right, numbers[i]);
        }

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (!canDivide(numbers, N, M, mid)) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        System.out.println(left);
        br.close();
    }

    static boolean canDivide(int[] numbers, int n, int m, int mid) {
        int count = 1;
        int min = numbers[0];
        int max = numbers[0];
        for (int i = 1; i < n; i++) {
            min = Math.min(min, numbers[i]);
            max = Math.max(max, numbers[i]);

            if (max - min > mid) {
                if (++count > m) return false;
                min = max = numbers[i];
            }
        }
        return true;
    }
}
