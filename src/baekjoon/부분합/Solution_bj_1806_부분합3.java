package baekjoon.부분합;

import java.io.*;
import java.util.*;

public class Solution_bj_1806_부분합3 {
    public static void main(String[] args) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            int N = Integer.parseInt(st.nextToken());
            int S = Integer.parseInt(st.nextToken());
            int[] arr = new int[N];
            st = new StringTokenizer(br.readLine(), " ");

            arr[0] = Integer.parseInt(st.nextToken());
            if (arr[0] >= S) {
                for (int i = 1; i < N; i++) {
                    st.nextToken();
                }
                System.out.println(1);
            } else {
                int answer = N + 1;
                int leftIdx = 0;
                int[] sum = new int[N];
                sum[0] = arr[0];
                for (int rightIdx = 1; rightIdx < N; rightIdx++) {
                    arr[rightIdx] = Integer.parseInt(st.nextToken());
                    sum[rightIdx] = sum[rightIdx - 1] + arr[rightIdx];
                    while (sum[rightIdx] >= S) {
                        answer = Math.min(answer, rightIdx - leftIdx + 1);
                        sum[rightIdx] -= arr[leftIdx++];
                    }
                }
                if (answer == N + 1) {
                    answer = 0;
                }
                System.out.println(answer);
            }
        }
    }
}
