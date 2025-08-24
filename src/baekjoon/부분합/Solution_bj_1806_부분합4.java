package baekjoon.부분합;

import java.io.*;
import java.util.*;

public class Solution_bj_1806_부분합4 {
    public static void main(String[] args) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            int N = Integer.parseInt(st.nextToken());
            int S = Integer.parseInt(st.nextToken());
            int answer = N + 1;
            int[] arr = new int[N];
            int sum = 0;
            int leftIdx = 0;
            st = new StringTokenizer(br.readLine(), " ");
            for (int rightIdx = 0; rightIdx < N; rightIdx++) {
                arr[rightIdx] = Integer.parseInt(st.nextToken());
                sum += arr[rightIdx];
                while (sum >= S) {
                    answer = Math.min(answer, rightIdx - leftIdx + 1);
                    sum -= arr[leftIdx++];
                }
            }
            if (answer == N + 1) {
                answer = 0;
            }
            System.out.println(answer);
        }
    }
}
