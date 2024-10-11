package baekjoon.부분합;

import java.io.*;
import java.util.*;

public class Solution_bj_1806_부분합2 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken());
        int S = Integer.parseInt(st.nextToken());
        int answer = N + 1;

        int[] numbers = new int[N];
        st = new StringTokenizer(br.readLine(), " ");
        for (int i = 0; i < N; i++) numbers[i] = Integer.parseInt(st.nextToken());

        int start = 0, end = 0, sum = 0;
        while (start < N && end < N) {
            sum += numbers[end++];
            while (sum >= S) {
                answer = Math.min(end - start, answer);
                sum -= numbers[start++];
            }
        }

        if (answer == N + 1) answer = 0;
        System.out.println(answer);
        br.close();
    }
}
