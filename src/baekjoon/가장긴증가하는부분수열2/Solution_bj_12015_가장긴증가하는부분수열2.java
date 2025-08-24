package baekjoon.가장긴증가하는부분수열2;

import java.io.*;
import java.util.*;

/**
 * 참고 url: https://st-lab.tistory.com/285
 */
public class Solution_bj_12015_가장긴증가하는부분수열2 {

    public static void main(String[] args) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            int N = Integer.parseInt(br.readLine());
            int[] A = new int[N];
            int[] lIS = new int[N];
            int idx = 0;
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            for (int i = 0; i < N; i++) {
                A[i] = Integer.parseInt(st.nextToken());
            }

            lIS[idx++] = A[0];

            for (int i = 1; i < N; i++) {
                int value = A[i];
                if (value > lIS[idx - 1]) {
                    lIS[idx++] = value;
                } else if (value < lIS[idx - 1]){
                    int left = 0;
                    int right = idx - 1;
                    while (left <= right) {
                        int mid = left + (right - left) / 2;
                        if (value > lIS[mid]) {
                            left = mid + 1;
                        } else {
                            right = mid - 1;
                        }
                    }
                    lIS[left] = value;
                }
            }
            System.out.println(idx);
        }
    }

}
